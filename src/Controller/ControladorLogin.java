package Controller;

import DAO.UsuariosDAO;
import Models.CRUD;
import Models.pojo.Usuarios;
import Models.Validaciones;
import Util.Util;
import Views.VistaLogin;
import Views.VistaPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorLogin extends CRUD implements ActionListener {
    private final VistaLogin vistaLogin;
    private final VistaPrincipal vistaPrincipal;
    private final JInternalFrame frameLogin;
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    public ControladorLogin(VistaLogin vistaLogin, VistaPrincipal vistaPrincipal, JInternalFrame frameLogin) {
        this.vistaLogin = vistaLogin;
        this.vistaPrincipal = vistaPrincipal;
        this.frameLogin = frameLogin;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == vistaLogin.btnSalir){
            if (frameLogin == null){
                vistaLogin.dispose();
                vistaPrincipal.dispose();
            } else {
                frameLogin.dispose();
            }
        }else if(event == vistaLogin.btnAceptar){
            if (Util.usuariosArrayList.isEmpty() || !vistaLogin.btnRegistrarse.isVisible()){
                primerUsuario();
            }else validarLogin();
        } else if (event == vistaLogin.btnRegistrarse) {
            registrarUsuario();
        }
    }
    private void validarLogin(){
        String usuario = vistaLogin.txtUsuario.getText();
        String contrasena = vistaLogin.txtContrasena.getText();
        if (!usuario.isEmpty() && !contrasena.isEmpty()){
            if (usuariosDAO.validarUsuario(usuario,contrasena)){
                int id = usuariosDAO.getKeyValueByName(usuario);
                Usuarios usuarios = usuariosDAO.getValueByKey(id);
                vistaPrincipal.setUsuario(usuarios);
                vistaPrincipal.lblUsuario.setText(usuarios.getUsuario());
                vistaPrincipal.lblTipoUsuario.setText(usuarios.getTipoUsuario());
                vistaLogin.dispose();
                vistaPrincipal.setEnabled(true);
                vistaPrincipal.toFront();
                vistaPrincipal.lblUsuario.setText(usuario);
                return;
            }
        }
        /*
        for (Usuarios usuarios : Util.usuariosArrayList){
            String usuarioValido = usuarios.getUsuario();
            String contrasenaValida = usuarios.getContrasena();
            if (usuario.equals(usuarioValido) && contrasena.equals(contrasenaValida)){
                String tipoUsr = usuarios.getTipoUsuario();
                vistaLogin.dispose();
                vistaPrincipal.setEnabled(true);
                vistaPrincipal.toFront();
                vistaPrincipal.lblUsuario.setText(usuario);
                vistaPrincipal.lblTipoUsuario.setText(tipoUsr);
                return;
            }
        }
         */
        JOptionPane.showMessageDialog(vistaLogin,"Credenciales invalidas",":(",JOptionPane.ERROR_MESSAGE);
    }
    private void registrarUsuario(){
        vistaLogin.titulo.setText("REGISTRAR USUARIO");
        vistaLogin.btnRegistrarse.setVisible(false);
        vistaLogin.lblTipoUsuario.setVisible(true);
        vistaLogin.tipoUsuario.setVisible(true);
    }
    private void primerUsuario(){
        agregarUsuario();
        vistaLogin.txtUsuario.setText("");
        vistaLogin.txtContrasena.setText("");
    }
    private void agregarUsuario(){
        String usuario = vistaLogin.txtUsuario.getText();
        if(!Validaciones.validarNombreExistente(null, Util.usuariosArrayList, usuario,true)) return;
        String contrasena = vistaLogin.txtContrasena.getText();
        String tipoUsr = String.valueOf(vistaLogin.tipoUsuario.getSelectedItem());
        if (!usuario.isEmpty() && !contrasena.isEmpty()){
            usuariosDAO.insert(new Usuarios(usuario,contrasena,tipoUsr));
            usuariosDAO.selectAll();
            //agregar(Util.usuariosArrayList, new Usuarios(usuario,contrasena,tipoUsr));
            JOptionPane.showMessageDialog(vistaLogin,"Usuario Registrado");
            if (frameLogin != null) return;
            vistaLogin.btnRegistrarse.setVisible(true);
            vistaLogin.lblTipoUsuario.setVisible(false);
            vistaLogin.tipoUsuario.setVisible(false);
            vistaLogin.titulo.setText("INICIE SESION");
            vistaLogin.titulo2.setText("");
            vistaLogin.tipoUsuario.setEnabled(true);
            vistaLogin.tipoUsuario.setSelectedIndex(0);
        }else JOptionPane.showMessageDialog(vistaLogin,"No debe haber campos vacios");
    }
}
