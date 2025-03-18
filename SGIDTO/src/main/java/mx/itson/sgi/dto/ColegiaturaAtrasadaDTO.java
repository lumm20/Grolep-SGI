package mx.itson.sgi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ColegiaturaAtrasadaDTO {

    private BigDecimal monto;
    private LocalDate mes;

    // Constructor
    public ColegiaturaAtrasadaDTO(BigDecimal monto, LocalDate mes) {
        this.monto = monto;
        this.mes = mes;
    }
}
