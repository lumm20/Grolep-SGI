package mx.itson.sgi.dto;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;


import lombok.Data;

@Data
public class AlumnoConsultaDTO implements Serializable {

	 private String matricula;
    private String nombre;
    private String apellidos;
    private String telefonoPadre;
    private List<CuotaDTO> cuotas;
    private LocalDate fechaNacimiento;
    

    public AlumnoConsultaDTO() {}

    // Constructor
    public AlumnoConsultaDTO(String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String numeroCelular, LocalDate fechaNacimiento) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidos = apellidoPaterno+" "+apellidoMaterno;
        this.telefonoPadre = numeroCelular;
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s, \"%s\"", nombre, apellidos, telefonoPadre, fechaNacimiento);
    }

}
