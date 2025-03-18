package mx.itson.sgi.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class AlumnoConsultaDTO implements Serializable {

    private String matricula;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroCelular;

    // Constructor
    public AlumnoConsultaDTO(String matricula, String nombres, String apellidoPaterno, String apellidoMaterno, String numeroCelular) {
        this.matricula = matricula;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroCelular = numeroCelular;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s, \"%s\"", nombres, apellidoPaterno, apellidoMaterno, numeroCelular);
    }

}
