package mx.sgi.presentacion.interfaces;

import mx.itson.sgi.dto.AlumnoConsultaDTO;

import java.util.List;

public interface IServicioAlumnos {

    public List<AlumnoConsultaDTO> consultarAlumnos(String nombre) throws Exception;

}
