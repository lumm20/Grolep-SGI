package mx.sgi.negocio.bo;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.sgi.datos.conexiones.Conexion;
import mx.sgi.datos.repositorios.RepositorioAlumnos;

import java.util.List;

public class BoAlumnos {

    RepositorioAlumnos repositorioAlumnos;

    public BoAlumnos(){
        this.repositorioAlumnos= new RepositorioAlumnos(Conexion.getInstance());
    }

    public List<AlumnoConsultaDTO> obtenerAlumnosPorNombre(String nombre){

    }

}
