package mx.itson.sgi.data_access.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mx.itson.sgi.dto.enums.Estatus;
import mx.itson.sgi.dto.enums.Genero;
import mx.itson.sgi.dto.enums.Nivel;

@Data
@Entity
@Table(name = "alumnos")
@Builder
@AllArgsConstructor
public class Alumno {

    @Expose
    @Id
    private String matricula;

    @Expose
    @Column(nullable = false)
    private String nombre;

    @Expose
    @Column(nullable = false)
    private double promedio;

    @Expose
    @Column(nullable = false)
    private int grado;

    @Expose
    @Column(nullable = false)
    private String grupo;

    @Expose
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estatus estatus;

    @Expose
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nivel nivel;

    @Expose
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Expose
    @Column(nullable = false)
    private String telefono;

    @Expose
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @Expose
    @Column(nullable = false)
    private String correo;

    @Expose
    @Column(nullable = false)
    private String tutor;

    @Expose
    @Column(name = "telefono_padre", nullable = false)
    private String telefonoPadre;

    @Embedded
    @AttributeOverride(name = "tipo", column = @Column(name = "tipo_beca"))
    private Beca beca;

    @OneToMany(mappedBy = "alumno", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Cuota> cuotas;
    
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private List<Venta> compras = new ArrayList<>();


    /**
     * Constructor vacio por defecto
     */
    public Alumno(){}

    public Alumno(String matricula) {
        this.matricula = matricula;
    }

    public Alumno(String matricula, String nombre, String telefonoPadre ){
        this.matricula = matricula;
        this.nombre = nombre;
        this.telefonoPadre = telefonoPadre;
    }

    public Alumno(String matricula, String nombre, Beca beca, String telefonoPadre) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.beca = beca;
        this.telefonoPadre = telefonoPadre;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }


    
}
