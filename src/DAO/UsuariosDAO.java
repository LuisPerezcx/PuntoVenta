package DAO;

import Models.interfaces.OperacionesBD;
import Models.pojo.Usuarios;
import Models.pojo.Venta;
import Util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuariosDAO implements OperacionesBD<Usuarios> {
    private static ConexionBD conexionBD;
    private static final String tabla = "usuario";
    private static final String col_ID = "id_usuario";
    private static final String col_Usuario = "usuario";
    private static final String col_Contrasena="contrasena";
    private static final String col_Puesto = "puesto";
    private StringBuilder strFields;
    private StringBuilder strValues;
    private StringBuilder strWhere;
    public UsuariosDAO(){
        conexionBD = new ConexionBD();
    }
    public boolean validarContrasenaAdmin(String contrasena){
        strFields = new StringBuilder();
        strValues = new StringBuilder();
        strWhere = new StringBuilder();

        strFields.append(col_Contrasena+",").append(col_Puesto);
        strWhere.append(col_Contrasena+" = '"+contrasena+"' and "+col_Puesto+" = 'Administrador'");

        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,strFields.toString(),strWhere,col_ID,"");
            if (resultado.next()){
                conexionBD.close();
                return true;
            }
            else return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean validarUsuario(String usuario, String contrasena){
        strFields = new StringBuilder();
        strValues = new StringBuilder();
        strWhere = new StringBuilder();

        strFields.append(col_Usuario + ",").append(col_Contrasena);
        strWhere.append(col_Usuario +" = '"+usuario+"' and "+col_Contrasena+" = '"+contrasena+"'");

        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,strFields.toString(),strWhere,col_ID,"");
            if (resultado.next()){
                conexionBD.close();
                return true;
            }
            else return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public Boolean insert(Usuarios usuarios) {
        strFields = new StringBuilder();
        strValues = new StringBuilder();

        strFields.append(col_Usuario+",").append(col_Contrasena+",").append(col_Puesto);

        strValues.append("'"+usuarios.getUsuario()+"',");
        strValues.append("'"+usuarios.getContrasena()+"',");
        strValues.append("'"+usuarios.getTipoUsuario()+"'");

        try {
            conexionBD.insertTabla(tabla,strFields.toString(),strValues.toString());
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean update(Usuarios usuarios, int id) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
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


        Util.usuariosArrayList = new ArrayList<>();

        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,"");
            while (resultado.next()){
                int id = resultado.getInt(1);
                String usuario = resultado.getString(2);
                String contrasena = resultado.getString(3);
                String tipoUsuario = resultado.getString(4);
                Usuarios nuevo = new Usuarios(usuario,contrasena,tipoUsuario);
                nuevo.setId(id);

                Util.usuariosArrayList.add(nuevo);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        conexionBD.close();
    }

    @Override
    public int getKeyValueByName(String nombre) {
        strWhere = new StringBuilder();
        strWhere.append(col_Usuario + " = '" + nombre + "'");
        ResultSet resultado = conexionBD.selectTabla(tabla,col_ID,strWhere,col_ID,"");
        int id = -1;
        try {
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
    public Usuarios getValueByKey(int key) {
        strWhere = new StringBuilder();
        strWhere.append(col_ID + " = '" + key + "'");
        Usuarios usuarios = null;

        try {
            ResultSet resultado = conexionBD.selectTabla(tabla,"*",strWhere,col_ID,"");
            if (resultado.next()){
                int id = resultado.getInt(1);
                String usuario = resultado.getString(2);
                String contrasena = resultado.getString(3);
                String tipoUsuario = resultado.getString(4);
                usuarios = new Usuarios(usuario,contrasena,tipoUsuario);
                usuarios.setId(id);

                Util.usuariosArrayList.add(usuarios);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        conexionBD.close();
        return usuarios;
    }
}
