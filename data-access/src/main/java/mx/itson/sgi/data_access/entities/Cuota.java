package mx.itson.sgi.data_access.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="cuotas")
public class Cuota {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Expose
    @Column(name = "monto_base")
    private Double montoBase;

    @Expose
    @ManyToOne
    @JoinColumn(name = "ciclo_escolar")
    private CicloEscolar ciclo;
    
    @Expose
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_alumno",referencedColumnName = "matricula", nullable = false)
    private Alumno alumno;

    @Expose
    @Enumerated(EnumType.STRING)
    private ConceptoCuota concepto;

    public Cuota() {
    }

    public Cuota(Double montoBase, CicloEscolar ciclo, ConceptoCuota concepto) {
        this.montoBase = montoBase;
        this.ciclo = ciclo;
        this.concepto = concepto;
    }

    public Cuota(Double montoBase, ConceptoCuota concepto) {
        this.montoBase = montoBase;
        this.concepto = concepto;
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
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }

    public ConceptoCuota getConcepto() {
        return concepto;
    }

    public void setConcepto(ConceptoCuota concepto) {
        this.concepto = concepto;
    }    
}
