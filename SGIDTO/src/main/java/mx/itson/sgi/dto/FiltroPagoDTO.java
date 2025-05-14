package mx.itson.sgi.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FiltroPagoDTO {
    private LocalDate fechaDesde; 
    private LocalDate fechaHasta; 
    private Double montoDesde;    
    private Double montoHasta;    
    private String nombreCajero;
    private MetodosPagoDTO metodosoPago;
}
