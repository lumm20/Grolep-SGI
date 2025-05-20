package mx.itson.sgi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetalleCicloDTO {

    private Long id;
    private String idCicloEscolar; // ID del ciclo escolar
    private Double cuotaInscripcion;
    private Double cuotaColegiatura;
    private Double cuotaLibros;
    private Double cuotaEventos;
    private Double cuotaAcademias;
    private Double cuotaUniforme;

    public DetalleCicloDTO() {
    }

    public DetalleCicloDTO(Long id, String idCicloEscolar, Double cuotaInscripcion, Double cuotaColegiatura,
                           Double cuotaLibros, Double cuotaEventos, Double cuotaAcademias, Double cuotaUniforme) {
        this.id = id;
        this.idCicloEscolar = idCicloEscolar;
        this.cuotaInscripcion = cuotaInscripcion;
        this.cuotaColegiatura = cuotaColegiatura;
        this.cuotaLibros = cuotaLibros;
        this.cuotaEventos = cuotaEventos;
        this.cuotaAcademias = cuotaAcademias;
        this.cuotaUniforme = cuotaUniforme;
    }
}