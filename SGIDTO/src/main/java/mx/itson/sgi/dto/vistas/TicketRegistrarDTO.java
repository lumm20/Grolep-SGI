package mx.itson.sgi.dto.vistas;

import lombok.Data;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.UsuarioDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TicketRegistrarDTO {

    private BigDecimal montoTotal;
    private String folio;
    private LocalDate fecha;
    private LocalTime hora;
    private String metodoPago;
    private BigDecimal montoVencidos;
    private BigDecimal montoColegiatura;
    private BigDecimal montoInscripcion;
    private BigDecimal montoLibros;
    private BigDecimal montoEventos;
    private BigDecimal montoAcademias;
    private BigDecimal montoUniforme;
    private String tipoDescuento;
    private BigDecimal descuento;
    private BigDecimal subTotal;
    private AlumnoConsultaDTO alumno;
    private UsuarioDTO usuario;
    private CicloEscolarDTO ciclo;

}
