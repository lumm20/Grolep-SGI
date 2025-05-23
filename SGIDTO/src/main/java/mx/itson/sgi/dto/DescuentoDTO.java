package mx.itson.sgi.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DescuentoDTO implements Serializable {

    String tipo; //indica que tipo de beca es
    Double descuento; //indica cuanto descuento da

    public DescuentoDTO(){}

    /**
     * Contructor completo
     *
     * @param tipo
     * @param descuento
     */
    public DescuentoDTO(String tipo,  Double descuento) {
        this.tipo = tipo;
        this.descuento = descuento;
    }

}
