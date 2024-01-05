package Controller;

import DAO.ProductoDAO;
import Models.CRUD;
import Models.Validaciones;
import Models.pojo.Producto;
import Util.Util;
import Views.VistaCarrito;
import Views.VistaPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorVistaCarrito extends CRUD implements ActionListener {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final VistaCarrito vistaCarrito;
    private final VistaPrincipal vistaPrincipal;
    private int indexT=-1;
    public ControladorVistaCarrito(VistaCarrito vistaCarrito, VistaPrincipal vistaPrincipal) {
        this.vistaCarrito = vistaCarrito;
        this.vistaPrincipal = vistaPrincipal;
        listenerTabla();
    }
    private void listenerTabla(){
        vistaCarrito.tablaProductos.getSelectionModel().addListSelectionListener(e ->{
            indexT = vistaCarrito.tablaProductos.getSelectedRow();
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == vistaCarrito.btnEliminar){
            try {
                eliminarProductoCarrito();
            }catch (NumberFormatException ignored){
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (event == vistaCarrito.btnRegresar) {
            vistaPrincipal.carritoIFrame.dispose();
        }
    }
    private void eliminarProductoCarrito (){
        if(Validaciones.validarIndexTabla(indexT)){
            String nombre = Util.carritoArrayList.get(indexT).getProducto().getNombre();
            int solicitados;
            try {
                solicitados = Integer.parseInt(JOptionPane.showInputDialog(null, "Cuantos " + nombre + " deseas eliminar", "CONFIRMACION", JOptionPane.INFORMATION_MESSAGE));
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null,"Entrada invalida",">:|",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int actuales = Util.carritoArrayList.get(indexT).getCantidad();
            int temIndex = indexT;
            if(Validaciones.validarCantidad(actuales, solicitados)){
                if(solicitados == actuales){
                    regresarProducto(solicitados,indexT);
                    eliminar(Util.carritoArrayList,indexT);
                }else {
                    Util.carritoArrayList.get(indexT).setCantidad((actuales- solicitados));
                    regresarProducto(solicitados, temIndex);
                }
                vistaPrincipal.dibujarPanelCarrito();
                vistaCarrito.calcularTotal();
            }
        }
    }
    private void regresarProducto(int cuantos, int index) {
        int indexAnterior = Util.carritoArrayList.get(index).getIndex();
        int cantidadAnterior = Util.productoArrayList.get(indexAnterior).getSotck();

        int idProducto = Util.carritoArrayList.get(index).getProducto().getId();
        Producto producto = productoDAO.getValueByKey(idProducto);
        producto.setStock(cantidadAnterior+cuantos);
        productoDAO.update(producto,idProducto);
        productoDAO.selectAll();
        vistaPrincipal.dibujarPanelVentas();
    }
}
