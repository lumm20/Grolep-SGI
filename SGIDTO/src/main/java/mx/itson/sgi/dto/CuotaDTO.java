package mx.itson.sgi.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CuotaDTO {

    private BigDecimal monto;
    private String concepto;

    // Constructor con parámetros
    public CuotaDTO(BigDecimal monto, String concepto) {
        this.monto = monto;
        this.concepto = concepto;
    }

}
