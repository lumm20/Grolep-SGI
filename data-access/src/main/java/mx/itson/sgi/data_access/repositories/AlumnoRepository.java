package mx.itson.sgi.data_access.repositories;

import org.springframework.data.repository.CrudRepository;

import mx.itson.sgi.data_access.entities.Alumno;

public interface AlumnoRepository extends CrudRepository<Alumno,String> {

}
