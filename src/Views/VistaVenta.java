package Views;

import Controller.ControladorVistaVenta;
import Media.DisenoVistas;
import Models.pojo.Carrito;
import Models.pojo.Producto;
import Models.pojo.Venta;
import Util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VistaVenta extends JFrame {
    private JPanel panelContenedor, pnlDetalles,pnlFiltrosYBuscar,pnlBuscar;
    public JButton btnCobrar, btnBuscar,btnCarrito;
    public DefaultTableModel modeloProductos;
    public JTable tablaProductos;
    public JTextField txtCodigo;
    public JLabel txtTotal,cantidadProductos;
    private final VistaPrincipal vistaPrincipal;
    public double total = 0;
    public static double totalcpy;
    private final ArrayList<Venta> ventaArrayList;

    public VistaVenta(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.ventaArrayList = VistaPrincipal.getArrayVentasDeUsuario();
        crearComponentes();
        initControl();
        configTable();
        dibujarCuerpo();
        add(panelContenedor);
        addListeners();
        calcularTotal();
    }


    private void crearComponentes() {
        DisenoVistas disenoVistas = new DisenoVistas();
        btnCobrar = new JButton("Cobrar");
        btnBuscar = disenoVistas.imagenBoton("Buscar","Media/Imagenes/buscar.png",15,15);
        btnCarrito = disenoVistas.imagenBoton("Ver carrito","Media/Imagenes/carritoCompra.png",15,15);
        modeloProductos = new DefaultTableModel();
        tablaProductos = new JTable(modeloProductos);

        panelContenedor = new JPanel();
        pnlDetalles = new JPanel();

        pnlFiltrosYBuscar = new JPanel();
        pnlBuscar = new JPanel();

        txtTotal = new JLabel("$");
        txtCodigo = new JTextField();

        cantidadProductos = new JLabel("0");

    }

    private void dibujarCuerpo() {
        pnlDetalles.setLayout(new GridLayout(3, 4));
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));


        pnlBuscar.setLayout(new FlowLayout(FlowLayout.LEFT));
        txtCodigo.setPreferredSize(new Dimension(150,24));
        pnlBuscar.add(new JLabel("Codigo Producto"));
        pnlBuscar.add(txtCodigo);
        pnlBuscar.add(Box.createHorizontalStrut(310));
        pnlBuscar.add(btnBuscar);
        pnlBuscar.add(btnCarrito);


        pnlFiltrosYBuscar.setLayout(new GridLayout(1,0));
        pnlFiltrosYBuscar.add(pnlBuscar);

        pnlDetalles.setLayout(new FlowLayout());
        pnlDetalles.setPreferredSize(new Dimension(650,50));
        pnlDetalles.add(cantidadProductos);
        pnlDetalles.add(new JLabel(" Productos en el carrito"));
        pnlDetalles.add(btnCobrar);
        txtTotal.setPreferredSize(new Dimension(200,20));
        pnlDetalles.add(txtTotal);


        panelContenedor.add(pnlFiltrosYBuscar);
        panelContenedor.add(new JScrollPane(tablaProductos));
        panelContenedor.add(pnlDetalles);
    }

    private void configTable() {
        tablaProductos.setGridColor(new Color(88, 214, 141));
        tablaProductos.setPreferredScrollableViewportSize(new Dimension(800, 380));
    }
    public void initControl() {
        modeloProductos.setRowCount(0);
        String[] titulos = {"ID","CODIGO","NOMBRE","DESCRIPCION","STOCK","PRECIO","CATEGORIA"};
        modeloProductos.setColumnIdentifiers(titulos);
        String[] fila = new String[modeloProductos.getColumnCount()];
        ArrayList<Producto> lista = Util.productoArrayList;
        String estatus;
        for (Producto userTable : lista) {
            if (userTable.isEstado()){
                estatus = "Disponible";
            }else estatus = "No disponible";
            fila[0] = String.valueOf(userTable.getId());
            fila[1] = String.valueOf(userTable.getCodigoBarras());
            fila[2] = userTable.getNombre();
            fila[3] = userTable.getDescripcion();
            fila[4] = String.valueOf(userTable.getSotck());
            fila[5] = String.valueOf(userTable.getPrecio());
            fila[6] = userTable.getCategoria();
            if(estatus.equals("Disponible")){
                modeloProductos.addRow(fila);
            }
        }
    }
    public void initControlOrdenado(ArrayList<Producto> array){
        modeloProductos.setRowCount(0);
        modeloProductos.setColumnIdentifiers(Util.titulosTabla);
        String[] fila = new String[modeloProductos.getColumnCount()];
        ArrayList<Producto> lista = Util.productoArrayList;
        for (Producto userTable : lista) {
            fila[0] = String.valueOf(userTable.getId());
            fila[1] = String.valueOf(userTable.getCodigoBarras());
            fila[2] = userTable.getNombre();
            fila[3] = userTable.getDescripcion();
            fila[4] = String.valueOf(userTable.getSotck());
            fila[5] = String.valueOf(userTable.getStockMin());
            fila[6] = String.valueOf(userTable.getPrecio());
            fila[7] = userTable.getCategoria();
            fila[8] = String.valueOf(userTable.isEstado());
            if (!fila[4].equals("0")) {
                modeloProductos.addRow(fila);
            }
        }
    }

    private void addListeners(){
        btnCobrar.addActionListener(new ControladorVistaVenta(this,vistaPrincipal,null,ventaArrayList));
        btnBuscar.addActionListener(new ControladorVistaVenta(this,vistaPrincipal,null,ventaArrayList));
        btnCarrito.addActionListener(new ControladorVistaVenta(this,vistaPrincipal,null,ventaArrayList));
        txtCodigo.addActionListener(new ControladorVistaVenta(this,vistaPrincipal,null,ventaArrayList));
    }
    public JPanel getPanelContenedor() {
        return panelContenedor;
    }

    public void calcularTotal(){
        if(Util.carritoArrayList.isEmpty()){
            txtTotal.setText("$ 0");
            return;
        }
        total=0;
        for(Carrito carrito : Util.carritoArrayList){
            int cantidad = carrito.getCantidad();
            double precio = carrito.getProducto().getPrecio();
            double subtotal = cantidad * precio;
            total += subtotal;
        }
        cantidadProductos.setText(String.valueOf(Util.carritoArrayList.size()));
        txtTotal.setText("$ "+total);
        totalcpy = total;
    }

    public static class VistaCobrar extends JFrame{
        private JPanel pnlPrincipal,panel,pnlBtns;
        public JButton btnCobrar,btnCancelar;
        public JTextField txtPago;
        private JLabel lblTotal;
        private final VistaPrincipal vistaPrincipal;
        private final VistaVenta vistaVenta;
        public VistaCobrar(VistaVenta vistaVenta, VistaPrincipal vistaPrincipal){
            this.vistaVenta = vistaVenta;
            this.vistaPrincipal = vistaPrincipal;
            configFrame();
            crearComponentes2();
            dibujarCuerpo2();
            add(pnlPrincipal);
            addListeners2();
            lblTotal.setText(String.valueOf(totalcpy));
        }
        private void configFrame(){
            setTitle("COBRAR");
            setLayout(new FlowLayout());
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
            setResizable(false);
            setSize(new Dimension( 300,180));
            setLocationRelativeTo(null);
            toFront();
        }
        private void crearComponentes2(){
            pnlPrincipal = new JPanel();
            panel = new JPanel();
            pnlBtns = new JPanel();
            btnCobrar = new JButton("Cobrar");
            btnCancelar = new JButton("Cancelar");
            txtPago = new JTextField();
            lblTotal = new JLabel("$ ");
        }
        private void dibujarCuerpo2(){
            txtPago.setPreferredSize(new Dimension(30,25));
            panel.setLayout(new GridLayout(1,2));
            panel.add(new JLabel("Efectivo:"));
            panel.add(txtPago);

            pnlBtns.setLayout(new FlowLayout());
            pnlBtns.add(btnCancelar);
            pnlBtns.add(Box.createHorizontalStrut(25));
            pnlBtns.add(btnCobrar);

            pnlPrincipal.setLayout(new BoxLayout(pnlPrincipal,BoxLayout.Y_AXIS));
            pnlPrincipal.add(new JLabel("TOTAL $:"));
            pnlPrincipal.add(lblTotal);
            pnlPrincipal.add(Box.createVerticalStrut(20));
            pnlPrincipal.add(panel);
            pnlPrincipal.add(Box.createVerticalStrut(20));
            pnlPrincipal.add(pnlBtns);
        }
        private void addListeners2(){
            btnCancelar.addActionListener(new ControladorVistaVenta(vistaVenta,vistaPrincipal,this,VistaPrincipal.getArrayVentasDeUsuario()));
            btnCobrar.addActionListener(new ControladorVistaVenta(vistaVenta,vistaPrincipal,this,VistaPrincipal.getArrayVentasDeUsuario()));
        }
    }
}
