package mx.itson.sgi.data_access.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import mx.itson.sgi.data_access.utilities.BecaConverter;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    private String matricula;
    private String nombre;
    private String apellidos;

    @Convert(converter = BecaConverter.class)
    @Column(name = "tipo_beca")
    private Beca beca;
    
    @Column(name = "telefono_padre")
    private String telefonoPadre;
    
    @OneToMany(mappedBy = "alumno", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Cuota> cuotas;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    public Alumno() {
        this.cuotas = new ArrayList<>();
    }

    public Alumno(String matricula, String nombre, String apellidos, Beca beca, String telefonoPadre,
             LocalDate fechaNacimiento) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.beca = beca;
        this.telefonoPadre = telefonoPadre;
        this.cuotas = new ArrayList<>();
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Beca getBeca() {
        return beca;
    }

    public void setBeca(Beca beca) {
        this.beca = beca;
    }

    public String getTelefonoPadre() {
        return telefonoPadre;
    }

    public void setTelefonoPadre(String telefonoPadre) {
        this.telefonoPadre = telefonoPadre;
    }

    public List<Cuota> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
