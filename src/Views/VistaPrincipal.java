package Views;

import Controller.ControladorVistaPrincipal;
import DAO.*;
import Media.DisenoVistas;
import Media.MiImagen;
import Models.pojo.Producto;
import Models.pojo.Usuarios;
import Models.pojo.Venta;
import Util.Util;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

public class VistaPrincipal extends JFrame {
    private JDesktopPane ventana;
    private JInternalFrame productosIFrame, ventaIFrame, buscarIFrame, corteCajaIFrame, registrarIFrame;
    public JInternalFrame carritoIFrame,categoriaIFrame;
    public JButton btnNewUsuario, btnNewProducto, btnCorteCaja, btnVenta;
    private JToolBar toolBar;

    private JPanel pnlBienvenida;
    private JMenuBar menuBar, infoSesion;


    private JPanel contenedor, pnlImagen, pnlUsuario, pnlBotones, contPnlUsuario;
    public JLabel titulo, lblUsuario, lblTipoUsuario;
    public JButton productosBtn, ventasBtn, cajaBtn, salirBtn;
    private MiImagen imagen;


    //asjnask
    public JMenu archivoMenu, almacenMenu, productosItem, categoriaItem, ventMenu, operacionesMenu;
    public JMenuItem usuarioItem, salirItem, nuevoProducItem, modificarProductItem, listarProductItem, nuevaCategoriaItem,
            modificarCategoriaItem, eliminarCategoriaItem, listarCategoriaItem,
            ventasItem, cancelarVentaItem, corteCajaItem, historicoCajaItem;

    private static final ProductoDAO productoDAO = new ProductoDAO();
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static final UsuariosDAO usuarioDAO = new UsuariosDAO();
    private static Usuarios usuarioLogeado;
    private static ArrayList<Venta> arrayVentasDeUsuario;

    public VistaPrincipal() {
        super("Control Ventas");
        crearComponentes();
        crearBtnsImagenes();
        crearVentana();
        dibujarMenu();
        add(toolBar,BorderLayout.NORTH);
        add(infoSesion,BorderLayout.SOUTH);
        addListeners();
        usuarioDAO.selectAll();
        new VistaLogin(new MiImagen(100,0),this,null,true);
        setEnabled(false);
    }
    public void setUsuario(Usuarios usuario) {
        System.out.println("usuario logeado:"+usuario);
        VistaPrincipal.usuarioLogeado = usuario;
        tipoUsuario();
    }
    public static Usuarios getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public static ArrayList<Venta> getArrayVentasDeUsuario() {
        return arrayVentasDeUsuario;
    }

    private void crearComponentes() {
        ventana = new JDesktopPane();
        getContentPane().add(ventana, BorderLayout.CENTER);

        toolBar = new JToolBar();
        btnNewUsuario = new JButton("NuevoUsuario");
        btnNewProducto = new JButton("Producto");
        btnCorteCaja = new JButton("Corte");
        btnVenta = new JButton("venta");

        productosIFrame = new JInternalFrame();
        ventaIFrame = new JInternalFrame();
        buscarIFrame = new JInternalFrame();
        corteCajaIFrame = new JInternalFrame();
        registrarIFrame = new JInternalFrame();
        carritoIFrame = new JInternalFrame();
        categoriaIFrame = new JInternalFrame();

        pnlBienvenida = new JPanel();
        contPnlUsuario = new JPanel();

        menuBar = new JMenuBar();
        infoSesion = new JMenuBar();

        contenedor = new JPanel();
        pnlImagen = new JPanel();
        pnlUsuario = new JPanel();
        pnlBotones = new JPanel();
        imagen = new MiImagen(0,0);
        titulo = new JLabel("BIENVENID@");
        productosBtn = new JButton("Ver Productos");
        ventasBtn = new JButton("Realizar venta");
        cajaBtn = new JButton("Hacer corte de caja");
        salirBtn = new JButton("Salir");

        lblUsuario = new JLabel("INICIE SESION");
        lblTipoUsuario = new JLabel("");
    }

