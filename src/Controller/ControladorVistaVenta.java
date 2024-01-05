package Controller;

import DAO.ProductoDAO;
import DAO.ProductoVendidoDAO;
import DAO.UsuariosDAO;
import DAO.VentaDAO;
import Models.*;
import Models.pojo.*;
import Util.Util;
import Views.Ticket;
import Views.VistaPrincipal;
import Views.VistaVenta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ControladorVistaVenta extends CRUD implements ActionListener {
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    private final ProductoVendidoDAO productoVendidoDAO = new ProductoVendidoDAO();
    private final VentaDAO ventaDAO = new VentaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final VistaVenta vistaVenta;
    private final VistaPrincipal vistaPrincipal;
    private final VistaVenta.VistaCobrar vistaCobrar;
    private int indexT = -1;
    private final ArrayList<Venta> ventasArray;
    private boolean enter = false;

    public ControladorVistaVenta(VistaVenta vistaVenta, VistaPrincipal vistaPrincipal, VistaVenta.VistaCobrar vistaCobrar,
    ArrayList<Venta> ventasArrayList) {
        this.vistaVenta = vistaVenta;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaCobrar = vistaCobrar;
        this.ventasArray = ventasArrayList;
        if (vistaVenta != null){
            listenerTabla();
        }
    }
    private void listenerTabla() {
        ListSelectionModel selectionModel = vistaVenta.tablaProductos.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !selectionModel.isSelectionEmpty()) {
                indexT = selectionModel.getMinSelectionIndex();
                agregarCarrito(indexT);
                selectionModel.clearSelection();
                vistaVenta.initControl();
                vistaVenta.txtCodigo.setText("");
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == vistaVenta.txtCodigo){
            buscarPorCodigo();
        }else if (event == vistaVenta.btnBuscar){
            vistaPrincipal.dibujarPanelBuscar(false,true,vistaVenta);
        } else if (event == vistaVenta.btnCobrar) {
            if (!Util.carritoArrayList.isEmpty()){
                new VistaVenta.VistaCobrar(vistaVenta,vistaPrincipal);
            }else JOptionPane.showMessageDialog(null,"Sin productos en el carrito");
        } else if (event == vistaVenta.btnCarrito){
            vistaPrincipal.dibujarPanelCarrito();
        } else if (vistaCobrar != null) {
            if (event == vistaCobrar.btnCancelar){
                vistaCobrar.dispose();
            } else if (event == vistaCobrar.btnCobrar) {
                double total = vistaVenta.total;
                double pago = Validaciones.validarDouble(vistaCobrar.txtPago.getText());
                double cambio = 0.0;
                if ( pago>= total){
                    if (pago == total){
                        JOptionPane.showMessageDialog(null,"$"+ cambio +" de cambio");
                    }else {
                        cambio = pago -total;
                        String cambioStr = String.valueOf(cambio);
                        JOptionPane.showMessageDialog(null, "$"+cambioStr+" de cambio");
                    }
                    vistaVenta.total = 0;
                    VistaVenta.totalcpy = 0;
                    llenarArrayVentas(pago,cambio);
                    eliminarTodo(Util.carritoArrayList);
                    JOptionPane.showMessageDialog(null,"COMPRA REALIZADA\nRECIBO IMPRESO");
                    vistaPrincipal.dibujarPanelVentas();
                    vistaCobrar.dispose();
                    crearTxt();
                } else {
                    JOptionPane.showMessageDialog(null, "Pago insuficiente");
                }
            }
        }
    }
    private void crearTxt(){
        int ultimoElemento = 0;
        if (!ventasArray.isEmpty()){
            ultimoElemento = ventasArray.size()-1;
        }
        Venta ultimaVenta = ventasArray.get(ultimoElemento);
        String nombreArchivo = ultimaVenta.getFolio();
        String ruta = Util.RutaTickets + nombreArchivo + ".txt";

        String info = ultimaVenta.getItemsInfo();
        String fechayhora = ultimaVenta.getFecha()+ "/" +ultimaVenta.getHora();

        Ticket ticket = new Ticket(ultimaVenta,info,fechayhora,VistaPrincipal.getUsuarioLogeado());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            writer.write(ticket.getTicketGenerado());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void agregarCarrito(int index){
        try {
            Producto prod = Util.productoArrayList.get(index);
            String nombreProducto = Util.productoArrayList.get(index).getNombre();
            int solicitados = Integer.parseInt(JOptionPane.showInputDialog(prod.toString()+"\n"+"Cantidad a agregar al carrito:"));
            int stock = Util.productoArrayList.get(index).getSotck();
            Producto producto = Util.productoArrayList.get(index);
            if(Validaciones.validarCantidad(stock, solicitados)){
                agregar(Util.carritoArrayList, new Carrito(producto, solicitados,index));
                //Util.productoArrayList.get(index).setStock((stock- solicitados));

                int idProducto = productoDAO.getKeyValueByName(producto.getNombre());
                producto.setStock(stock-solicitados);
                productoDAO.update(producto,idProducto);

                vistaVenta.initControl();
                vistaVenta.calcularTotal();
                JOptionPane.showMessageDialog(null,"AGREGADO AL CARRITO");
            }
        }catch (NumberFormatException ignored){
        }
    }
    private void llenarArrayVentas(double pago, double cambio){
        ventaDAO.setUsuarioLogeado(VistaPrincipal.getUsuarioLogeado());
        int idUsuario = usuariosDAO.getKeyValueByName(VistaPrincipal.getUsuarioLogeado().getUsuario());
        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();
        Map<Producto,Integer> productosMap = new HashMap<>();
        double total = 0;
        int cantidadProductosTotal = 0;
        ventaDAO.insert(null);
        int idVenta = (ventaDAO.getNextID()-1);
        for(Carrito carrito : Util.carritoArrayList){
            double precio = carrito.getProducto().getPrecio();
            int cantidad = carrito.getCantidad();
            cantidadProductosTotal += cantidad;
            total += precio * cantidad;
            Producto producto = carrito.getProducto();

            productosMap.put(producto,cantidad);
            Venta venta = new Venta(producto,fecha,hora,cantidad,total,pago,cambio);
            int idProducto = productoDAO.getKeyValueByName(producto.getNombre());

            insertarProductoVendido(idUsuario,cantidad,idVenta,idProducto);
            agregar(ventasArray,venta);
            //int stock = productoDAO.getStockProdcuto(idProducto);
            //producto.setStock(stock-cantidad);
            //productoDAO.update(producto,idProducto);
        }
        int ultimo = 0;
        if (!ventasArray.isEmpty()){
            ultimo = ventasArray.size()-1;
        }
        System.out.println(ultimo);
        ventasArray.get(ultimo).setProductoMap(productosMap);
        ventasArray.get(ultimo).setCantidad(cantidadProductosTotal);
        ventasArray.get(ultimo).setPago(pago);
        ventasArray.get(ultimo).setCambio(cambio);
        ventasArray.removeIf(venta -> venta.getProductoMap().isEmpty());

        Venta venta = ventasArray.get(ventasArray.size()-1);
        ventaDAO.update(venta,idVenta);
    }
    private void insertarProductoVendido(int idUsr,int cantidad, int id_venta,int id_producto){
        productoVendidoDAO.insert(new ProductoVendido(cantidad,id_venta,id_producto,idUsr));
    }
    private void buscarPorCodigo(){
        String codigo = vistaVenta.txtCodigo.getText();
        if (codigo.isEmpty()){
            productoDAO.selectAll();
            vistaVenta.initControl();
        }else {
            if (productoDAO.getValueBy(codigo,"codbarras")){
                vistaVenta.initControl();
                indexT = 0;
                agregarCarrito(indexT);
                indexT = -1;
                productoDAO.selectAll();
                vistaVenta.initControl();
                vistaVenta.txtCodigo.setText("");
            }else JOptionPane.showMessageDialog(null,"NO SE ENCONTRO EL PRODUCTO",":/",JOptionPane.WARNING_MESSAGE);
        }
    }
}
