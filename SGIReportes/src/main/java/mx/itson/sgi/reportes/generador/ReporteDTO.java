package mx.itson.sgi.reportes.generador;

import lombok.Data;
import mx.itson.sgi.dto.MetodosPagoDTO;

@Data
public class ReporteDTO {
    private String folio;
    private Double montoTotal;
    private MetodosPagoDTO metodoPago;
    private String fecha;
    private String matricula;
    private String nombre;
    private String tipoDescuento;
    private Double montoDescuento;
}
