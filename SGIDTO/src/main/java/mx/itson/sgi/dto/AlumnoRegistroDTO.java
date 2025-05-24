package mx.itson.sgi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mx.itson.sgi.dto.enums.Estatus;
import mx.itson.sgi.dto.enums.Genero;
import mx.itson.sgi.dto.enums.Nivel;

import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoRegistroDTO {
    private String matricula;
    private String nombre;
    private double promedio;
    private int grado;
    private String grupo;
    private Estatus estatus;
    private Nivel nivel;
    private String fechaNacimiento;
    private String telefono;
    private Genero genero;
    private String correo;
    private BecaDTO beca;
    private String tutor;
}

