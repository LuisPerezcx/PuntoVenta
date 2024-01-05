package Views;


import Controller.ControladorVistaBuscar;
import Models.pojo.Producto;
import Util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class VistaBuscar extends JFrame {
    private JPanel panelContenedor, panelBoton, pnlDetalles, pnlFiltros, pnlTitulos, pnlFiltrosYBuscar, pnlBuscar;
    public JButton btnModificar, btnBuscar, btnQuitar, btnCancelar, btnAgregar;
    private JLabel cantidadlbl;
    public DefaultTableModel modeloProductos;
    public JTable tablaProductos;
    public JTextField txtCantidad, txtBuscar;
    public JComboBox<String> comboFiltros;
    public JComboBox<String> comboBusqueda;
    private final VistaPrincipal vistaPrincipal;
    private final VistaVenta vistaVenta;
    private final JInternalFrame frameInterno;

    public VistaBuscar(VistaPrincipal vistaPrincipal, JInternalFrame frameInterno, VistaVenta vistaVenta) {
        this.vistaPrincipal = vistaPrincipal;
        this.frameInterno = frameInterno;
        this.vistaVenta = vistaVenta;
        crearComponentes();
        initControl();
        configTable();
        dibujarCuerpo();
        add(panelContenedor);
        addListeners();
    }

    private void crearComponentes() {
        btnModificar = new JButton("Modificar");
        cantidadlbl = new JLabel("Cantidad de productos: ");
        btnBuscar = new JButton("Buscar");
        btnQuitar = new JButton("Quitar filtros");
        btnCancelar = new JButton("Cancelar");
        btnAgregar = new JButton("Agregar");


        modeloProductos = new DefaultTableModel();
        tablaProductos = new JTable(modeloProductos);

        panelContenedor = new JPanel();
        panelBoton = new JPanel();
        pnlDetalles = new JPanel();
        pnlFiltros = new JPanel();
        pnlTitulos = new JPanel();

        pnlFiltrosYBuscar = new JPanel();
        pnlBuscar = new JPanel();

        txtCantidad = new JTextField();
        txtBuscar = new JTextField();

        String[] filtros = {" ", "NOMBRE", "PRECIO MENOR-MAYOR", "PRECIO MAYOR-MENOR", "CATEGORIA"};
        String[] busqueda = {"NOMBRE","CATEGORIA","COD. BARRAS","ID"};
        comboBusqueda = new JComboBox<>(busqueda);
        comboFiltros = new JComboBox<>(filtros);

    }

    private void dibujarCuerpo() {
        panelBoton.setLayout(new FlowLayout());
        //panelBoton.add(cantidadlbl);
        //txtCantidad.setPreferredSize(new Dimension(100, 20));
        //panelBoton.add(txtCantidad);
        panelBoton.add(btnAgregar);
        panelBoton.add(btnModificar);
        panelBoton.add(btnCancelar);

        pnlDetalles.setLayout(new GridLayout(3, 4));

        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));


        pnlFiltros.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnlFiltros.add(btnQuitar);
        //pnlFiltros.add(comboFiltros);

        pnlBuscar.setLayout(new FlowLayout(FlowLayout.LEFT));
        txtBuscar.setPreferredSize(new Dimension(110, 24));
        pnlBuscar.add(comboBusqueda);
        pnlBuscar.add(txtBuscar);
        pnlBuscar.add(btnBuscar);
        //pnlBuscar.add(btnQuitar);


        pnlFiltrosYBuscar.setLayout(new GridLayout(1, 0));
        pnlFiltrosYBuscar.add(pnlBuscar);
        pnlFiltrosYBuscar.add(pnlFiltros);


        pnlTitulos.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlTitulos.add(new JLabel("BUSCAR"));

        panelContenedor.add(pnlTitulos);
        panelContenedor.add(pnlFiltrosYBuscar);
        panelContenedor.add(new JScrollPane(tablaProductos));
        panelContenedor.add(panelBoton);
        panelContenedor.add(pnlDetalles);
    }

    private void configTable() {
        tablaProductos.setGridColor(new Color(88, 214, 141));
        tablaProductos.setPreferredScrollableViewportSize(new Dimension(800, 380));
    }

    public void initControl() {
        modeloProductos.setRowCount(0);
        modeloProductos.setColumnIdentifiers(Util.titulosTabla);
        String[] fila = new String[modeloProductos.getColumnCount()];
        ArrayList<Producto> lista = Util.productoArrayList;
        String estatus;
        for (Producto userTable : lista) {
            if (userTable.isEstado()){
                estatus = "Disponible";
            }else estatus = "No disponible";
            fila[0] = String.valueOf(userTable.getId());
            fila[1] = String.valueOf(userTable.getCodigoBarras());
            fila[2] = userTable.getNombre();
            fila[3] = userTable.getDescripcion();
            fila[4] = String.valueOf(userTable.getSotck());
            fila[5] = String.valueOf(userTable.getStockMin());
            fila[6] = String.valueOf(userTable.getPrecio());
            fila[7] = userTable.getCategoria();
            fila[8] = estatus;
            modeloProductos.addRow(fila);
        }
        tablaProductos.setDefaultRenderer(Object.class, new Util.CustomCellRenderer());
    }

    public void initControlOrdenado(ArrayList<Producto> array) {
        modeloProductos.setRowCount(0);
        modeloProductos.setColumnIdentifiers(Util.titulosTabla);
        String[] fila = new String[modeloProductos.getColumnCount()];
        for (Producto userTable : array) {
            fila[0] = String.valueOf(userTable.getCodigoBarras());
            fila[1] = userTable.getNombre();
            fila[2] = String.valueOf(userTable.getSotck());
            fila[3] = String.valueOf(userTable.getPrecio());
            fila[4] = userTable.getCategoria();
            if (!fila[2].equals("0")) {
                modeloProductos.addRow(fila);
            }
        }
    }

    private void addListeners(){
        btnAgregar.addActionListener(new ControladorVistaBuscar(this,vistaPrincipal,frameInterno,vistaVenta));
        btnModificar.addActionListener(new ControladorVistaBuscar(this,vistaPrincipal,frameInterno,vistaVenta));
        btnBuscar.addActionListener(new ControladorVistaBuscar(this,vistaPrincipal,frameInterno,vistaVenta));
        btnQuitar.addActionListener(new ControladorVistaBuscar(this,vistaPrincipal,frameInterno,vistaVenta));
        btnCancelar.addActionListener(new ControladorVistaBuscar(this,vistaPrincipal,frameInterno,vistaVenta));
    }

    public JPanel getPanelContenedor() {
        return panelContenedor;
    }
}