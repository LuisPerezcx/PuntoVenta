package Models.pojo;

public class Carrito {
    private final Producto producto;
    private int cantidad;
    private final int index;

    public Carrito(Producto producto, int cantidad, int index) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.index = index;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getIndex() {
        return index;
    }
}
