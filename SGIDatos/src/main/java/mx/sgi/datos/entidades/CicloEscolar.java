package mx.sgi.datos.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "ciclos_escolares")
public class CicloEscolar {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "inicio", nullable = false)
    private LocalDate inicio;

    @Column(name = "fin", nullable = false)
    private LocalDate fin;


}