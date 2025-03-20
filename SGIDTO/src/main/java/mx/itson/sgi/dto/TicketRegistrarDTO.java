package mx.itson.sgi.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class TicketRegistrarDTO {

	private String folio;
	private LocalDateTime fecha;
	private List<DetallePagoDTO> detalles;
	private Double montoTotal;
	private String nombreCajero;
	private String nombreAlumno;
	// private String mensaje;

	public TicketRegistrarDTO(String folio, LocalDateTime fecha, List<DetallePagoDTO> detalles, Double montoTotal,
			String nombreCajero, String nombreAlumno) {
		this.folio = folio;
		this.fecha = fecha;
		this.detalles = detalles;
		this.montoTotal = montoTotal;
		this.nombreCajero = nombreCajero;
		this.nombreAlumno = nombreAlumno;
	}

	
}