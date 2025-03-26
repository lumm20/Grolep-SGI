package mx.itson.sgi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ColegiaturaAtrasadaDTO {

    private final LocalDate mes; //el mes que quedo debiendo
    private final BigDecimal adeudoAcumulado; // ela adeudo que acumulo
    private  final BigDecimal montoPagado; // el monto que pago

    // Constructor
    public ColegiaturaAtrasadaDTO(LocalDate mes,  BigDecimal adeudoAcumulado, BigDecimal montoPagado) {
        this.mes = mes;
        this.adeudoAcumulado = adeudoAcumulado;
        this.montoPagado = montoPagado;
    }
}
