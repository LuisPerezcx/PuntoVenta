package Views;

import Models.pojo.Usuarios;
import Models.pojo.Venta;

public class Ticket {
    private String ticketGenerado;
    public Ticket(Venta venta, String items, String fechayhora, Usuarios usuario) {

        this.ticketGenerado = "TIENDA {{nameLocal}}\n" +
                "DOMICILIO CONOCIDO OAXACA, OAX.\n" +
                "=============================\n" +
                "OAXACA, XXXXXXXXXXXX\n" +
                "RFC: XXX-020226-XX9\n" +
                "Caja # {{box}} - Ticket # {{ticket}}\n" +
                "LE ATENDIO: {{cajero}}\n" +
                "{{dateTime}}\n" +
                "=============================\n" +
                "{{items}}\n" +
                "=============================\n" +
                "SUBTOTAL: {{subTotal}}\n" +
                "TOTAL: {{total}}\n\n" +
                "RECIBIDO: {{recibo}}\n" +
                "CAMBIO: {{change}}\n\n" +
                "=============================\n" +
                "GRACIAS POR SU COMPRA...\n" +
                "ESPERAMOS SU VISITA NUEVAMENTE {{nameLocal}}\n" +
                "\n" +
                "\n";

        // Reemplazar placeholders con información de la venta
        this.ticketGenerado = this.ticketGenerado.replace("{{nameLocal}}", "UNSIJ"); // Reemplazar con el nombre real
        this.ticketGenerado = this.ticketGenerado.replace("{{box}}", "Caja1"); // Reemplazar con el número de caja real
        this.ticketGenerado = this.ticketGenerado.replace("{{ticket}}", venta.getFolio());
        this.ticketGenerado = this.ticketGenerado.replace("{{cajero}}", usuario.getUsuario()); // Reemplazar con el nombre real
        this.ticketGenerado = this.ticketGenerado.replace("{{dateTime}}", fechayhora);

        // Obtener información de productos y cantidades
        this.ticketGenerado = this.ticketGenerado.replace("{{items}}", items);

        // Reemplazar con información de la venta
        this.ticketGenerado = this.ticketGenerado.replace("{{subTotal}}", String.valueOf(venta.getTotalVenta())); // Reemplazar con el subtotal real
        this.ticketGenerado = this.ticketGenerado.replace("{{total}}", String.valueOf(venta.getTotalVenta())); // Reemplazar con el total real
        this.ticketGenerado = this.ticketGenerado.replace("{{recibo}}", String.valueOf(venta.getPago())); // Reemplazar con el monto recibido real
        this.ticketGenerado = this.ticketGenerado.replace("{{change}}", String.valueOf(venta.getCambio())); // Reemplazar con el cambio real
    }
    public String getTicketGenerado() {
        return ticketGenerado;
    }
}