package DAO;

import Models.interfaces.OperacionesBD;
import Models.pojo.ProductoVendido;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoVendidoDAO implements OperacionesBD<ProductoVendido> {
    private static ConexionBD conexionBD;
    private static final String tabla = "producto_vendido";
    private static final String col_ID = "id_producto_vendido";
    private static final String col_CantidadProducto = "cantidad_productos";
    private static final String col_VentaID = "venta_id";
    private static final String col_ProductoID = "producto_id";
    private static final String col_UsuarioID = "usuario_id";
    private StringBuilder strFields;
    private StringBuilder strValues;
    private StringBuilder strWhere;

    public ProductoVendidoDAO() {
        conexionBD = new ConexionBD();
    }
    public ArrayList<ProductoVendido> getArrayByVentaId(int idVenta){
        strWhere = new StringBuilder();
        strWhere.append(col_VentaID + " = '" + idVenta +"'");
        ArrayList<ProductoVendido> productoVendidos = new ArrayList<>();
        try {
            ResultSet resultSet = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,"");
            while (resultSet.next()){
                int cantidadProducto = resultSet.getInt(2);
                int ventaID = resultSet.getInt(3);
                int productoID = resultSet.getInt(4);
                int usuarioID = resultSet.getInt(5);
                productoVendidos.add(new ProductoVendido(cantidadProducto,ventaID,productoID,usuarioID));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productoVendidos;
    }

    @Override
    public Boolean insert(ProductoVendido productoVendido) {
        strFields = new StringBuilder();
        strValues = new StringBuilder();

        strFields.append(col_CantidadProducto+",").append(col_VentaID+",").append(col_ProductoID+",").append(col_UsuarioID);

        strValues.append("'"+productoVendido.getCantidadProducto()+"',");
        strValues.append("'"+productoVendido.getVentaID()+"',");
        strValues.append("'"+productoVendido.getProductoID()+"',");
        strValues.append("'"+productoVendido.getUsuarioID()+"'");

        try {
            conexionBD.insertTabla(tabla,strFields.toString(),strValues.toString());
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean update(ProductoVendido productoVendido, int id) {
        return null;
    }

    @Override
    public Boolean delete(int id_venta) {
        StringBuilder strDelete = new StringBuilder();
        strDelete.append(" where " + col_VentaID + " = " + id_venta);

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

    }

    @Override
    public int getKeyValueByName(String name) {
        return 0;
    }

    @Override
    public ProductoVendido getValueByKey(int key) {
        return null;
    }
}
