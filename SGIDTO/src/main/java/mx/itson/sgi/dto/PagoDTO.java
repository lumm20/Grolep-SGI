package mx.itson.sgi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PagoDTO {

    private BigDecimal montoTotal;
    private String folio;
    private LocalDate fecha;
    private LocalTime hora;
    private String metodoPago;
    private String descuento;
    private AlumnoConsultaDTO alumno;
    private UsuarioDTO usuairo;

}
