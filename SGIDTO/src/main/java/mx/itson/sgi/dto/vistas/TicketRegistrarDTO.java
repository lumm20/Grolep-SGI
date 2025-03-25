package mx.itson.sgi.dto.vistas;

import lombok.Data;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.DetallePagoDTO;
import mx.itson.sgi.dto.MetodosPagoDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.google.gson.annotations.Expose;

@Data
public class TicketRegistrarDTO {

    @Expose
    private Double montoTotal;
    @Expose
    private String folio;
    @Expose(deserialize = false)
    private LocalDate fecha;
    @Expose(deserialize = false)
    private LocalTime hora;
    @Expose
    private AlumnoConsultaDTO alumno;
    @Expose
    private MetodosPagoDTO metodoPago;
    private Double montoVencidos;
    private Double montoColegiatura;
    private Double montoInscripcion;
    private Double montoLibros;
    private Double montoEventos;
    private Double montoAcademias;
    private Double montoUniforme;
    private String TipoDescuento;
    private String montoDescuento;
    @Expose
    private Long idUsuario;
    @Expose
    private CicloEscolarDTO ciclo;
	// private String mensaje;

    @Expose
    private List<DetallePagoDTO> detalles;

    public TicketRegistrarDTO() {
    }
	
}