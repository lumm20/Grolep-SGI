package mx.itson.sgi.dto;

import lombok.Data;

import com.google.gson.annotations.Expose;

@Data
public class DetallePagoDTO {

	@Expose
	private String conceptoCuota;
	@Expose
	private Double montoPagado;


	public DetallePagoDTO() {
	}
	
	public DetallePagoDTO(String conceptoCuota, Double montoPagado) {
		this.conceptoCuota = conceptoCuota;
		this.montoPagado = montoPagado;
	}

}
