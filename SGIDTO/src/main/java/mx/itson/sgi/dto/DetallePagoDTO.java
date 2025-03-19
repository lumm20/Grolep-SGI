package mx.itson.sgi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DetallePagoDTO {

	private Long id;
	private PagoDTO pago;
	private CuotaDTO cuota;
	private Double montoPagado;

}
