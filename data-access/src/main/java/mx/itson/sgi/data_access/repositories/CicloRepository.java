package mx.itson.sgi.data_access.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.entities.CicloEscolar;
import java.time.LocalDate;

@Repository
public interface CicloRepository extends CrudRepository<CicloEscolar,String>{

    Optional<CicloEscolar> findByFechaInicioAndFechaFin(LocalDate fechaInicio, LocalDate fechaFin);
}
