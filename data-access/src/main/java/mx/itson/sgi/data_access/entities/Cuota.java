package mx.itson.sgi.data_access.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="cuotas")
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "monto_base")
    private Double montoBase;
    @Column(name = "ciclo_escolar")
    private CicloEscolar ciclo;
    
    @ManyToOne
    @JoinColumn(name = "matricula_alumno")
    private Alumno alumno;

    public Cuota() {
    }

    public Cuota(Double montoBase, CicloEscolar ciclo, Alumno alumno) {
        this.montoBase = montoBase;
        this.ciclo = ciclo;
        this.alumno = alumno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontoBase() {
        return montoBase;
    }

    public void setMontoBase(Double montoBase) {
        this.montoBase = montoBase;
    }

    public CicloEscolar getCiclo() {
        return ciclo;
    }

    public void setCiclo(CicloEscolar ciclo) {
        this.ciclo = ciclo;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", montoBase=" + montoBase + ", ciclo=" + ciclo + "}";
    }    
}
