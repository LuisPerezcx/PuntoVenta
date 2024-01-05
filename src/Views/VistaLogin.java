package Views;

import Controller.ControladorLogin;
import Media.MiImagen;
import Util.Util;

import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame {
    private JPanel panelContenedor, panelLogin,pnlArriba,pnlContenedorPrincipal, pnlLbls, pnlTitulos;
    private final MiImagen imagen;
    public JButton btnSalir, btnAceptar, btnRegistrarse;
    private JLabel usuario,contrasena;
    public JLabel titulo, titulo2, lblTipoUsuario;
    public JTextField txtUsuario;
    public JPasswordField txtContrasena;
    public JComboBox<String> tipoUsuario;
    private final VistaPrincipal vistaPrincipal;
    private final JInternalFrame frameLogin;
    public  VistaLogin(MiImagen imagen, VistaPrincipal vistaPrincipal, JInternalFrame frameLogin){
        this.imagen = imagen;
        this.vistaPrincipal = vistaPrincipal;
        this.frameLogin = frameLogin;
        initComponents();
        setPanelContainer();
        registrar();
        addListeners();
        add(pnlContenedorPrincipal);
        toFront();
    }
    public  VistaLogin(MiImagen imagen, VistaPrincipal vistaPrincipal, JInternalFrame frameLogin, boolean enVentana){
        this.imagen = imagen;
        this.vistaPrincipal = vistaPrincipal;
        this.frameLogin = frameLogin;
        initComponents();
        noRegistrado();
        initFrame();
        setPanelContainer();
        addListeners();
        add(pnlContenedorPrincipal);
        toFront();
    }
    private void initComponents(){
        panelContenedor = new JPanel();
        btnSalir = new JButton("Salir");
        btnAceptar = new JButton("Aceptar");
        panelLogin = new JPanel();
        pnlArriba = new JPanel();
        pnlContenedorPrincipal = new JPanel();
        pnlLbls = new JPanel();
        pnlTitulos = new JPanel();

        btnRegistrarse = new JButton("Registrarse");

        titulo = new JLabel("Iniciar Sesion");
        titulo2 = new JLabel("");
        usuario = new JLabel("Usuario: ");
        contrasena = new JLabel("Contrasena: ");
        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();

        String[] tipos = {"Cajero","Administrador"};
        tipoUsuario = new JComboBox<>(tipos);
        lblTipoUsuario = new JLabel("Tipo Usuario:");
    }
    private void initFrame(){
        setTitle("LOGIN ADMIN");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(380,420);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    private void setPanelContainer(){
        pnlContenedorPrincipal.setLayout(new BoxLayout(pnlContenedorPrincipal,BoxLayout.Y_AXIS));
        pnlArriba.setLayout(new FlowLayout(FlowLayout.RIGHT));

        pnlTitulos.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlLbls.setLayout(new GridLayout(2,0));
        pnlLbls.add(titulo);
        pnlLbls.add(titulo2);
        pnlTitulos.add(pnlLbls);

        panelContenedor.setLayout(new GridLayout(2,1));
        panelContenedor.setPreferredSize(new Dimension(300,300));
        panelContenedor.add(imagen);

        panelLogin.setLayout(new GridLayout(6,2));
        panelLogin.add(usuario);
        panelLogin.add(txtUsuario);
        panelLogin.add(contrasena);
        panelLogin.add(txtContrasena);
        panelLogin.add(lblTipoUsuario);
        panelLogin.add(tipoUsuario);
        panelLogin.add(new JLabel());
        panelLogin.add(new JLabel());
        panelLogin.add(btnSalir);
        panelLogin.add(btnAceptar);
        panelContenedor.add(panelLogin);

        pnlContenedorPrincipal.add(pnlArriba);
        pnlContenedorPrincipal.add(pnlTitulos);
        pnlContenedorPrincipal.add(panelContenedor);

        lblTipoUsuario.setVisible(false);
        tipoUsuario.setVisible(false);
    }
    private void addListeners(){
        btnSalir.addActionListener(new ControladorLogin(this,vistaPrincipal,frameLogin));
        btnAceptar.addActionListener(new ControladorLogin(this,vistaPrincipal,frameLogin));
        btnRegistrarse.addActionListener(new ControladorLogin(this,vistaPrincipal,frameLogin));
    }
    private void registrar(){
        titulo.setText("REGISTRAR USUARIO");
        btnRegistrarse.setVisible(false);
        lblTipoUsuario.setVisible(true);
        tipoUsuario.setVisible(true);
    }
    private void noRegistrado(){
        if (Util.usuariosArrayList.isEmpty()){
            titulo.setText("PARA EMPEZAR REGISTRE UN USUARIO");
            titulo2.setText("                    ADMINISTRADOR");
            btnRegistrarse.setVisible(false);
            tipoUsuario.setSelectedIndex(1);
            tipoUsuario.setEnabled(false);
        }
    }
    public JPanel getPnlContenedorPrincipal() {
        return pnlContenedorPrincipal;
    }
}

