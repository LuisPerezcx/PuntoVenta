package Controller;

import DAO.CategoriaDAO;
import Models.CRUD;
import Models.Validaciones;
import Util.Util;
import Views.VistaCaregorias;
import Views.VistaPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorVistaCategoria extends CRUD implements ActionListener {
    private final VistaCaregorias vistaCaregorias;
    private final VistaPrincipal vistaPrincipal;
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    int indexT = -1;

    public ControladorVistaCategoria(VistaCaregorias vistaCaregorias, VistaPrincipal vistaPrincipal) {
        this.vistaCaregorias = vistaCaregorias;
        this.vistaPrincipal = vistaPrincipal;
        listenerTabla();
    }
    private void listenerTabla() {
        vistaCaregorias.tablaCategorias.getSelectionModel().addListSelectionListener(e -> {
            int fila = vistaCaregorias.tablaCategorias.getSelectedRow();
            if (fila != -1) {
                Object idObj = vistaCaregorias.tablaCategorias.getValueAt(fila, 0);
                if (idObj != null) {
                    try {
                        String idString = (String) idObj;
                        indexT = Integer.parseInt(idString);
                        vistaCaregorias.txt.setText(Util.categoriaArray.get(fila));
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
        if (event == vistaCaregorias.regresarBtn){
            vistaPrincipal.categoriaIFrame.dispose();
        }else if (event == vistaCaregorias.agrearBtn){
            agregarCategoria();
        } else if (event == vistaCaregorias.modificarBtn) {
            modificar();
        }else if (event == vistaCaregorias.eliminarBtn){
            eliminar();
        } else if (event == vistaCaregorias.buscarBtn) {
            buscar();
        } else if (event == vistaCaregorias.quitarBtn) {
            quitarBusqueda();
        }
    }
    private void agregarCategoria(){
        String categoria = vistaCaregorias.txt.getText();
        if(categoria.isEmpty()){
            JOptionPane.showMessageDialog(null,"Ingresa el nombre de la categoria",":/",JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (String nombre : Util.categoriaArray){
            if (nombre.equals(categoria)){
                JOptionPane.showMessageDialog(null,"La categoria ya exitse",":/",JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        categoriaDAO.insert(categoria);
        categoriaDAO.selectAll();

        //agregar(Util.categoriaArray,categoria);
        vistaCaregorias.configTable();
        JOptionPane.showMessageDialog(null,"Categoria "+categoria+" agregada");
        vistaCaregorias.txt.setText("");
    }
    private void modificar(){
        if (Validaciones.validarIndexTabla(indexT)){
            String categoria = vistaCaregorias.txt.getText();
            if (categoria.isEmpty()){
                JOptionPane.showMessageDialog(null,"Ingresa el nombre de la categoria",":/",JOptionPane.WARNING_MESSAGE);
            }else {
                categoriaDAO.update(categoria,indexT);
                categoriaDAO.selectAll();

                //editar(Util.categoriaArray,indexT,categoria);
                vistaCaregorias.configTable();
                JOptionPane.showMessageDialog(null,"Categoria modificada");
                vistaCaregorias.txt.setText("");
            }
        }
        indexT = -1;
    }
    private void eliminar(){
        if (Validaciones.validarIndexTabla(indexT)){
            categoriaDAO.delete(indexT);
            categoriaDAO.selectAll();

            //eliminar(Util.categoriaArray,indexT);
            vistaCaregorias.configTable();
            JOptionPane.showMessageDialog(null,"Categoria eliminada");
            vistaCaregorias.txt.setText("");
        }
    }
    private void buscar(){
        String busqueda = vistaCaregorias.txt.getText();
        if(!busqueda.isEmpty()){
            if(categoriaDAO.selectByName(busqueda)){
                vistaCaregorias.configTable();
            }else JOptionPane.showMessageDialog(null,"No se encontro la categoria");
            vistaCaregorias.txt.setText("");
        }else {
            JOptionPane.showMessageDialog(null,"Ingresa un criterio de busqueda",":/",JOptionPane.WARNING_MESSAGE);
        }
    }
    private void quitarBusqueda(){
        categoriaDAO.selectAll();
        vistaCaregorias.configTable();
    }
}
