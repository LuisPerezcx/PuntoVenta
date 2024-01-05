package Views;

import Controller.ControladorVistaProductos;
import Media.DisenoVistas;
import Models.pojo.Producto;
import Util.Util;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class VistaProductos extends JFrame{
    private final VistaPrincipal vistaPrincipal;
    private final Producto producto;
    private JPanel panelPrincipal,pnlLblsIz,pnltxtsIz,pnlLblsDer,pnltxtsDer,pnlIZ,pnlDer,pnlRadio,pnlCodigo;
    private JPanel pnlFormulario, pnlBtnsArriba,pnlBtnsAbajo;
    public JButton btnBuscar, btnNuevo, btnModificar, btnNuevoAb, btnGuardar, btnModificarAb, btnCancelar,btnEditar;
    private JLabel lblId, lblNombre, lblDescripcion, lblStock, lblStockMin, lblCodigoBarras, lblCategoria, lblEstado, lblPrecio;
    public JTextField txtId, txtNombre, txtStock, txtStockMin, txtCodigoBarras, txtPrecio;
    public JTextArea txtDescripcion;
    public JComboBox<String> comboCategorias;
    public JRadioButton btnActivo, btnInactivo;
    private ButtonGroup radioGroup;
    private final String btnPulsado;
    public VistaProductos(VistaPrincipal vistaPrincipal,Producto producto,String btn){
        this.vistaPrincipal = vistaPrincipal;
        this.producto = producto;
        this.btnPulsado = btn;
        crearComponentes();
        crearBtnsImagenes();
        dibujarFormulario();
        dibujarCuerpo();
        addListeners();
        validarBtn();
    }
    private void crearComponentes(){
        panelPrincipal = new JPanel();
        pnlLblsIz = new JPanel();
        pnltxtsIz = new JPanel();
        pnlLblsDer = new JPanel();
        pnltxtsDer = new JPanel();
        pnlIZ = new JPanel();
        pnlDer = new JPanel();
        pnlRadio = new JPanel();
        pnlFormulario = new JPanel();
        pnlBtnsArriba = new JPanel();
        pnlBtnsAbajo = new JPanel();
        pnlCodigo = new JPanel();

        lblId = new JLabel("ID:");
        lblNombre = new JLabel("Nombre:");
        lblDescripcion = new JLabel("Descripción:");
        lblStock = new JLabel("Stock:");
        lblStockMin = new JLabel("Stock Mínimo:");
        lblCodigoBarras = new JLabel("Código de Barras:");
        lblCategoria = new JLabel("Categoría:");
        lblEstado = new JLabel("Estado:");
        lblPrecio = new JLabel("Precio:");

        btnActivo = new JRadioButton();
        btnInactivo = new JRadioButton();
        radioGroup = new ButtonGroup();

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtDescripcion = new JTextArea();
        txtStock = new JTextField();
        txtStockMin = new JTextField();
        txtCodigoBarras = new JTextField();
        txtPrecio = new JTextField();

        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>(Util.categoriaArray.toArray(new String[0]));
        comboCategorias = new JComboBox<>(comboModel);

        btnBuscar = new JButton(" Buscar");
        btnNuevo = new JButton("Nuevo");
        btnModificar = new JButton("Modificar");
        btnNuevoAb = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnModificarAb = new JButton("Modificar");
        btnCancelar = new JButton("Cancelar");
        btnEditar = new JButton();

    }
    private void crearBtnsImagenes(){
        DisenoVistas disenoVistas = new DisenoVistas();
        btnBuscar = disenoVistas.imagenBoton("Buscar","Media/Imagenes/buscar.png",15,15);
        btnGuardar = disenoVistas.imagenBoton("Guardar","Media/Imagenes/disquete.png",15,15);
        btnModificar = disenoVistas.imagenBoton("Modificar","Media/Imagenes/editar.png",15,15);
        btnModificarAb = disenoVistas.imagenBoton("Modificar","Media/Imagenes/editar.png",15,15);
        btnNuevo = disenoVistas.imagenBoton("Agregar","Media/Imagenes/nuevo.png",15,15);
        btnNuevoAb = disenoVistas.imagenBoton("Agregar","Media/Imagenes/nuevo.png",15,15);
        btnCancelar = disenoVistas.imagenBoton("Cancelar","Media/Imagenes/cancelar.png",15,15);
        btnEditar = disenoVistas.imagenBoton("","Media/Imagenes/editar.png",15,15);
    }
    private void dibujarFormulario(){
        txtId.setEditable(false);
        txtCodigoBarras.setEditable(false);
        btnGuardar.setVisible(false);

        txtNombre.setMaximumSize(new Dimension(150,25));

        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        JScrollPane scrollPaneDescripcion = new JScrollPane(txtDescripcion);
        scrollPaneDescripcion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneDescripcion.setPreferredSize(new Dimension(150,50));

        radioGroup.add(btnActivo);
        radioGroup.add(btnInactivo);
        pnlRadio.setLayout(new FlowLayout());
        pnlRadio.add(new JLabel("Activo"));
        pnlRadio.add(btnActivo);
        pnlRadio.add(new JLabel("Inactivo"));
        pnlRadio.add(btnInactivo);

        pnlLblsIz.setLayout(new GridLayout(5,1));
        pnlLblsIz.add(lblId);
        pnlLblsIz.add(lblNombre);
        pnlLblsIz.add(lblDescripcion);
        pnlLblsIz.add(lblStock);
        pnlLblsIz.add(lblStockMin);

        pnltxtsIz.setLayout(new BoxLayout(pnltxtsIz,BoxLayout.Y_AXIS));
        pnltxtsIz.add(txtId);
        pnltxtsIz.add(txtNombre);
        pnltxtsIz.add(scrollPaneDescripcion);
        pnltxtsIz.add(txtStock);
        pnltxtsIz.add(txtStockMin);


        pnlLblsDer.setLayout(new GridLayout(5, 1));
        pnlLblsDer.add(lblCodigoBarras);
        pnlLblsDer.add(lblCategoria);
        pnlLblsDer.add(lblEstado);
        pnlLblsDer.add(lblPrecio);
        pnlLblsDer.add(new JLabel(" "));

        pnlCodigo.setLayout(new BoxLayout(pnlCodigo,BoxLayout.X_AXIS));
        pnlCodigo.add(txtCodigoBarras);
        pnlCodigo.add(Box.createHorizontalStrut(5));
        btnEditar.setPreferredSize(new Dimension(20,20));
        pnlCodigo.add(btnEditar);

        pnltxtsDer.setLayout(new GridLayout(5, 1));
        pnltxtsDer.add(pnlCodigo);
        pnltxtsDer.add(comboCategorias);
        pnltxtsDer.add(pnlRadio);
        pnltxtsDer.add(txtPrecio);
        pnltxtsDer.add(new JLabel(" "));
        pnltxtsDer.setPreferredSize(new Dimension(150,120));

        pnlIZ.setLayout(new GridLayout(1,1));
        pnlIZ.add(pnlLblsIz);
        pnlIZ.add(pnltxtsIz);
        pnlDer.setLayout(new GridLayout(1,1));
        pnlDer.add(pnlLblsDer);
        pnlDer.add(pnltxtsDer);

        pnlFormulario.setLayout(new FlowLayout());
        pnlFormulario.add(pnlIZ);
        pnlFormulario.add(Box.createHorizontalStrut(80));
        pnlFormulario.add(pnlDer);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Datos del Producto");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        pnlFormulario.setBorder(BorderFactory.createCompoundBorder(titledBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        infoProducto();
    }
    private void infoProducto(){
        if (producto != null){
            txtId.setText(String.valueOf(producto.getId()));
            txtCodigoBarras.setText(String.valueOf(producto.getCodigoBarras()));
            txtNombre.setText(producto.getNombre());
            txtDescripcion.setText(producto.getDescripcion());
            txtStock.setText(String.valueOf(producto.getSotck()));
            txtStockMin.setText(String.valueOf(producto.getStockMin()));
            comboCategorias.setSelectedItem(producto.getCategoria());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            if (producto.isEstado()){
                btnActivo.setSelected(true);
            }else btnInactivo.setSelected(true);
            btnGuardar.setVisible(true);
            btnModificar.setEnabled(false);
            btnModificarAb.setEnabled(false);
            btnBuscar.setEnabled(false);
            btnBuscar.setEnabled(false);
            btnNuevo.setEnabled(false);
            btnNuevoAb.setEnabled(false);
        }
    }
    private void dibujarCuerpo(){
        pnlBtnsArriba.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlBtnsArriba.add(btnBuscar);
        pnlBtnsArriba.add(btnNuevo);
        pnlBtnsArriba.add(btnModificar);

        pnlBtnsAbajo.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnlBtnsAbajo.add(btnNuevoAb);
        pnlBtnsAbajo.add(btnModificarAb);
        pnlBtnsAbajo.add(btnGuardar);
        pnlBtnsAbajo.add(btnCancelar);

        panelPrincipal.setLayout(new BoxLayout(panelPrincipal,BoxLayout.Y_AXIS));
        panelPrincipal.add(pnlBtnsArriba);
        panelPrincipal.add(pnlFormulario);
        panelPrincipal.add(pnlBtnsAbajo);
    }
    private void addListeners(){
        ControladorVistaProductos controladorVistaProductos = new ControladorVistaProductos(vistaPrincipal,this);
        btnBuscar.addActionListener(controladorVistaProductos);
        btnNuevo.addActionListener(controladorVistaProductos);
        btnModificar.addActionListener(controladorVistaProductos);
        btnNuevoAb.addActionListener(controladorVistaProductos);
        btnGuardar.addActionListener(controladorVistaProductos);
        btnModificarAb.addActionListener(controladorVistaProductos);
        btnCancelar.addActionListener(controladorVistaProductos);
        comboCategorias.addActionListener(controladorVistaProductos);
        btnEditar.addActionListener(controladorVistaProductos);
    }
    public void clear(){
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtStock.setText("");
        txtStockMin.setText("");
        txtCodigoBarras.setText("");
        txtPrecio.setText("");
    }
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    private void validarBtn(){
        if (btnPulsado != null){
            switch (btnPulsado){
                case "Nuevo producto" -> {
                    btnModificar.setVisible(false);
                    btnModificarAb.setVisible(false);
                    btnBuscar.setVisible(false);
                }
                case "Modificar producto" -> {
                    btnNuevo.setVisible(false);
                    btnNuevo.setVisible(false);
                    btnNuevoAb.setVisible(false);
                    btnBuscar.setVisible(false);
                }
                default -> {
                    btnModificar.setVisible(true);
                    btnModificarAb.setVisible(true);
                    btnBuscar.setVisible(true);
                    btnNuevo.setVisible(true);
                    btnNuevoAb.setVisible(true);
                }
            }
        }
    }
}
