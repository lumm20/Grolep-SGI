package mx.sgi.datos.entidades;

import jakarta.persistence.*;
import mx.sgi.datos.enumeradores.TipoDescuento;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "montoTotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "folio", nullable = false, length = 50)
    private String folio;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Lob
    @Column(name = "metodoPago", nullable = false)
    private String metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_descuento", nullable = false)
    private TipoDescuento tipoDescuento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "matricula", nullable = false, referencedColumnName = "matricula")
    private Alumno matricula;

}