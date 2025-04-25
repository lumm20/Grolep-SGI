package mx.itson.sgi.dto;

import lombok.Data;

@Data
public class CuotaConsultadaDTO {
    private Long id;
    private Double adeudo;
    private Double montoBase;
    private String concepto;
    private String cicloEscolar;
    private String fechaCuota;
    private String matriculaAlumno;

    public CuotaConsultadaDTO() {
    }

    public CuotaConsultadaDTO(Long id, String matriculaAlumno, String concepto,String cicloEscolar,Double montoBase, Double adeudo) {
        this.id = id;
        this.adeudo = adeudo;
        this.montoBase = montoBase;
        this.concepto = concepto;
        this.cicloEscolar = cicloEscolar;
        this.matriculaAlumno = matriculaAlumno;
    }
    public CuotaConsultadaDTO(Long id, String matriculaAlumno, String concepto,
    String cicloEscolar,String fecha, Double montoBase, Double adeudo) {
        this.id = id;
        this.adeudo = adeudo;
        this.montoBase = montoBase;
        this.concepto = concepto;
        this.cicloEscolar = cicloEscolar;
        this.matriculaAlumno = matriculaAlumno;
        this.fechaCuota = fecha;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CuotaConsultadaDTO other = (CuotaConsultadaDTO) obj;
        if (concepto == null) {
            if (other.concepto != null)
                return false;
        } else if (!concepto.equals(other.concepto))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((concepto == null) ? 0 : concepto.hashCode());
        return result;
    }

    
}
