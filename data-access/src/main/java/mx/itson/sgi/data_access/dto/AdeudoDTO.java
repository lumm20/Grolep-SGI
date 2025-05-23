package mx.itson.sgi.data_access.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;

public class AdeudoDTO {
    private Long id;
    private String matriculaAlumno;
    private String cicloEscolar;
    private String conceptoCuota;
    private Double montoBase;
    //@Expose
    private Double adeudo;
    //@Expose
    private Cuota cuota;

    public AdeudoDTO() {
    }
    
    public AdeudoDTO( Double montoBase,Long id, String cicloEscolar,String matriculaAlumno, String conceptoCuota, Double adeudo) {
        this.id = id;
        this.matriculaAlumno = matriculaAlumno;
        this.conceptoCuota = conceptoCuota;
        this.cicloEscolar = cicloEscolar;
        this.montoBase = montoBase;
        this.adeudo = adeudo;
        setCuota();
    }
    
    public AdeudoDTO(Double montoBase, String conceptoCuota,  String cicloEscolar) {
        this.conceptoCuota = conceptoCuota;
        this.montoBase = montoBase;
        this.cicloEscolar = cicloEscolar;
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

    public Cuota getCuota() {return cuota;}

    public void setCuota() {
        Concepto concepto=null;
        String conceptoLowerCase = conceptoCuota.toLowerCase();
        switch (conceptoLowerCase) {
            case "academias":
                concepto = Concepto.ACADEMIAS;
                break;
            case "uniformes":
                concepto = Concepto.UNIFORMES;
                break;
            case "libros":
                concepto = Concepto.LIBROS;
                break;
            case "inscripcion":
                concepto = Concepto.INSCRIPCION;
                break;
            case "eventos":
                concepto = Concepto.EVENTOS;
                break;
            case "colegiatura":
                concepto = Concepto.COLEGIATURA;
                break;
        }
        cuota =new Cuota(montoBase,  new CicloEscolar(cicloEscolar), concepto);
        cuota.setAlumno(new Alumno(matriculaAlumno));
        cuota.setId(id);
    }

    public String getMatriculaAlumno() {
        return matriculaAlumno;
    }
    public void setMatriculaAlumno(String matriculaAlumno) {
        this.matriculaAlumno = matriculaAlumno;
    }
    public String getCicloEscolar() {
        return cicloEscolar;
    }
    public void setCicloEscolar(String cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
    }
    public String getConcepto() {
        return conceptoCuota;
    }
    public void setConcepto(String conceptoCuota) {
        this.conceptoCuota = conceptoCuota;
    }
    public Double getAdeudo() {
        return adeudo;
    }
    public void setAdeudo(Double adeudo) {
        this.adeudo = adeudo;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
    
    
}
