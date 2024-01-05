package Controller;

import DAO.CategoriaDAO;
import DAO.ProductoDAO;
import Models.pojo.Producto;
import Models.Validaciones;
import Util.Util;
import Views.VistaBuscar;
import Views.VistaPrincipal;
import Views.VistaVenta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorVistaBuscar implements ActionListener {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final VistaBuscar vistaBuscar;
    private final VistaPrincipal vistaPrincipal;
    private final VistaVenta vistaVenta;
    private final JInternalFrame frameInterno;
    private int indexT = -1;
    private int fila = -1;

    public ControladorVistaBuscar(VistaBuscar vistaBuscar, VistaPrincipal vistaPrincipal, JInternalFrame frameInterno,
                                  VistaVenta vistaVenta) {
        this.vistaBuscar = vistaBuscar;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaVenta = vistaVenta;
        this.frameInterno = frameInterno;
        listenerTabla();
    }

    private void listenerTabla() {
        vistaBuscar.tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            fila = vistaBuscar.tablaProductos.getSelectedRow();
            if (fila != -1) {
                Object idObj = vistaBuscar.tablaProductos.getValueAt(fila, 0);
                if (idObj != null) {
                    try {
                        String idString = (String) idObj;
                        indexT = Integer.parseInt(idString);
                    } catch (ClassCastException | NumberFormatException ex) {
                        indexT = -1;
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == vistaBuscar.btnBuscar){
            buscar();
        } else if (event == vistaBuscar.btnQuitar) {
            productoDAO.selectAll();
            vistaBuscar.initControl();
        } else if (event == vistaBuscar.btnModificar) {
            modificar();
        }else if(event == vistaBuscar.btnAgregar){
            agregarProducto();
        } else if (event == vistaBuscar.btnCancelar) {
            frameInterno.dispose();
        }
    }
    private void buscar(){
        boolean bandera =  true;
        String busqueda = vistaBuscar.txtBuscar.getText();
        int indexCombo = vistaBuscar.comboBusqueda.getSelectedIndex();
        switch (indexCombo){
            case 0 -> {
                if(!productoDAO.getValueBy(busqueda,"nombre_producto")){
                    JOptionPane.showMessageDialog(null,"NO SE ENCONTRO EL PRODUCTO",":/",JOptionPane.WARNING_MESSAGE);
                    bandera = false;
                }
            }
            case 1 -> {
                int idCategoria = categoriaDAO.getKeyValueByName(busqueda);
                if(!productoDAO.getValueBy(String.valueOf(idCategoria),"categoria_id")){
                    JOptionPane.showMessageDialog(null,"NO SE ENCONTRO LA CATEGORIA",":/",JOptionPane.WARNING_MESSAGE);
                    bandera = false;
                }
            }
            case 2 -> {
                if(!productoDAO.getValueBy(busqueda,"codbarras")){
                    JOptionPane.showMessageDialog(null,"NO SE ENCONTRO EL CODIGO",":/",JOptionPane.WARNING_MESSAGE);
                    bandera = false;
                }
            }
            case 3 -> {
                if (Validaciones.validarNumeros(busqueda,"ID")==-1) return;
                if(!productoDAO.getValueBy(busqueda,"id_producto")){
                    JOptionPane.showMessageDialog(null,"NO SE ENCONTRO EL ID",":/",JOptionPane.WARNING_MESSAGE);
                    bandera = false;
                }
            }
        }
        if (bandera){
            vistaBuscar.txtBuscar.setText("");
            vistaBuscar.initControl();
        }
    }
    private void modificar(){
        if(Validaciones.validarIndexTabla(fila)){
            Util.indexTablaModificar = fila;
            Producto producto = Util.productoArrayList.get(fila);
            vistaPrincipal.dibujarPanelProductos(producto,"Modificar producto");
        }
    }
    private void agregarProducto(){
        ControladorVistaVenta controladorVistaVenta = new ControladorVistaVenta(vistaVenta,vistaPrincipal,null,null);
        controladorVistaVenta.agregarCarrito(fila);
        frameInterno.dispose();
    }
}
