package mx.itson.sgi.data_access.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_ciclo")
@Data
public class DetalleCiclo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ciclo", referencedColumnName = "id", nullable = false)
    private CicloEscolar cicloEscolar;

    @Column(name = "cuota_inscripcion", nullable = false)
    private Double cuotaInscripcion;

    @Column(name = "cuota_colegiatura", nullable = false)
    private Double cuotaColegiatura;

    @Column(name = "cuota_libros", nullable = false)
    private Double cuotaLibros;

    @Column(name = "cuota_eventos", nullable = false)
    private Double cuotaEventos;

    @Column(name = "cuota_academias", nullable = false)
    private Double cuotaAcademias;

    @Column(name = "cuota_uniforme", nullable = false)
    private Double cuotaUniforme;

    public DetalleCiclo() {
    }

    public DetalleCiclo(CicloEscolar cicloEscolar, Double cuotaInscripcion, Double cuotaColegiatura, Double cuotaLibros,
                        Double cuotaEventos, Double cuotaAcademias, Double cuotaUniforme) {
        this.cicloEscolar = cicloEscolar;
        this.cuotaInscripcion = cuotaInscripcion;
        this.cuotaColegiatura = cuotaColegiatura;
        this.cuotaLibros = cuotaLibros;
        this.cuotaEventos = cuotaEventos;
        this.cuotaAcademias = cuotaAcademias;
        this.cuotaUniforme = cuotaUniforme;
    }

    @Override
    public String toString() {
        return "DetalleCiclo{" +
                "id=" + id +
                ", cicloEscolar=" + cicloEscolar.getId() +
                ", cuotaInscripcion=" + cuotaInscripcion +
                ", cuotaColegiatura=" + cuotaColegiatura +
                ", cuotaLibros=" + cuotaLibros +
                ", cuotaEventos=" + cuotaEventos +
                ", cuotaAcademias=" + cuotaAcademias +
                ", cuotaUniforme=" + cuotaUniforme +
                '}';
    }
}