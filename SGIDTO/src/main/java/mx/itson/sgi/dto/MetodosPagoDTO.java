package mx.itson.sgi.dto;

public enum MetodosPagoDTO {
    Transferencia,
    Efectivo,
    Tarjeta,;


    public static MetodosPagoDTO fromString(String value) {
        switch (value.toUpperCase()) {
            case "TRANSFERENCIA":
                return Transferencia;
            case "EFECTIVO":
                return Efectivo;
            case "TARJETA":
                return Tarjeta;
            default:
                throw new IllegalArgumentException("Método de pago no válido: " + value);
        }
    }
}
