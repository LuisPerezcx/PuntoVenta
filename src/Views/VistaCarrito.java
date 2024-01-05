package Views;

import Controller.ControladorVistaCarrito;
import Media.DisenoVistas;
import Models.pojo.Carrito;
import Models.pojo.Producto;
import Util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VistaCarrito extends JFrame {
    private JPanel panelContenedor, pnlDetalles,pnlFiltrosYBuscar,pnlBuscar;
    public JButton btnRegresar, btnEliminar;
    public DefaultTableModel modeloProductos;
    public JTable tablaProductos;
    public JLabel txtTotal;
    private final VistaPrincipal vistaPrincipal;

    public VistaCarrito(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        crearComponentes();
        initControl();
        configTable();
        dibujarCuerpo();
        add(panelContenedor);
        addListeners();
        calcularTotal();
    }


    private void crearComponentes() {
        DisenoVistas disenoVistas = new DisenoVistas();
        btnRegresar = new JButton("Regresar");
        btnEliminar = disenoVistas.imagenBoton("Quitar","Media/Imagenes/menos.png",15,15);
        modeloProductos = new DefaultTableModel();
        tablaProductos = new JTable(modeloProductos);

        panelContenedor = new JPanel();
        pnlDetalles = new JPanel();

        pnlFiltrosYBuscar = new JPanel();
        pnlBuscar = new JPanel();

        txtTotal = new JLabel();

    }

    private void dibujarCuerpo() {
        pnlDetalles.setLayout(new GridLayout(3, 4));
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));


        pnlBuscar.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlBuscar.add(Box.createHorizontalStrut(370));
        pnlBuscar.add(new JLabel("CARRITO"));
        pnlBuscar.add(Box.createHorizontalStrut(250));
        pnlBuscar.add(btnEliminar);


        pnlFiltrosYBuscar.setLayout(new GridLayout(1,0));
        pnlFiltrosYBuscar.add(pnlBuscar);

        pnlDetalles.setLayout(new FlowLayout());
        pnlDetalles.setPreferredSize(new Dimension(650,50));
        pnlDetalles.add(new JLabel("Total :"));
        txtTotal.setPreferredSize(new Dimension(100,20));
        pnlDetalles.add(txtTotal);
        pnlDetalles.add(Box.createHorizontalStrut(550));
        pnlDetalles.add(btnRegresar);


        panelContenedor.add(pnlFiltrosYBuscar);
        panelContenedor.add(new JScrollPane(tablaProductos));
        panelContenedor.add(pnlDetalles);
    }

    private void configTable() {
        tablaProductos.setGridColor(new Color(88, 214, 141));
        tablaProductos.setPreferredScrollableViewportSize(new Dimension(800, 380));
    }
    public void initControl() {
        modeloProductos.setRowCount(0);
        String[] titulos = {"Nombre","Precio (c/u)","Cant. Prod.","Subtotal"};
        modeloProductos.setColumnIdentifiers(titulos);
        String[] fila = new String[modeloProductos.getColumnCount()];
        ArrayList<Carrito> lista = Util.carritoArrayList;
        for (Carrito userTable : lista) {
            int cantidad = userTable.getCantidad();
            double precio = userTable.getProducto().getPrecio();
            fila[0] = userTable.getProducto().getNombre();
            fila[1] = String.valueOf(userTable.getProducto().getPrecio());
            fila[2] = String.valueOf(userTable.getCantidad());
            fila[3] = String.valueOf(cantidad*precio);
            modeloProductos.addRow(fila);
        }
    }
    public void initControlOrdenado(ArrayList<Producto> array){
        modeloProductos.setRowCount(0);
        modeloProductos.setColumnIdentifiers(Util.titulosTabla);
        String[] fila = new String[modeloProductos.getColumnCount()];
        ArrayList<Producto> lista = Util.productoArrayList;
        for (Producto userTable : lista) {
            fila[0] = String.valueOf(userTable.getId());
            fila[1] = String.valueOf(userTable.getCodigoBarras());
            fila[2] = userTable.getNombre();
            fila[3] = userTable.getDescripcion();
            fila[4] = String.valueOf(userTable.getSotck());
            fila[5] = String.valueOf(userTable.getStockMin());
            fila[6] = String.valueOf(userTable.getPrecio());
            fila[7] = userTable.getCategoria();
            fila[8] = String.valueOf(userTable.isEstado());
            if (!fila[4].equals("0")) {
                modeloProductos.addRow(fila);
            }
        }
    }
    public void calcularTotal(){
        if(Util.carritoArrayList.isEmpty()){
            txtTotal.setText("$ 0");
            return;
        }
        double total=0;
        for(Carrito carrito : Util.carritoArrayList){
            int cantidad = carrito.getCantidad();
            double precio = carrito.getProducto().getPrecio();
            double subtotal = cantidad * precio;
            total += subtotal;
        }
        txtTotal.setText("$"+total);
    }

    private void addListeners(){
        btnRegresar.addActionListener(new ControladorVistaCarrito(this,vistaPrincipal));
        btnEliminar.addActionListener(new ControladorVistaCarrito(this,vistaPrincipal));
    }
    public JPanel getPanelContenedor() {
        return panelContenedor;
    }
}
