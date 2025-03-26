package mx.sgi.datos.entidades;

import jakarta.persistence.*;
import lombok.Data;
import mx.sgi.datos.enumeradores.TipoDescuento;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "descuentos")
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_descuento", nullable = false)
    private TipoDescuento tipoDescuento;

    @Column(name = "cantidad_descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadDescuento;
}
