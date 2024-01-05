package Views;

import Controller.ControladorVistaCategoria;
import DAO.CategoriaDAO;
import Util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaCaregorias extends JFrame {
    private JPanel panelContenedor, pnlBotones;
    public JButton agrearBtn, modificarBtn, eliminarBtn, regresarBtn,buscarBtn,quitarBtn;
    private JLabel nombreCatLbl;
    public JTextField txt;
    public DefaultTableModel modelCategorias;
    public JTable tablaCategorias;
    private final VistaPrincipal vistaPrincipal;
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    public VistaCaregorias(VistaPrincipal vistaPrincipal,String item){
        this.vistaPrincipal = vistaPrincipal;
        categoriaDAO.selectAll();
        crearComponentes();
        configTable();
        dibujarCuerpo();
        validarVista(item);
        add(panelContenedor);
        addListeners();
    }
    private void crearComponentes(){
        panelContenedor = new JPanel();
        pnlBotones = new JPanel();

        agrearBtn = new JButton("Agregar");
        buscarBtn = new JButton("Buscar");
        modificarBtn = new JButton("Modificar");
        eliminarBtn = new JButton("Eliminar");
        regresarBtn = new JButton("Regresar");
        quitarBtn = new JButton("Quitar busqueda");

        nombreCatLbl = new JLabel("Nombre de la categoria");
        txt = new JTextField();
        modelCategorias = new DefaultTableModel();
        modelCategorias.addColumn("ID");
        modelCategorias.addColumn("CATEGORIAS");
        tablaCategorias = new JTable(modelCategorias);
    }

    private void dibujarCuerpo(){
        txt.setPreferredSize(new Dimension(120,25));
        pnlBotones.setLayout(new FlowLayout());
        pnlBotones.add(nombreCatLbl);
        pnlBotones.add(txt,FlowLayout.CENTER);
        pnlBotones.add(agrearBtn);
        pnlBotones.add(modificarBtn);
        pnlBotones.add(eliminarBtn);
        pnlBotones.add(buscarBtn);
        pnlBotones.add(quitarBtn);
        pnlBotones.add(Box.createHorizontalStrut(250));
        pnlBotones.add(regresarBtn);

        panelContenedor.setLayout(new BorderLayout());
        panelContenedor.add(pnlBotones,BorderLayout.NORTH);
        panelContenedor.add(new JScrollPane(tablaCategorias));
    }
    public void configTable(){
        tablaCategorias.setGridColor(new Color(88, 214, 141));
        modelCategorias.setRowCount(0);
        String[] fila = new String[modelCategorias.getColumnCount()];
        int i = 1;
        for (String categoria : Util.categoriaArray){
            fila[0] = String.valueOf(categoriaDAO.getKeyValueByName(categoria));
            fila[1] = categoria;
            modelCategorias.addRow(fila);
        }
        tablaCategorias.getColumnModel().getColumn(0).setPreferredWidth(50);
    }
    private void validarVista(String item){
        switch (item) {
            case "Modificar categoria" -> {
                agrearBtn.setVisible(false);
                eliminarBtn.setVisible(false);
            }
            case "Nueva categoria" -> {
                modificarBtn.setVisible(false);
                eliminarBtn.setVisible(false);
                buscarBtn.setVisible(false);
                quitarBtn.setVisible(false);
            }
            case "Eliminar categoria" -> {
                agrearBtn.setVisible(false);
                modificarBtn.setVisible(false);
            }
            default -> {
                agrearBtn.setVisible(false);
                eliminarBtn.setVisible(false);
                modificarBtn.setVisible(false);
            }
        }
    }
    private void addListeners(){
        agrearBtn.addActionListener(new ControladorVistaCategoria(this, vistaPrincipal));
        modificarBtn.addActionListener(new ControladorVistaCategoria(this,vistaPrincipal));
        eliminarBtn.addActionListener(new ControladorVistaCategoria(this,vistaPrincipal));
        buscarBtn.addActionListener(new ControladorVistaCategoria(this,vistaPrincipal));
        regresarBtn.addActionListener(new ControladorVistaCategoria(this,vistaPrincipal));
        quitarBtn.addActionListener(new ControladorVistaCategoria(this,vistaPrincipal));
    }
    public JPanel getPanelContenedor() {
        return panelContenedor;
    }
}