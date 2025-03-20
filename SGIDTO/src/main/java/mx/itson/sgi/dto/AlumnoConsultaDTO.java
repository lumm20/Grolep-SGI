package mx.itson.sgi.dto;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;


import lombok.Data;

@Data
public class AlumnoConsultaDTO implements Serializable {

    private String matricula;
    private String nombre;
    private String telefonoPadre;

    public AlumnoConsultaDTO() {}

    // Constructor
    public AlumnoConsultaDTO(String matricula, String nombre, String telefonoPadre) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.telefonoPadre = telefonoPadre;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s, \"%s\"",matricula, nombre, telefonoPadre);
    }

}
