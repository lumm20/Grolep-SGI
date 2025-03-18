package mx.itson.sgi.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class TicketRegistrarDTO {

    private BigDecimal montoTotal;
    private String folio;
    private LocalDate fecha;
    private LocalTime hora;
    private AlumnoConsultaDTO alumno;
    private String metodoPago;
    private BigDecimal montoVencidos;
    private BigDecimal montoColegiatura;
    private BigDecimal montoInscripcion;
    private BigDecimal montoLibros;
    private BigDecimal montoEventos;
    private BigDecimal montoAcademias;
    private BigDecimal montoUniforme;
    private String descuento;
    private UsuarioDTO usuario;

}
