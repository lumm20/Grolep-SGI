package mx.itson.sgi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CicloConDetallesDTO {
    private CicloEscolarDTO cicloEscolar;
    private DetalleCicloDTO detalleCiclo;

    public CicloConDetallesDTO() {
    }

    public CicloConDetallesDTO(CicloEscolarDTO cicloEscolar, DetalleCicloDTO detalleCiclo) {
        this.cicloEscolar = cicloEscolar;
        this.detalleCiclo = detalleCiclo;
    }
}