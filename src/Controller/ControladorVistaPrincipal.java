package Controller;

import Models.CRUD;
import Views.VistaPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorVistaPrincipal extends CRUD implements ActionListener {
    private final VistaPrincipal vistaPrincipal;
    private String item;
    public ControladorVistaPrincipal(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();

        if (event instanceof JMenuItem botonPulsado) {
            item = botonPulsado.getText();
            System.out.println("Botón pulsado: " + item);
        }


        if(event == vistaPrincipal.nuevaCategoriaItem || event == vistaPrincipal.modificarCategoriaItem || event == vistaPrincipal.eliminarCategoriaItem
        || event == vistaPrincipal.listarCategoriaItem){
            vistaPrincipal.dibujarPnlCategorias(item);
        }else if (event == vistaPrincipal.nuevoProducItem || event == vistaPrincipal.productosBtn || event == vistaPrincipal.btnNewProducto
        || event == vistaPrincipal.modificarProductItem){
            vistaPrincipal.dibujarPanelProductos(null,item);
        } else if (event == vistaPrincipal.ventasItem || event == vistaPrincipal.ventasBtn || event == vistaPrincipal.btnVenta) {
            vistaPrincipal.dibujarPanelVentas();
        } else if (event == vistaPrincipal.corteCajaItem || event == vistaPrincipal.cajaBtn || event == vistaPrincipal.btnCorteCaja
        || event == vistaPrincipal.cancelarVentaItem) {
            vistaPrincipal.dibujarPanelCorteCaja(false);
        } else if (event ==  vistaPrincipal.salirItem) {
            vistaPrincipal.dispose();
            new VistaPrincipal();
        } else if (event == vistaPrincipal.salirBtn) {
            vistaPrincipal.dispose();
        } else if (event == vistaPrincipal.listarProductItem) {
            vistaPrincipal.dibujarPanelBuscar(false,false,null);
        } else if (event == vistaPrincipal.btnNewUsuario || event == vistaPrincipal.usuarioItem) {
            vistaPrincipal.dibujarPanelLogin();
        } else if (event == vistaPrincipal.historicoCajaItem) {
            vistaPrincipal.dibujarPanelCorteCaja(true);
        }
    }
}

