package mx.sgi.datos.entidades;

import jakarta.persistence.*;
import mx.sgi.datos.enumeradores.Rol;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "identificador", nullable = false, length = 16)
    private String identificador;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

}