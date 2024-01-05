package DAO;

import Models.interfaces.OperacionesBD;
import Util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//clase para crear los querys
public class CategoriaDAO implements OperacionesBD<String> {
    private static ConexionBD conexionBD;
    private static final String tabla = "categoria";
    private static final String col_ID = "id_categoria";
    private static final String col_NombreCategoria ="nombre_categoria";

    private StringBuilder strFields,strWhere;
    public CategoriaDAO(){
        conexionBD = new ConexionBD();
    }


    @Override
    public Boolean insert(String categoria) {
        StringBuilder strValues = new StringBuilder();
        strValues.append("'"+categoria+"'");
        try {
            conexionBD.insertTabla(tabla,col_NombreCategoria,strValues.toString());
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean update(String categoria, int id) {

        strFields = new StringBuilder();
        strWhere = new StringBuilder();

        strFields.append(col_NombreCategoria + " =  '" + categoria + "'");
        strWhere.append(" where " + col_ID + " = " + id);

        try {
            conexionBD.updateTabla(tabla, strFields,strWhere);
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
        strWhere = new StringBuilder();

        strWhere.append(col_NombreCategoria + " = '" + nombre + "'");
        ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,"");

        Util.categoriaArray = new ArrayList<>();

        try {
            while (resultado.next()){
                String categoria = resultado.getString(2);
                Util.categoriaArray.add(categoria);
            }
            return !Util.categoriaArray.isEmpty();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void selectAll() {
        strWhere = new StringBuilder();
        strWhere.append("");
        ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,"");

        Util.categoriaArray = new ArrayList<>();

        try {
            while (resultado.next()){
                String categoria = resultado.getString(2);
                Util.categoriaArray.add(categoria);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        conexionBD.close();
    }
    @Override
    public int getKeyValueByName(String nombre) {
       strWhere = new StringBuilder();
       strWhere.append(col_NombreCategoria + " = '" + nombre + "'");

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
    public String getValueByKey(int key) {
        strWhere = new StringBuilder();
        strWhere.append(col_ID + " = '" + key + "'");

        String nombreCategoria = "null";

        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,col_NombreCategoria,strWhere,col_NombreCategoria,"");
            if (resultado.next()){
                nombreCategoria = resultado.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        conexionBD.close();
        return nombreCategoria;
    }
}
