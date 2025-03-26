package mx.sgi.datos.interfaces;

import mx.sgi.datos.entidades.Alumno;
import mx.sgi.datos.excepciones.RepositoryException;

import java.util.List;

public interface IAlumnoRepository {

    public List<Alumno> getAlumnosPorNombre(String nombre) throws RepositoryException;

}
