package Models.pojo;

import java.util.ArrayList;

public class Usuarios {
    private int id;
    private final String usuario;
    private final String contrasena;
    private final String tipoUsuario;
    private final ArrayList<Venta> ventaArrayList;

    public Usuarios(String usuario, String contrasena, String tipoUsuario) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        ventaArrayList = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUsuario() {
        return usuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public String getTipoUsuario(){return tipoUsuario;}
    public ArrayList<Venta> getVentaArrayList() {
        return ventaArrayList;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", ventaArrayList=" + ventaArrayList +
                '}';
    }
}
