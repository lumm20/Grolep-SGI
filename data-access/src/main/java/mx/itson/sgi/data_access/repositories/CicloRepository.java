package mx.itson.sgi.data_access.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mx.itson.sgi.data_access.entities.CicloEscolar;
import java.time.LocalDate;


public interface CicloRepository extends CrudRepository<CicloEscolar,String>{

    Optional<CicloEscolar> findByFechaInicioAndFechaFin(LocalDate fechaInicio, LocalDate fechaFin);
}
