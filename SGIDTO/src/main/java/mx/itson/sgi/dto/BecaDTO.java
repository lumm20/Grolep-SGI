package mx.itson.sgi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BecaDTO implements Serializable {

    String tipo; //indica que tipo de beca es
    BigDecimal descuento; //indica cuanto descuento da

    /**
     * Constructor por defecto
     */
    public BecaDTO() {}

    /**
     * Contructor completo
     *
     * @param tipo
     * @param descuento
     */
    public BecaDTO(String tipo,  BigDecimal descuento) {
        this.tipo = tipo;
        this.descuento = descuento;
    }

}
