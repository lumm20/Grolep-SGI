package mx.itson.sgi.data_access.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class Beca {

    @Enumerated(EnumType.STRING)
    private TipoBeca tipo;
    private Double porcentajeDescuento;

    public Beca() {
    }

    public Beca(TipoBeca tipo, Double porcentajeDescuento) {
        this.tipo = tipo;
        this.porcentajeDescuento = porcentajeDescuento;
    }

}