    private void crearVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setSize(840, 590);
        setLocationRelativeTo(null);
    }

    private void dibujarMenu() {
        archivoMenu = new JMenu("Archivo");
        usuarioItem = new JMenuItem("Usuario");
        salirItem = new JMenuItem("Salir");

        almacenMenu = new JMenu("Almacen");
        productosItem = new JMenu("Producto");
        nuevoProducItem = new JMenuItem("Nuevo producto");
        modificarProductItem = new JMenuItem("Modificar producto");
        listarProductItem = new JMenuItem("Listar productos");
        categoriaItem = new JMenu("Categoria");
        nuevaCategoriaItem = new JMenuItem("Nueva categoria");
        modificarCategoriaItem = new JMenuItem("Modificar categoria");
        eliminarCategoriaItem = new JMenuItem("Eliminar categoria");
        listarCategoriaItem = new JMenuItem("Listar categoria");

        ventMenu = new JMenu("Ventas");
        ventasItem = new JMenuItem("Nueva venta");
        cancelarVentaItem = new JMenuItem("Cancelar Venta");

        operacionesMenu = new JMenu("Operaciones");
        corteCajaItem = new JMenuItem("Corte de Caja");
        historicoCajaItem = new JMenuItem("Historico corte Caja");

        archivoMenu.add(usuarioItem);
        archivoMenu.addSeparator();
        archivoMenu.add(salirItem);

        almacenMenu.add(productosItem);
        almacenMenu.add(categoriaItem);

        productosItem.add(nuevoProducItem);
        productosItem.add(modificarProductItem);
        productosItem.add(listarProductItem);

        categoriaItem.add(nuevaCategoriaItem);
        categoriaItem.add(modificarCategoriaItem);
        categoriaItem.add(eliminarCategoriaItem);
        categoriaItem.add(listarCategoriaItem);

        ventMenu.add(ventasItem);
        ventMenu.add(cancelarVentaItem);

        operacionesMenu.add(corteCajaItem);
        operacionesMenu.add(historicoCajaItem);

        menuBar.add(archivoMenu);
        menuBar.add(almacenMenu);
        menuBar.add(ventMenu);
        menuBar.add(operacionesMenu);

        setJMenuBar(menuBar);

        toolBar.add(btnNewUsuario);
        toolBar.add(btnNewProducto);
        toolBar.add(btnVenta);
        toolBar.add(btnCorteCaja);
        toolBar.addSeparator();


        infoSesion.add(Box.createHorizontalStrut(8));
        infoSesion.add(lblUsuario);
        infoSesion.add(Box.createHorizontalStrut(30));
        infoSesion.add(lblTipoUsuario);
        infoSesion.add(Box.createHorizontalGlue());
    }
    private void tipoUsuario(){
        String tipoUsuario = usuarioLogeado.getTipoUsuario();
        if (tipoUsuario.equals("Administrador")){
            btnNewUsuario.setVisible(true);
            btnNewProducto.setVisible(true);
            almacenMenu.setVisible(true);
            cancelarVentaItem.setVisible(true);
            historicoCajaItem.setVisible(true);
            usuarioItem.setVisible(true);
        }else if (tipoUsuario.equals("Cajero")){
            btnNewUsuario.setVisible(false);
            btnNewProducto.setVisible(false);
            almacenMenu.setVisible(false);
            cancelarVentaItem.setVisible(false);
            historicoCajaItem.setVisible(false);
            usuarioItem.setVisible(false);
        }
    }
    public void dibujarPanelBienvenida() {
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));

        contenedor.add(Box.createVerticalStrut(50));

        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font(titulo.getFont().getName(),Font.PLAIN,30));
        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(10));
        pnlImagen.setLayout(new FlowLayout(FlowLayout.CENTER));
        imagen.setPreferredSize(new Dimension(128,128));
        pnlImagen.add(imagen);
        contenedor.add(pnlImagen);


        pnlUsuario.setLayout(new FlowLayout(FlowLayout.CENTER));
        contPnlUsuario.setLayout(new GridLayout(2,0));
        contPnlUsuario.add(lblUsuario);
        contPnlUsuario.add(lblTipoUsuario);

        pnlUsuario.add(contPnlUsuario);

        contenedor.add(pnlUsuario);
        contenedor.add(new JLabel(" "));
        contenedor.add(new JLabel(" "));

        pnlBotones.setLayout(new FlowLayout());
        pnlBotones.add(productosBtn);
        pnlBotones.add(ventasBtn);
        pnlBotones.add(cajaBtn);
        pnlBotones.add(salirBtn);

        contenedor.add(pnlBotones);
        pnlBienvenida.add(contenedor);
        setLocationRelativeTo(null);
    }

    public void dibujarPanelProductos(Producto producto, String btn) {
        categoriaDAO.selectAll();
        if (Util.categoriaArray.isEmpty()){
            JOptionPane.showMessageDialog(null,"Primero registra una categoria",":/",JOptionPane.WARNING_MESSAGE);
            return;
        }
        productosIFrame.dispose();
        productosIFrame = new JInternalFrame("Productos",true,true,true,true);
        try {
            productosIFrame.setMaximum(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        VistaProductos vistaProductos = new VistaProductos(this, producto,btn);
        if (producto == null){
            vistaProductos.txtId.setText(String.valueOf(Util.idProducto));
        }else{
            vistaProductos.txtId.setText(String.valueOf(productoDAO.getKeyValueByName(producto.getNombre())));
            vistaProductos.txtCodigoBarras.setText(String.valueOf(producto.getCodigoBarras()));
        }
        productosIFrame.getContentPane().add(vistaProductos.getPanelPrincipal());
        ventana.add(productosIFrame);
        productosIFrame.setVisible(true);
    }

    public void dibujarPanelBuscar(boolean showBtnModificar,boolean showBtnAgregar,VistaVenta vistaVenta) {
        productoDAO.selectAll();
        buscarIFrame.dispose();
        buscarIFrame = new JInternalFrame("Buscar",true,true,true,true);
        try {
            buscarIFrame.setMaximum(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        VistaBuscar vistaBuscar = new VistaBuscar(this,buscarIFrame,vistaVenta);
        vistaBuscar.btnModificar.setVisible(showBtnModificar);
        vistaBuscar.btnAgregar.setVisible(showBtnAgregar);
        buscarIFrame.getContentPane().add(vistaBuscar.getPanelContenedor());
        ventana.add(buscarIFrame);
        buscarIFrame.setVisible(true);
    }

    public void dibujarPanelVentas() {
        productoDAO.selectAll();
        ventaIFrame.dispose();
        ventaIFrame = new JInternalFrame("Realizar Venta",true,true,true,true);
        arrayVentasDeUsuario = Util.generarArrayVentasUsuario(usuarioLogeado);
        if (!ventaIFrame.isVisible()) {
            try {
                ventaIFrame.setMaximum(true);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
            VistaVenta vistaVenta = new VistaVenta(this);
            ventaIFrame.getContentPane().add(vistaVenta.getPanelContenedor());
            ventana.add(ventaIFrame);
            ventaIFrame.setVisible(true);
        } else ventaIFrame.toFront();
    }

    public void dibujarPanelCorteCaja(boolean todas) {
        VentaDAO ventaDAO = new VentaDAO();
        ventaDAO.setUsuarioLogeado(usuarioLogeado);
        arrayVentasDeUsuario = Util.generarArrayVentasUsuario(usuarioLogeado);
        corteCajaIFrame.dispose();
        JInternalFrame corteVista = new JInternalFrame("Realizar Corte de Caja", true, true, true, true);
        corteCajaIFrame = corteVista;
        try {
            corteCajaIFrame.setMaximum(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        VistaCorteCaja vistaCorteCaja;
        if (todas){
            vistaCorteCaja = new VistaCorteCaja(this,arrayVentasDeUsuario,todas);
            corteVista.setTitle("Realizar Corte de Caja Historico");
        }else {
            vistaCorteCaja = new VistaCorteCaja(this,arrayVentasDeUsuario);
        }
        corteCajaIFrame.getContentPane().add(vistaCorteCaja.getPanelPrincipal());
        ventana.add(corteCajaIFrame);
        corteCajaIFrame.setVisible(true);
    }

    public void dibujarPanelLogin(){
        registrarIFrame.dispose();
        registrarIFrame = new JInternalFrame("Registrar",true,true,true,true);
        if(!registrarIFrame.isVisible()){
            try {
                registrarIFrame.setMaximum(true);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
            VistaLogin vistaLogin = new VistaLogin(new MiImagen(350,0),this,registrarIFrame);
            registrarIFrame.getContentPane().add(vistaLogin.getPnlContenedorPrincipal());
            ventana.add(registrarIFrame);
            registrarIFrame.setVisible(true);
        } else registrarIFrame.toFront();
    }
    public void dibujarPanelCarrito(){
        carritoIFrame.dispose();
        carritoIFrame = new JInternalFrame("Carrito",true,true,true,true);
        if(!carritoIFrame.isVisible()){
            try {
                carritoIFrame.setMaximum(true);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
            VistaCarrito vistaCarrito = new VistaCarrito(this);
            carritoIFrame.getContentPane().add(vistaCarrito.getPanelContenedor());
            ventana.add(carritoIFrame);
            carritoIFrame.setVisible(true);
        } else carritoIFrame.toFront();
    }
    public void dibujarPnlCategorias(String item){
        categoriaIFrame.dispose();
        categoriaIFrame = new JInternalFrame("Carrito",true,true,true,true);
        if(!categoriaIFrame.isVisible()){
            try {
                categoriaIFrame.setMaximum(true);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
           VistaCaregorias vistaCategorias = new VistaCaregorias(this,item);
            categoriaIFrame.getContentPane().add(vistaCategorias.getPanelContenedor());
            ventana.add(categoriaIFrame);
            categoriaIFrame.setVisible(true);
        } else categoriaIFrame.toFront();
    }
    private void crearBtnsImagenes(){
        DisenoVistas disenoVistas = new DisenoVistas();
        btnNewUsuario = disenoVistas.imagenBoton("","Media/Imagenes/nuevo-usuario.png",40,40);
        btnNewProducto = disenoVistas.imagenBoton("","Media/Imagenes/agregar-producto.png",40,40);
        btnCorteCaja = disenoVistas.imagenBoton("","Media/Imagenes/caja-registradora.png",40,40);
        btnVenta = disenoVistas.imagenBoton("","Media/Imagenes/ventas.png",40,40);
    }

    private void addListeners() {
        usuarioItem.addActionListener(new ControladorVistaPrincipal(this));
        salirItem.addActionListener(new ControladorVistaPrincipal(this));

        nuevoProducItem.addActionListener(new ControladorVistaPrincipal(this));
        modificarProductItem.addActionListener(new ControladorVistaPrincipal(this));
        listarProductItem.addActionListener(new ControladorVistaPrincipal(this));

        nuevaCategoriaItem.addActionListener(new ControladorVistaPrincipal(this));
        modificarCategoriaItem.addActionListener(new ControladorVistaPrincipal(this));
        eliminarCategoriaItem.addActionListener(new ControladorVistaPrincipal(this));
        listarCategoriaItem.addActionListener(new ControladorVistaPrincipal(this));

        ventasItem.addActionListener(new ControladorVistaPrincipal(this));
        cancelarVentaItem.addActionListener(new ControladorVistaPrincipal(this));

        corteCajaItem.addActionListener(new ControladorVistaPrincipal(this));
        historicoCajaItem.addActionListener(new ControladorVistaPrincipal(this));


        productosBtn.addActionListener(new ControladorVistaPrincipal(this));
        ventasBtn.addActionListener(new ControladorVistaPrincipal(this));
        cajaBtn.addActionListener(new ControladorVistaPrincipal(this));
        salirBtn.addActionListener(new ControladorVistaPrincipal(this));

        btnNewProducto.addActionListener(new ControladorVistaPrincipal(this));
        btnNewUsuario.addActionListener(new ControladorVistaPrincipal(this));
        btnCorteCaja.addActionListener(new ControladorVistaPrincipal(this));
        btnVenta.addActionListener(new ControladorVistaPrincipal(this));
    }
}
