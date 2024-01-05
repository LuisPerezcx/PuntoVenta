package Views;

import Controller.ControladorVistaCorteCaja;
import DAO.VentaDAO;
import Models.pojo.Venta;
import Util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class VistaCorteCaja extends JFrame {
    private JPanel panelPrincipal,pnlIz,pnlDer,pnlJunto,pnlBusqueda,pnlDetalles,pnlCorteTotal;
    private JScrollPane scroll;
    public JTextArea textArea;
    public JTextField txtBusqueda, txtTotal, txtPago, txtCambio;
    public JTable tablaVentas, tablaDetalles;
    private JLabel lblFechas, lblTotal;
    public JButton btnCancelar, btnTodas;
    public DefaultTableModel ventasModel, detallesModel;
    public JComboBox<LocalDate> fechasCombo;
    private final VistaPrincipal vistaPrincipal;
    private final ArrayList<Venta> ventaArrayList;
    public boolean todas;
    public VistaCorteCaja(VistaPrincipal vistaPrincipal, ArrayList<Venta> ventaArrayList) {
        this.vistaPrincipal = vistaPrincipal;
        this.ventaArrayList = ventaArrayList;
        crearComponentes();
        hoy();
        initControlFecha();
        configTable();
        dibujarCuerpo();
        add(panelPrincipal);
        addListeners();
        this.todas = false;
    }
    public VistaCorteCaja(VistaPrincipal vistaPrincipal, ArrayList<Venta> ventaArrayList,boolean todas) {
        VentaDAO ventaDAO = new VentaDAO();
        ventaDAO.selectTODAS();
        this.vistaPrincipal = vistaPrincipal;
        this.ventaArrayList = ventaArrayList;
        this.todas = todas;
        crearComponentes();
        hoy();
        initControlFechaTodas();
        configTable();
        dibujarCuerpo();
        add(panelPrincipal);
        addListeners();
    }

    private void crearComponentes() {
        ventasModel = new DefaultTableModel();
        detallesModel = new DefaultTableModel();

        fechasCombo = new JComboBox<>(Util.fechasArray.toArray(new LocalDate[0]));

        tablaVentas = new JTable(ventasModel);
        tablaDetalles = new JTable(detallesModel);
        panelPrincipal = new JPanel();
        pnlIz = new JPanel();
        pnlDer = new JPanel();
        pnlJunto = new JPanel();
        pnlBusqueda = new JPanel();
        pnlDetalles = new JPanel();
        pnlCorteTotal = new JPanel();

        txtBusqueda = new JTextField();
        txtTotal = new JTextField();
        txtCambio = new JTextField();
        txtPago = new JTextField();

        lblFechas = new JLabel("Venta por fechas");
        lblTotal = new JLabel("");

        btnCancelar = new JButton("Cancelar venta");
        btnTodas = new JButton("Ver Todas");

        textArea = new JTextArea();
        scroll = new JScrollPane(textArea);
        textArea.setEditable(false);
    }

    private void dibujarCuerpo() {
        pnlBusqueda.setLayout(new FlowLayout());
        txtBusqueda.setPreferredSize(new Dimension(350,25));
        fechasCombo.setPreferredSize(new Dimension(280,25));
        pnlBusqueda.add(lblFechas);
        pnlBusqueda.add(fechasCombo);
        //pnlBusqueda.add(txtBusqueda);
        pnlBusqueda.add(btnTodas);

        txtTotal.setPreferredSize(new Dimension(30,25));
        txtPago.setPreferredSize(new Dimension(30,25));
        txtCambio.setPreferredSize(new Dimension(30,25));
        txtTotal.setEnabled(false);
        txtPago.setEnabled(false);
        txtCambio.setEnabled(false);

        pnlDetalles.setLayout(new GridLayout(3,2));
        pnlDetalles.add(new JLabel("Total:"));
        pnlDetalles.add(txtTotal);
        pnlDetalles.add(new JLabel("Pago:"));
        pnlDetalles.add(txtPago);
        pnlDetalles.add(new JLabel("Cambio:"));
        pnlDetalles.add(txtCambio);


        pnlIz.setLayout(new BoxLayout(pnlIz,BoxLayout.Y_AXIS));
        pnlIz.add(pnlBusqueda);
        pnlIz.add(new JScrollPane(tablaVentas));

        pnlDer.setLayout(new BoxLayout(pnlDer,BoxLayout.Y_AXIS));
        scroll.setPreferredSize(new Dimension(300, 300));
        pnlDer.add(scroll);
        //pnlDer.add(pnlDetalles);
        pnlDer.add(Box.createVerticalStrut(30));
        pnlCorteTotal.setLayout(new FlowLayout());
        pnlCorteTotal.add(btnCancelar);
        pnlCorteTotal.add(lblTotal);
        pnlDer.add(pnlCorteTotal);

        pnlJunto.setLayout(new FlowLayout());
        pnlJunto.add(pnlIz);
        pnlJunto.add(pnlDer);
        panelPrincipal.add(pnlJunto);
    }
    private void configTable() {
        tablaVentas.setGridColor(new Color(88, 214, 141));
        tablaVentas.setPreferredScrollableViewportSize(new Dimension(300, 320));
    }
    private void hoy(){
        LocalDate hoy = LocalDate.now();
        if (Util.fechasArray.contains(hoy)){
            System.out.println("fechasasas:"+hoy);
            fechasCombo.setSelectedItem(hoy);
        }
    }
    public void initControlFecha() {
        double total = 0;
        ventasModel.setRowCount(0);
        String[] titulos = {"FOLIO","ARTICULOS","FECHA","TOTAL"};
        ventasModel.setColumnIdentifiers(titulos);
        String[] fila = new String[ventasModel.getColumnCount()];
        for (Venta userTable : ventaArrayList) {
            fila[0] = userTable.getFolio();
            fila[1] = String.valueOf(userTable.getCantidad());
            fila[2] = String.valueOf(userTable.getFecha());
            fila[3] = String.valueOf(userTable.getTotalVenta());
            if(userTable.getFecha().equals(fechasCombo.getSelectedItem())){
                ventasModel.addRow(fila);
                total += userTable.getTotalVenta();
            }
        }
        lblTotal.setText("Corte de caja: " + total);
    }
    public void initControl(){
        double total = 0;
        ventasModel.setRowCount(0);
        String[] titulos = {"FOLIO","ARTICULOS","FECHA","TOTAL"};
        ventasModel.setColumnIdentifiers(titulos);
        String[] fila = new String[ventasModel.getColumnCount()];
        for (Venta userTable : ventaArrayList) {
            fila[0] = userTable.getFolio();
            fila[1] = String.valueOf(userTable.getCantidad());
            fila[2] = String.valueOf(userTable.getFecha());
            fila[3] = String.valueOf(userTable.getTotalVenta());
            total += userTable.getTotalVenta();
            ventasModel.addRow(fila);
        }
        lblTotal.setText("Corte de caja: " + total);
    }
    public void initControlTodas(){
        double total = 0;
        ventasModel.setRowCount(0);
        String[] titulos = {"FOLIO","USUARIO","ARTICULOS","FECHA","TOTAL"};
        ventasModel.setColumnIdentifiers(titulos);
        String[] fila = new String[ventasModel.getColumnCount()];
        for (Venta userTable : Util.todasVentas) {
            fila[0] = userTable.getFolio();
            fila[1] = userTable.getUsuario();
            fila[2] = String.valueOf(userTable.getCantidad());
            fila[3] = String.valueOf(userTable.getFecha());
            fila[4] = String.valueOf(userTable.getTotalVenta());
            total += userTable.getTotalVenta();
            ventasModel.addRow(fila);
        }
        lblTotal.setText("Corte de caja: " + total);
    }
    public void initControlFechaTodas() {
        double total = 0;
        ventasModel.setRowCount(0);
        String[] titulos = {"FOLIO","USUARIO","ARTICULOS","FECHA","TOTAL"};
        ventasModel.setColumnIdentifiers(titulos);
        String[] fila = new String[ventasModel.getColumnCount()];
        for (Venta userTable : Util.todasVentas) {
            fila[0] = userTable.getFolio();
            fila[1] = userTable.getUsuario();
            fila[2] = String.valueOf(userTable.getCantidad());
            fila[3] = String.valueOf(userTable.getFecha());
            fila[4] = String.valueOf(userTable.getTotalVenta());
            if(userTable.getFecha().equals(fechasCombo.getSelectedItem())){
                ventasModel.addRow(fila);
                total += userTable.getTotalVenta();
            }
        }
        lblTotal.setText("Corte de caja: " + total);
    }
    private void addListeners(){
        btnTodas.addActionListener(new ControladorVistaCorteCaja(this, vistaPrincipal, ventaArrayList));
        btnCancelar.addActionListener(new ControladorVistaCorteCaja(this, vistaPrincipal, ventaArrayList));
        fechasCombo.addItemListener(new ControladorVistaCorteCaja(this,vistaPrincipal,ventaArrayList));
    }
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
