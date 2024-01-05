package Controller;

import DAO.CategoriaDAO;
import DAO.ProductoDAO;
import Models.CRUD;
import Models.pojo.Producto;
import Models.Validaciones;
import Util.Util;
import Views.VistaPrincipal;
import Views.VistaProductos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

public class ControladorVistaProductos extends CRUD implements ActionListener {
    private final VistaPrincipal vistaPrincipal;
    private final VistaProductos vistaProductos;
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static final ProductoDAO productoDAO = new ProductoDAO();
    public ControladorVistaProductos(VistaPrincipal vistaPrincipal, VistaProductos vistaProductos) {
        this.vistaPrincipal = vistaPrincipal;
        this.vistaProductos = vistaProductos;
        generarCodigoBarras();
    }
    private void generarCodigoBarras(){
        String nombreCat = (String) vistaProductos.comboCategorias.getSelectedItem();
        int categoriaID = categoriaDAO.getKeyValueByName(nombreCat);
        Random random = new Random();
        int codigo = random.nextInt(10000,99999);
        String codBarras = categoriaID+"00"+codigo;
        vistaProductos.txtCodigoBarras.setText(codBarras);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == vistaProductos.btnBuscar){
            vistaPrincipal.dibujarPanelBuscar(false,false,null);
        } else if (event == vistaProductos.btnNuevo || event == vistaProductos.btnNuevoAb) {
            insertar();
        } else if (event == vistaProductos.btnModificar || event == vistaProductos.btnModificarAb) {
            vistaPrincipal.dibujarPanelBuscar(true,false,null);
        } else if (event == vistaProductos.btnGuardar){
            guardar();
        } else if (event == vistaProductos.btnCancelar) {
            vistaPrincipal.dibujarPanelProductos(null,"");
        } else if (event == vistaProductos.comboCategorias) {
            generarCodigoBarras();
        } else if (event == vistaProductos.btnEditar) {
            vistaProductos.txtCodigoBarras.setEditable(true);
        }
    }
    private void insertar(){
        Producto nuevo = nuevoProducto(true);
        if (nuevo != null){
            if(productoDAO.insert(nuevo)){
                JOptionPane.showMessageDialog(null,nuevo.getNombre()+" agregado");
                vistaPrincipal.dibujarPanelProductos(null,"");
            }else JOptionPane.showMessageDialog(null," error");
            //agregar(Util.productoArrayList, nuevo);
        }
    }
    private void guardar(){
        Producto p = Util.productoArrayList.get(Util.indexTablaModificar);
        int id = p.getId();
        Producto modificado = nuevoProducto(false);
        if (modificado!=null){
            productoDAO.update(modificado,id);


            //editar(Util.productoArrayList,Util.indexTablaModificar,modificado);
            Util.indexTablaModificar = -1;
            vistaPrincipal.dibujarPanelProductos(null,"");
            JOptionPane.showMessageDialog(null,"Editado");
        }
    }
    private Producto nuevoProducto(boolean validarNombreExistente){
        if(validarCamposCompletos()){
            String nombre = vistaProductos.txtNombre.getText();
            if(!Validaciones.validarNombreExistente(Util.productoArrayList,null,nombre,validarNombreExistente)) return null;
            String descripcion = vistaProductos.txtDescripcion.getText();
            int stock = Validaciones.validarNumeros(vistaProductos.txtStock.getText(),"Stock");
            int stockMin = Validaciones.validarNumeros(vistaProductos.txtStockMin.getText(),"Stock Min");
            int codigo = Validaciones.validarNumeros(vistaProductos.txtCodigoBarras.getText(),"Cod. Barras");
            String categoria = String.valueOf(vistaProductos.comboCategorias.getSelectedItem());
            boolean disponible = validarRadios();
            double precio = Validaciones.validarDouble(vistaProductos.txtPrecio.getText());

            int id_categoria = Integer.parseInt(String.valueOf(categoriaDAO.getKeyValueByName(categoria)));
            if (codigo==-1 || precio == -1 || stock ==-1 || stockMin == -1){
                return null;
            }else return  new Producto(codigo,nombre,precio,stock,stockMin,categoria,disponible,descripcion,id_categoria);

        }else return null;
    }

    private boolean validarRadios(){
        if (vistaProductos.btnActivo.isSelected() && !vistaProductos.btnInactivo.isSelected()){
            return true;
        }else if(vistaProductos.btnInactivo.isSelected() && !vistaProductos.btnActivo.isSelected()){
            return false;
        }
        return false;
    }

    private boolean validarCamposCompletos(){
        if (vistaProductos.txtNombre.getText().isEmpty() || vistaProductos.txtDescripcion.getText().isEmpty() || vistaProductos.txtStock.getText().isEmpty()
        || vistaProductos.txtStockMin.getText().isEmpty() || vistaProductos.txtCodigoBarras.getText().isEmpty() || vistaProductos.txtPrecio.getText().isEmpty()
        || (!vistaProductos.btnActivo.isSelected() && !vistaProductos.btnInactivo.isSelected())) {
            JOptionPane.showMessageDialog(null, "COMPLETA TODOS LOS CAMPOS",":/",JOptionPane.WARNING_MESSAGE);
            return false;
        }else return true;
    }
}
