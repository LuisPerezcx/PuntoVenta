package DAO;

import Models.interfaces.OperacionesBD;
import Models.pojo.Producto;
import Models.pojo.ProductoVendido;
import Models.pojo.Usuarios;
import Models.pojo.Venta;
import Util.Util;
import Views.VistaPrincipal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class VentaDAO implements OperacionesBD<Venta> {
    private static ConexionBD conexionBD;
    private Usuarios usuarioLogeado;
    private static final String tabla = "venta";
    private static final String col_ID = "id_venta";
    private static final String col_Folio = "folio";
    private static final String col_Total_venta = "total_venta";
    private static final String col_Fecha = "fecha";
    private static final String col_Hora = "hora";
    private static final String col_Pago = "pago";
    private static final String col_Cambio = "cambio";
    private static final String col_CantidadProductos = "cantidad_productos";
    private static final String col_ProductoID = "producto_id";
    private static final String col_Usuario_ID = "usuario_id";
    private static final UsuariosDAO usuariosDAO = new UsuariosDAO();
    private static final ProductoDAO productosDAO = new ProductoDAO();
    private final ProductoVendidoDAO productoVendidoDAO = new ProductoVendidoDAO();

    private StringBuilder strFields;
    private StringBuilder strValues;
    private StringBuilder strWhere;
    public VentaDAO(){
        conexionBD = new ConexionBD();
    }
    private int idUsuarioLogeado;

    public void setUsuarioLogeado(Usuarios usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
        idUsuarioLogeado = usuariosDAO.getKeyValueByName(usuarioLogeado.getUsuario());
    }

    public void regresarProducto(int idVenta){
        ArrayList<ProductoVendido> productosVendidos = productoVendidoDAO.getArrayByVentaId(idVenta);
        System.out.println(productosVendidos);
        for(ProductoVendido p : productosVendidos){
            int idProducto = p.getProductoID();
            int stock = productosDAO.getStockProdcuto(idProducto);
            int cantidadVendidos = p.getCantidadProducto();
            int id_venta = p.getVentaID();
            int id_usuario = p.getUsuarioID();

            Producto producto = productosDAO.getValueByKey(idProducto);
            producto.setStock(stock+cantidadVendidos);
            productosDAO.update(producto,idProducto);

            productoVendidoDAO.delete(id_venta);
            delete(id_venta);
        }
    }

    public int getNextID(){
        int id = -1;
        int inseriones = 0;

        try {
            String count = "select count(*) as totalFilas from " + tabla;
            ResultSet resultSet = conexionBD.executeQuery(count);
            if (resultSet.next()){
                inseriones = resultSet.getInt(1);
                System.out.println(inseriones);
                id = 1;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        if (inseriones>=1){
            try {
                id = conexionBD.getLastInsertedID(tabla)+1;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        conexionBD.close();
        return id;
    }
    public ArrayList<Venta> getValuesByUsuarioKey(int key) {
        strWhere = new StringBuilder();
        strWhere.append(col_Usuario_ID + " = '" + key + "'");
        ArrayList<Venta> ventaArrayList = new ArrayList<>();
        Util.fechasArray = new ArrayList<>();
        Util.fechasArray.add(null);
        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,"");
             while (resultado.next()){
                int idProducto = resultado.getInt(9);
                //Producto producto = productosDAO.getValueByKey(idProducto);

                LocalDate fecha = resultado.getDate(4).toLocalDate();
                LocalTime hora = resultado.getTime(5).toLocalTime();
                int cantidadProductos = resultado.getInt(8);
                double total = resultado.getDouble(3);
                double pago = resultado.getDouble(6);
                double cambio = resultado.getDouble(7);

                ventaArrayList.add(new Venta(null,fecha,hora,cantidadProductos,total,pago,cambio));
                 if (!Util.fechasArray.contains(fecha)){
                     Util.fechasArray.add(fecha);
                 }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        conexionBD.close();
        return ventaArrayList;
    }

    @Override
    public Boolean insert(Venta venta) {
        if (venta == null){
            String query = "insert into " + tabla + " default values";
            conexionBD.executeQuery(query);
            conexionBD.close();
            return null;
        }
        strFields = new StringBuilder();
        strValues = new StringBuilder();

        strFields.append(col_Folio+",").append(col_Total_venta+",").append(col_Fecha+",").append(col_Hora+",")
                .append(col_Pago+",").append(col_Cambio+",").append(col_CantidadProductos+",").append(col_ProductoID+",")
                .append(col_Usuario_ID);

        strValues.append("'"+venta.getFolio()+"',");
        strValues.append("'"+venta.getTotalVenta()+"',");
        strValues.append("'"+venta.getFecha()+"',");
        strValues.append("'"+venta.getHora()+"',");
        strValues.append("'"+venta.getPago()+"',");
        strValues.append("'"+venta.getCambio()+"',");
        strValues.append("'"+venta.getCantidad()+"',");

        strValues.append(productosDAO.getKeyValueByName(venta.getProducto().getNombre())+",");
        strValues.append(idUsuarioLogeado);

        try {
            conexionBD.insertTabla(tabla,strFields.toString(),strValues.toString());
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean update(Venta venta, int id) {
        strFields = new StringBuilder();
        strWhere = new StringBuilder();

        int producto_id = venta.getProducto().getId();

        strFields.append(col_Folio + " = '" + venta.getFolio() + "',");
        strFields.append(col_Total_venta + " = '" + venta.getTotalVenta() + "',");
        strFields.append(col_Fecha + " = '" + venta.getFecha() + "',");
        strFields.append(col_Hora + " = '" + venta.getHora() + "',");
        strFields.append(col_Pago + " = '" + venta.getPago() + "',");
        strFields.append(col_Cambio + " = '" + venta.getCambio() + "',");
        strFields.append(col_CantidadProductos + " = " + venta.getCantidad() + ",");
        strFields.append(col_ProductoID + " = '" + producto_id + "',");
        strFields.append(col_Usuario_ID + " = '" + idUsuarioLogeado + "'");

        strWhere.append(" where " + col_ID + " = " + id);

        try {
            conexionBD.updateTabla(tabla,strFields,strWhere);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean delete(int id) {
        StringBuilder strDelete = new StringBuilder();
        strDelete.append(" where " + col_ID + " = " + id);

        try {
            conexionBD.deleteTabla(tabla,strDelete);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean selectByName(String name) {
        return null;
    }

    @Override
    public void selectAll() {
        strWhere = new StringBuilder();
        strWhere.append("");
        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,"");
            while (resultado.next()){
                int idProducto = resultado.getInt(9);
                Producto producto = productosDAO.getValueByKey(idProducto);
                LocalDate fecha = resultado.getDate(4).toLocalDate();
                LocalTime hora = resultado.getTime(5).toLocalTime();
                int cantidadProductos = resultado.getInt(8);
                double total = resultado.getDouble(3);
                double pago = resultado.getDouble(6);
                double cambio = resultado.getDouble(7);

                Venta venta = new Venta(producto,fecha,hora,cantidadProductos,total,pago,cambio);
                usuarioLogeado.getVentaArrayList().add(venta);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        conexionBD.close();
    }

    @Override
    public int getKeyValueByName(String folio) {
        strWhere = new StringBuilder();
        strWhere.append(col_Folio + " = '" + folio + "'");

        ResultSet resultado = conexionBD.selectTabla(tabla,col_ID,strWhere,col_ID,"");
        int id = -1;

        try {
            while (resultado.next()){
                id = resultado.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        conexionBD.close();
        return id;
    }

    @Override
    public Venta getValueByKey(int key) {
        strWhere = new StringBuilder();
        strWhere.append(col_ID + " = '" + key + "'");
        Venta venta = null;

        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,"");
            if (resultado.next()){
               // int idProducto = resultado.getInt(9);
                //Producto producto = productosDAO.getValueByKey(idProducto);
                LocalDate fecha = resultado.getDate(4).toLocalDate();
                LocalTime hora = resultado.getTime(5).toLocalTime();
                int cantidadProductos = resultado.getInt(8);
                double total = resultado.getDouble(3);
                double pago = resultado.getDouble(6);
                double cambio = resultado.getDouble(7);


                venta = new Venta(null,fecha,hora,cantidadProductos,total,pago,cambio);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        conexionBD.close();
        return venta;
    }
    public void selectTODAS() {
        strWhere = new StringBuilder();
        strWhere.append("");
        String join = "usuario on venta.usuario_id = usuario.id_usuario";
        Util.todasVentas = new ArrayList<>();
        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,join);
            while (resultado.next()){
                LocalDate fecha = resultado.getDate(4).toLocalDate();
                LocalTime hora = resultado.getTime(5).toLocalTime();
                int cantidadProductos = resultado.getInt(8);
                double total = resultado.getDouble(3);
                double pago = resultado.getDouble(6);
                double cambio = resultado.getDouble(7);
                String usuario = resultado.getString(12);

                Venta venta = new Venta(null,fecha,hora,cantidadProductos,total,pago,cambio);
                venta.setUsuario(usuario);
                Util.todasVentas.add(venta);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        conexionBD.close();
    }
}
