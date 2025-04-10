package mx.itson.sgi.dto;

import java.time.LocalDate;
import java.time.Month;

import lombok.Data;

@Data
public class DetalleAdeudoDTO {
    private Month mesAdeudo;
    private String fechaPago;
    private Double montoAdeudo;
    private Double montoBase;
    private Double montoPagado;

    public DetalleAdeudoDTO(Month mesAdeudo, Double montoPagado, Double montoAdeudo) {
        this.mesAdeudo = mesAdeudo;
        this.montoAdeudo = montoAdeudo;
        this.montoPagado = montoPagado;
    }

    public DetalleAdeudoDTO(String fechaPago, Double montoPagado,Double montoAcumulado) {
        this.fechaPago = fechaPago;
        this.montoPagado = montoPagado;
        this.montoAdeudo = montoAcumulado;
    }

    public DetalleAdeudoDTO(Month mesAdeudo) {
        this.mesAdeudo = mesAdeudo;
    }

    public DetalleAdeudoDTO() {}
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DetalleAdeudoDTO other = (DetalleAdeudoDTO) obj;
        if (mesAdeudo == null) {
            if (other.mesAdeudo != null)
                return false;
        } else if (!mesAdeudo.equals(other.mesAdeudo))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mesAdeudo == null) ? 0 : mesAdeudo.hashCode());
        return result;
    }

    
}
