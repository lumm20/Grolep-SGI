package mx.sgi.presentacion.interfaces;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.AlumnoRegistroDTO;
import mx.sgi.presentacion.excepciones.ConexionServidorException;

import java.util.List;

public interface IServicioAlumnos {

    List<AlumnoConsultaDTO> buscarAlumnos(String nombre) throws ConexionServidorException;
    List<AlumnoRegistroDTO> searchCompleteStudent(String name, int limit, int offset) throws ConexionServidorException;
    void registerStudent(AlumnoRegistroDTO alumno) throws ConexionServidorException;
    void editStudent(AlumnoRegistroDTO alumno) throws ConexionServidorException;

}
