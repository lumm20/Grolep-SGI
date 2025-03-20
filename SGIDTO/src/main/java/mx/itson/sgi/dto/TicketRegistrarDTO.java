package mx.itson.sgi.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class TicketRegistrarDTO {

    private Double montoTotal;
    private String folio;
    private LocalDate fecha;
    private LocalTime hora;
    private AlumnoConsultaDTO alumno;
    private String metodoPago;
    private Double montoVencidos;
    private Double montoColegiatura;
    private Double montoInscripcion;
    private Double montoLibros;
    private Double montoEventos;
    private Double montoAcademias;
    private Double montoUniforme;
    private String TipoDescuento;
    private String montoDescuento;
    private UsuarioDTO usuario;
    private CicloEscolarDTO ciclo;
	// private String mensaje;

    private List<DetallePagoDTO> detalles;

    public TicketRegistrarDTO() {
    }
	
}