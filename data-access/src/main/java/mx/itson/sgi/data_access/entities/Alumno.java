package mx.itson.sgi.data_access.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "alumnos")
public class Alumno {

    @Expose
    @Id
    private String matricula;
    @Expose
    private String nombre;
    @Expose
    private String apellidos;

    @OneToMany(mappedBy = "alumno", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Cuota> cuotas;

    @Column(name="telefono_padre", nullable = false)
    private String telefonoPadre;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Venta> compras = new ArrayList<>();
      
    public Alumno(String matricula) {
        this.matricula = matricula;
    }

    public Alumno(String matricula, String nombre, String apellidos, Beca beca, String telefonoPadre,
             LocalDate fechaNacimiento) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "{matricula=" + matricula + ", nombre=" + nombre + ", apellidos=" + apellidos + ", telefonoPadre="
                + telefonoPadre + "}";
    }

    
}
