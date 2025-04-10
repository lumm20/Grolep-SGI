package mx.sgi.negocio.fachadas;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.sgi.negocio.bo.BoAlumnos;
import mx.sgi.negocio.mapper.AlumnoConvertidor;

import java.util.List;

public class AlumnosFachada {

    private BoAlumnos boAlumnos;
    private AlumnoConvertidor alumnoConvertidor;

    public AlumnosFachada() {
        this.boAlumnos = new BoAlumnos();
        this.alumnoConvertidor = new AlumnoConvertidor();
    }

    public List<AlumnoConsultaDTO> obtenerAlumnosPorNombre(String nombre){
        return boAlumnos.obtenerAlumnosPorNombre(nombre);
    }



}
