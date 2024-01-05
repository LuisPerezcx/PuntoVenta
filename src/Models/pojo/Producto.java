package Models.pojo;

public class Producto {
    private int id;
    private final String nombre;
    private final String Descripcion;
    private int sotck;
    private final int StockMin;
    private final int codigoBarras;
    private final String categoria;
    private final boolean estado;
    private final double precio;
    private final int id_categoria;

    public Producto(int codigoBarras, String nombre, double precio, int sotck, int stockMin, String categoria, boolean estado, String descripcion, int id_categoria) {
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.precio = precio;
        this.sotck = sotck;
        this.StockMin = stockMin;
        this.estado = estado;
        this.categoria = categoria;
        this.Descripcion = descripcion;
        this.id_categoria = id_categoria;
    }

    public void setStock(int cantidad) {
        this.sotck = cantidad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public int getStockMin() {
        return StockMin;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getSotck() {
        return sotck;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public int getCodigoBarras() {
        return codigoBarras;
    }

    public String formatDescription() {
        String[] palabras = Descripcion.split("\\s+");
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < palabras.length; i++) {
            resultado.append(palabras[i]).append(" ");
            if ((i + 1) % 4 == 0) {
                resultado.append("\n");
            }
        }
        return resultado.toString().trim();
    }
    @Override
    public String toString() {
        String descripcionFormateada = formatDescription();
        return "Nombre: " + nombre + "\n" +
                "Descripcion:\n" + descripcionFormateada + "\n" +
                "Categoria: " + categoria + "\n" +
                "Precio: $" + precio + "\n" +
                "-----------------------------------------";
    }
}