package Models.pojo;

public class ProductoVendido {
    private final int cantidadProducto;
    private final int ventaID;
    private final int productoID;
    private final int usuarioID;

    public ProductoVendido(int cantidadProducto, int ventaID, int productoID, int usuarioID) {
        this.cantidadProducto = cantidadProducto;
        this.ventaID = ventaID;
        this.productoID = productoID;
        this.usuarioID = usuarioID;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public int getVentaID() {
        return ventaID;
    }

    public int getProductoID() {
        return productoID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }
}
