package mx.itson.sgi.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DescuentoDTO implements Serializable {

    String tipo; //indica que tipo de beca es
    BigDecimal descuento; //indica cuanto descuento da

    public DescuentoDTO() {}

    /**
     * Contructor completo
     *
     * @param tipo
     * @param descuento
     */
    public DescuentoDTO(String tipo,  BigDecimal descuento) {
        this.tipo = tipo;
        this.descuento = descuento;
    }

}
