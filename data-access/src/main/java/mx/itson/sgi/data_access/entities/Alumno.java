package mx.itson.sgi.data_access.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import mx.itson.sgi.data_access.utilities.BecaConverter;

@Data
@Entity
@Table(name = "alumnos")
public class Alumno {

    @Expose
    @Id
    private String matricula;
    @Expose
    @Column(nullable = false)
    private String nombre;
    @Expose
    @Column(nullable = false)
    private String apellidos;

    @Column(name = "tipo_beca", nullable = false)
    @Convert(converter = BecaConverter.class)
    private Beca beca;

    @OneToMany(mappedBy = "alumno", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Cuota> cuotas;

    @Expose
    @Column(name="telefono_padre", nullable = false)
    private String telefonoPadre;
    
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private List<Venta> compras = new ArrayList<>();
      
    public Alumno(){}

    public Alumno(String matricula) {
        this.matricula = matricula;
    }

    public Alumno(String matricula, String nombre, String apellidos, Beca beca, String telefonoPadre) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.beca = beca;
        this.telefonoPadre = telefonoPadre;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
    
}
