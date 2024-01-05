package Util;

import DAO.ProductoDAO;
import DAO.UsuariosDAO;
import DAO.VentaDAO;
import Models.pojo.Carrito;
import Models.pojo.Producto;
import Models.pojo.Usuarios;
import Models.pojo.Venta;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Util {
    public static String RutaTickets = "C:\\Users\\david\\Desktop\\tickets\\";
    public static ProductoDAO productoDAO = new ProductoDAO();
    public static VentaDAO ventaDAO = new VentaDAO();
    public static UsuariosDAO usuariosDAO = new UsuariosDAO();
    public static ArrayList<Venta> todasVentas = new ArrayList<>();
    public static ArrayList<Producto> productoArrayList = new ArrayList<>();
    public static ArrayList<Carrito> carritoArrayList = new ArrayList<>();
    public static ArrayList<Usuarios> usuariosArrayList =new ArrayList<>();
    public static ArrayList<String> categoriaArray = new ArrayList<>();
    public static ArrayList<LocalDate> fechasArray = new ArrayList<>();
    public static String[] titulosTabla = {"ID","CÓDIGO","NOMBRE","DESCRIPCION","SOTCK","STOCK MIN.","PRECIO","CATEGORIA","ESTADO"};
    public static int indexTablaModificar = -1;
    public static int idProducto = productoDAO.getNextID();
    public static String nombreBD = "Tienda";


    public static ArrayList<Venta> generarArrayVentasUsuario(Usuarios usuario){
        int idUsuario = usuariosDAO.getKeyValueByName(usuario.getUsuario());
        return ventaDAO.getValuesByUsuarioKey(idUsuario);
    }
    public static class CustomCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component tabla = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            int stockValue = Integer.parseInt(table.getValueAt(row, 4).toString());
            int stockMinValue = Integer.parseInt(table.getValueAt(row, 5).toString());
            if (isSelected){
                tabla.setBackground(table.getSelectionBackground());
                tabla.setForeground(table.getSelectionForeground());
            }else if (stockValue <= stockMinValue) {
                tabla.setBackground(Color.YELLOW);
            } else {
                tabla.setBackground(table.getBackground());
                tabla.setForeground(table.getForeground());
            }
            return tabla;
        }
    }
}
