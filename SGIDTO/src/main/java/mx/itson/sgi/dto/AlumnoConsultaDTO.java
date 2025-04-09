package mx.itson.sgi.dto;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class AlumnoConsultaDTO implements Serializable {

    @Expose
    private String matricula;
    @Expose
    private String nombre;
    @Expose
    private String numeroCelular;

    /**
     * constructor vacio
     */
    public AlumnoConsultaDTO() {}
    public AlumnoConsultaDTO(String matricula) {this.matricula = matricula;}

    /**
     * Constructor completo
     *
     * @param matricula
     * @param nombre
     * @param numeroCelular
     */
    public AlumnoConsultaDTO(String matricula, String nombre, String numeroCelular) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.numeroCelular = numeroCelular;
    }

    @Override
    public String toString() {
        return String.format("%s, \"%s\"", nombre, numeroCelular);
    }

}
