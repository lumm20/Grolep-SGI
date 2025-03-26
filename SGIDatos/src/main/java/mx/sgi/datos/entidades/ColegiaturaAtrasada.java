package mx.sgi.datos.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "colegiaturas_atrasadas")
public class ColegiaturaAtrasada {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mes", nullable = false)
    private LocalDate mes;

    @Column(name = "adeudo_acumulado", nullable = false, precision = 10)
    private BigDecimal adeudoAcumulado;

    @Column(name = "monto_pagado", nullable = false, precision = 10)
    private BigDecimal montoPagado;

    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

}