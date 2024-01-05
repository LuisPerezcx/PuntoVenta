package DAO;

import Models.interfaces.OperacionesBD;
import Models.pojo.Producto;
import Util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDAO implements OperacionesBD<Producto> {
    private static  ConexionBD conexionBD;
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static final  String tabla = "producto";
    private static final String col_ID = "id_producto";
    private static final String col_NombreProducto = "nombre_producto";
    private static final String col_Descripcion = "descripcion";
    private static final String col_Stock = "stock";
    private static final String col_StockMin = "stockminimo";
    private static final String col_CategoriaID = "categoria_id";
    private static final String col_CodigoBarras = "codbarras";
    private static final String col_Estado = "estado";
    private static final String col_Precio = "precio";

    private StringBuilder strFields = new StringBuilder();
    private StringBuilder strValues;
    private StringBuilder strWhere;
    public ProductoDAO(){
        conexionBD = new ConexionBD();
    }
    public boolean getValueBy(String busqueda, String columna){
        boolean bandera = false;
        strFields = new StringBuilder();
        strWhere = new StringBuilder();
        strFields.append(tabla+".*,").append("categoria.nombre_categoria");
        strWhere.append(columna + " = '" + busqueda + "'");
        String join = "categoria on producto.categoria_id = categoria.id_categoria";
        ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,join);
        try {
            Util.productoArrayList = new ArrayList<>();
            while (resultado.next()){
                int id = resultado.getInt(1);
                int codBarras = resultado.getInt(6);
                String nombreProducto = resultado.getString(2);
                double precio = resultado.getDouble(8);
                int stock = resultado.getInt(4);
                int stockMin = resultado.getInt(5);
                boolean estado = resultado.getBoolean(7);
                String descripcion = resultado.getString(3);

                int id_categoria = resultado.getInt(9);
                String nombreCategoria = resultado.getString(11);
                System.out.println(nombreCategoria);

                Producto nuevo = new Producto(codBarras,nombreProducto,precio,stock,stockMin,nombreCategoria,estado,descripcion,id_categoria);
                nuevo.setId(id);

                Util.productoArrayList.add(nuevo);
                bandera = true;
            }
            conexionBD.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bandera;
    }
    public  int getStockProdcuto(int id){
        int stock = -1;
        strWhere = new StringBuilder();
        strWhere.append(col_ID + " = '" + id + "'");

        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,col_Stock,strWhere,col_ID,"");
            if (resultado.next()){
               stock = resultado.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        conexionBD.close();
        return stock;
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
    @Override
    public Boolean insert(Producto producto) {
        strFields = new StringBuilder();
        strValues = new StringBuilder();

        strFields.append(col_NombreProducto+",").append(col_Descripcion+",").append(col_Stock+",").append(col_StockMin+",")
                .append(col_CodigoBarras+",").append(col_Estado+",").append(col_Precio+",").append(col_CategoriaID);



        strValues.append("'"+producto.getNombre()+"',");
        strValues.append("'"+producto.getDescripcion()+"',");
        strValues.append("'"+producto.getSotck()+"',");
        strValues.append("'"+producto.getStockMin()+"',");
        strValues.append("'"+producto.getCodigoBarras()+"',");
        strValues.append("'"+producto.isEstado()+"',");
        strValues.append("'"+producto.getPrecio()+"',");

        strValues.append(categoriaDAO.getKeyValueByName(producto.getCategoria()));

        try {
            boolean exito = conexionBD.insertTabla(tabla,strFields.toString(), strValues.toString());
            if(exito){
                Util.idProducto = conexionBD.getLastInsertedID(tabla)+1;
                System.out.println(Util.idProducto);
                return  true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return  false;
        }
    }

    @Override
    public Boolean update(Producto producto, int id) {
        strFields = new StringBuilder();
        strWhere = new StringBuilder();

        strFields.append(col_NombreProducto + " = '" + producto.getNombre() + "',");
        strFields.append(col_Descripcion + " = '" + producto.getDescripcion() + "',");
        strFields.append(col_Stock + " = '" + producto.getSotck() + "',");
        strFields.append(col_StockMin + " = '" + producto.getStockMin() + "',");
        strFields.append(col_CategoriaID + " = '" + producto.getId_categoria() + "',");
        strFields.append(col_CodigoBarras + " = '" + producto.getCodigoBarras() + "',");
        strFields.append(col_Estado + " = " + producto.isEstado() + ",");
        strFields.append(col_Precio + " = '" + producto.getPrecio() + "'");

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
    public Boolean selectByName(String nombre) {
        strFields = new StringBuilder();
        strWhere = new StringBuilder();
        strFields.append(tabla+".*,").append("categoria.nombre_categoria");
        strWhere.append(col_NombreProducto + " = '" + nombre + "'");
        String join = "categoria on producto.categoria_id = categoria.id_categoria";
        ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,join);

        Util.categoriaArray = new ArrayList<>();

        try {
            while (resultado.next()){
                int id = resultado.getInt(1);
                int codBarras = resultado.getInt(6);
                String nombreProducto = resultado.getString(2);
                double precio = resultado.getDouble(8);
                int stock = resultado.getInt(4);
                int stockMin = resultado.getInt(5);
                boolean estado = resultado.getBoolean(7);
                String descripcion = resultado.getString(3);

                int id_categoria = resultado.getInt(9);
                String nombreCategoria = resultado.getString(10);

                Producto nuevo = new Producto(codBarras,nombreProducto,precio,stock,stockMin,nombreCategoria,estado,descripcion,id_categoria);
                nuevo.setId(id);

                Util.productoArrayList.add(nuevo);
            }
            conexionBD.close();
            return !Util.categoriaArray.isEmpty();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void selectAll() {
        strFields = new StringBuilder();
        strWhere = new StringBuilder();
        strFields.append(tabla+".*,").append("categoria.nombre_categoria");
        strWhere.append("");
        String join = "categoria on producto.categoria_id = categoria.id_categoria";
        ResultSet resultado = conexionBD.selectTabla(tabla,strFields.toString(),strWhere,col_ID,join);

        Util.productoArrayList = new ArrayList<>();

        try {
            while (resultado.next()){
                int id = resultado.getInt(1);
                int codBarras = resultado.getInt(6);
                String nombreProducto = resultado.getString(2);
                double precio = resultado.getDouble(8);
                int stock = resultado.getInt(4);
                int stockMin = resultado.getInt(5);
                boolean estado = resultado.getBoolean(7);
                String descripcion = resultado.getString(3);

                int id_categoria = resultado.getInt(9);
                String nombreCategoria = resultado.getString(10);

                Producto nuevo = new Producto(codBarras,nombreProducto,precio,stock,stockMin,nombreCategoria,estado,descripcion,id_categoria);
                nuevo.setId(id);

                Util.productoArrayList.add(nuevo);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        conexionBD.close();
    }

    @Override
    public int getKeyValueByName(String nombre) {
        strWhere = new StringBuilder();
        strWhere.append(col_NombreProducto + " = '" + nombre + "'");

        int id = -1;

        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,col_ID,strWhere,col_ID,"");
            if (resultado.next()){
                id = resultado.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        conexionBD.close();
        return id;
    }

    @Override
    public Producto getValueByKey(int key) {
        strFields = new StringBuilder();
        strWhere = new StringBuilder();
        strFields.append(tabla+".*,").append("categoria.nombre_categoria");
        strWhere.append(col_ID + " = '" + key + "'");
        String join = "categoria on producto.categoria_id = categoria.id_categoria";

        Producto producto = null;

        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,strFields.toString(),strWhere,col_ID,join);
            if (resultado.next()){
                int codBarras = resultado.getInt(6);
                String nombreProducto = resultado.getString(2);
                double precio = resultado.getDouble(8);
                int stock = resultado.getInt(4);
                int stockMin = resultado.getInt(5);
                boolean estado = resultado.getBoolean(7);
                String descripcion = resultado.getString(3);

                int id_categoria = resultado.getInt(9);
                String nombreCategoria = resultado.getString(10);

                producto = new Producto(codBarras,nombreProducto,precio,stock,stockMin,nombreCategoria,estado,descripcion,id_categoria);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        conexionBD.close();
        return producto;
    }
}
