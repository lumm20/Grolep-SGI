package mx.itson.sgi.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TicketRegistrarDTO {

	 private String folio;
	    private LocalDateTime fecha;
	    private Double monto;
	    private UsuarioDTO cajero;
	    private String matriculaAlumno;
	    private String mensaje;

	    // Getters y setters
	    public String getFolio() {
	        return folio;
	    }

	    public void setFolio(String folio) {
	        this.folio = folio;
	    }

	    public LocalDateTime getFecha() {
	        return fecha;
	    }

	    public void setFecha(LocalDateTime fecha) {
	        this.fecha = fecha;
	    }

	    public Double getMonto() {
	        return monto;
	    }

	    public void setMonto(Double monto) {
	        this.monto = monto;
	    }

	    public UsuarioDTO getCajero() {
	        return cajero;
	    }

	    public void setCajero(UsuarioDTO cajero) {
	        this.cajero = cajero;
	    }

	    public String getMatriculaAlumno() {
	        return matriculaAlumno;
	    }

	    public void setMatriculaAlumno(String matriculaAlumno) {
	        this.matriculaAlumno = matriculaAlumno;
	    }

	    public String getMensaje() {
	        return mensaje;
	    }

	    public void setMensaje(String mensaje) {
	        this.mensaje = mensaje;
	    }

}
