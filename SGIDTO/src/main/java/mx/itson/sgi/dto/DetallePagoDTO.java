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
	
	public DetallePagoDTO(String concepto) {
		this.conceptoCuota = concepto;
	}
	
	public DetallePagoDTO(String conceptoCuota, Double montoPagado) {
		this.conceptoCuota = conceptoCuota;
		this.montoPagado = montoPagado;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetallePagoDTO other = (DetallePagoDTO) obj;
		if (conceptoCuota == null) {
			if (other.conceptoCuota != null)
				return false;
		} else if (!conceptoCuota.equals(other.conceptoCuota))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conceptoCuota == null) ? 0 : conceptoCuota.hashCode());
		return result;
	}

	

}
