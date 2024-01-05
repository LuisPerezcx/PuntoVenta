package Models.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Venta {
    private final String folio;
    private final double totalVenta;
    private final Producto producto;
    private Map<Producto,Integer> productoMap = new HashMap<>();
    private final LocalDate fecha;
    private final LocalTime hora;
    private int cantidad;
    private double pago;
    private double cambio;
    private String usuario;

    public Venta(Producto producto, LocalDate fecha, LocalTime hora, int cantidad,double total,double pago, double cambio) {
        this.producto = producto;
        this.fecha = fecha;
        this.hora = hora;
        folio = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "_" + hora.format(DateTimeFormatter.ofPattern("HH-mm-ss"));
        this.cantidad = cantidad;
        this.pago = pago;
        this.cambio = cambio;
        totalVenta = total;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getCantidad(){
        return cantidad;
    }

    public Producto getProducto(){
        return producto;
    }
    public String getFolio() {
        return folio;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public String getHora() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
        return hora.format(formato);
    }
    public double getTotalVenta() {
        return totalVenta;
    }
    public double getPago() {
        return pago;
    }
    public double getCambio() {
        return cambio;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }
    public void setCambio(double cambio) {
        this.cambio = cambio;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public Map<Producto, Integer> getProductoMap() {
        return productoMap;
    }
    public void setProductoMap(Map<Producto,Integer> productoMap) {
        this.productoMap = productoMap;
    }
    public String getItemsInfo() {
        StringBuilder items = new StringBuilder();
        int contador = 1;
        for (Map.Entry<Producto, Integer> entry : productoMap.entrySet()) {
            Producto prod = entry.getKey();
            int cuantos = entry.getValue();
            double subtotal = cuantos * prod.getPrecio();

            items.append(contador).append(".- ").
                    append(entry.getValue()).append("  ").
                    append(entry.getKey().getNombre()).
                    append("-----$").append(subtotal).append("\n");
            contador++;
        }
        return items.toString();
    }
    @Override
    public String toString() {
        return "Folio: " + folio + "\nFecha:" + fecha + "\nHora:" + getHora() +
                "\nProductos:\n"+ getItemsInfo() + "Total: $" +totalVenta;
    }
}
