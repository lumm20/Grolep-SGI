package mx.itson.sgi.dto;

import lombok.Data;

@Data
public class CicloConDetallesDTO {
    private CicloEscolarDTO cicloEscolar;
    private DetalleCicloDTO detalleCiclo;
}