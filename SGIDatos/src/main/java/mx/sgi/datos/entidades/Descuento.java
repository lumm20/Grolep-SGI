package mx.sgi.datos.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "descuentos")
public class Descuento {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "tipo_descuento", nullable = false)
    private String tipoDescuento;

    @Column(name = "catidad_descuento", nullable = false, precision = 10)
    private BigDecimal catidadDescuento;


}