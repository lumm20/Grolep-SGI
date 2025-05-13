package mx.itson.sgi.dto;

import lombok.Data;

@Data
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