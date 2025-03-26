package mx.sgi.datos.entidades;

import jakarta.persistence.*;
import lombok.Data;
import mx.sgi.datos.enumeradores.Beca;

@Data
@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "matricula", nullable = false, length = 50)
    private String matricula;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "numeroTutor", nullable = false, length = 20)
    private String numerTutor;

    @Enumerated(EnumType.STRING)
    @Column(name = "beca", nullable = false)
    private Beca beca;

}