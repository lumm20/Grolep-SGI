package mx.itson.sgi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ColegiaturaAtrasadaDTO {

    private final LocalDate mes;
    private final BigDecimal adeudoAcumulado;
    private  final BigDecimal montoPagado;

    // Constructor
    public ColegiaturaAtrasadaDTO(LocalDate mes,  BigDecimal adeudoAcumulado, BigDecimal montoPagado) {
        this.mes = mes;
        this.adeudoAcumulado = adeudoAcumulado;
        this.montoPagado = montoPagado;
    }
}
