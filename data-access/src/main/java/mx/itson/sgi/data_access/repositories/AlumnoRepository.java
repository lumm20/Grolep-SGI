package mx.itson.sgi.data_access.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.entities.Alumno;
import java.util.List;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno,String> {

    List<Alumno> findByNombre(String nombre);
    List<Alumno> findByTelefonoPadre(String telefonoPadre);
}
