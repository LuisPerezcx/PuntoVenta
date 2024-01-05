package Controller;

import DAO.UsuariosDAO;
import DAO.VentaDAO;
import Models.*;
import Models.pojo.Venta;
import Util.Util;
import Views.VistaCorteCaja;
import Views.VistaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ControladorVistaCorteCaja extends CRUD implements ActionListener, ItemListener {
    private static final UsuariosDAO usuariosDAO = new UsuariosDAO();
    private static final VentaDAO ventaDAO = new VentaDAO();
    private final VistaCorteCaja vistaCorteCaja;
    private final VistaPrincipal vistaPrincipal;
    private final ArrayList<Venta> ventaArrayList;
    private int indexT = -1;
    public ControladorVistaCorteCaja(VistaCorteCaja vistaCorteCaja, VistaPrincipal vistaPrincipal,
                                     ArrayList<Venta> ventaArrayList) {
        this.vistaCorteCaja = vistaCorteCaja;
        this.vistaPrincipal = vistaPrincipal;
        this.ventaArrayList = ventaArrayList;
        listenerTabla();
    }
    private void listenerTabla() {
        ListSelectionModel selectionModel = vistaCorteCaja.tablaVentas.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !selectionModel.isSelectionEmpty()) {
                indexT = selectionModel.getMinSelectionIndex();
                if (vistaCorteCaja.todas){
                    mostrarReciboTodos();
                }else mostrarRecibo();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == vistaCorteCaja.btnCancelar){
            regresarProductos();
        } else if (event == vistaCorteCaja.btnTodas) {
            vistaCorteCaja.fechasCombo.setSelectedIndex(0);
            if (vistaCorteCaja.todas){
                vistaCorteCaja.initControlTodas();
            }else vistaCorteCaja.initControl();
        }
    }
    private String dialogoContrasena(){
        String contra="";
        JLabel label = new JLabel("Ingresa contraseña de administrador:");
        JPasswordField passwordField = new JPasswordField();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.add(label);
        panel.add(passwordField);


        int option = JOptionPane.showConfirmDialog(null, panel, "Contraseña Admin:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            char[] passwordChars = passwordField.getPassword();
            contra = new String(passwordChars);
        }
        return contra;
    }
    private void regresarProductos() {
        if (ventaArrayList.isEmpty() && !Validaciones.validarIndexTabla(indexT))return;
        String contra = dialogoContrasena();
        if (!validarContrasena(contra))return;
        String folio = ventaArrayList.get(indexT).getFolio();
        ventaDAO.setUsuarioLogeado(VistaPrincipal.getUsuarioLogeado());
        int idVenta = ventaDAO.getKeyValueByName(folio);
        System.out.println(idVenta);
        ventaDAO.regresarProducto(idVenta);
        vistaPrincipal.dibujarPanelCorteCaja(false);
        JOptionPane.showMessageDialog(null,"Venta eliminada");
        //vistaPrincipal.dibujarPanelCorteCaja();
        /*
        Venta venta = ventaArrayList.get(indexT);
        for (Map.Entry<Producto, Integer> entry : venta.getProductoMap().entrySet()){
            int indexAnterior = ventaArrayList.get(indexT).getProducto().getId()-1;
            int cantidadAnterior = Util.productoArrayList.get(indexAnterior).getSotck();
            int cuantos = entry.getValue();
            Util.productoArrayList.get(indexAnterior).setStock(cantidadAnterior + cuantos);
        }
        JOptionPane.showMessageDialog(null,"VENTA ELIMINADA");
        eliminar(ventaArrayList, indexT);
        vistaPrincipal.dibujarPanelCorteCaja();
         */
    }
    private boolean validarContrasena(String contra){
        if (usuariosDAO.validarContrasenaAdmin(contra)){
            return true;
        }else{
            JOptionPane.showMessageDialog(null,"Credenciales invalidas",":(",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    private void mostrarRecibo(){
        System.out.println("index: " + indexT);
        String folio = ventaArrayList.get(indexT).getFolio();
        String rutaArchivo = Util.RutaTickets + folio + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            vistaCorteCaja.textArea.setText("");
            String linea;
            while ((linea = br.readLine()) != null) {
                vistaCorteCaja.textArea.append(linea + "\n");
            }
        } catch (IOException e) {
            System.out.println("No se puede mostrar el recibo\nCodigo de error: ");
            e.printStackTrace();
        }
    }
    private void mostrarReciboTodos(){
        System.out.println("index: " + indexT);
        String folio = Util.todasVentas.get(indexT).getFolio();
        String rutaArchivo = Util.RutaTickets + folio + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            vistaCorteCaja.textArea.setText("");
            String linea;
            while ((linea = br.readLine()) != null) {
                vistaCorteCaja.textArea.append(linea + "\n");
            }
        } catch (IOException e) {
            System.out.println("No se puede mostrar el recibo\nCodigo de error: ");
            e.printStackTrace();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (vistaCorteCaja.todas){
            vistaCorteCaja.initControlFechaTodas();
        }else vistaCorteCaja.initControlFecha();
    }
}
