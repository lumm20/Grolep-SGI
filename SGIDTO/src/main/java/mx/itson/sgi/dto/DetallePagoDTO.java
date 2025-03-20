package mx.itson.sgi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DetallePagoDTO {

	private String conceptoCuota;
	private Double montoPagado;


	public DetallePagoDTO() {
	}
	
	public DetallePagoDTO(String conceptoCuota, Double montoPagado) {
		this.conceptoCuota = conceptoCuota;
		this.montoPagado = montoPagado;
	}

}
