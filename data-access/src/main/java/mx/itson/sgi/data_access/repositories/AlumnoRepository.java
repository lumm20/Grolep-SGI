package mx.itson.sgi.data_access.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.entities.Alumno;

import java.util.List;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno,String> {


    List<Alumno> findByNombre(String nombre);
    @Query("select new Alumno(a.matricula, a.nombre, a.telefonoPadre) from Alumno a where a.nombre like %?1%")
    List<Alumno> encontrarAlumnosConNombre(String n);
    List<Alumno> findByTelefonoPadre(String telefonoPadre);
    List<Alumno> findByNombreContaining(String nombre);
}
