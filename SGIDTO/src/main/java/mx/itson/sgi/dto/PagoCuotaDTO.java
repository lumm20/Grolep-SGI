package mx.itson.sgi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PagoCuotaDTO {

    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal monto;
    private String concepto;

}
