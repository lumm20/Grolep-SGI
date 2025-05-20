package mx.itson.sgi.dto;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlumnoConsultaDTO implements Serializable {

    @Expose
    private String matricula;
    private String nombre;
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
