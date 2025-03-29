package mx.itson.sgi.data_access.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.entities.CicloEscolar;
import java.time.LocalDate;

@Repository
public interface CicloRepository extends CrudRepository<CicloEscolar,String>{

    Optional<CicloEscolar> findByFechaInicioAndFechaFin(LocalDate fechaInicio, LocalDate fechaFin);
    @Query(value = "call sp_obtener_ciclo_actual()", nativeQuery = true)
    Optional<CicloEscolar> findCicloActual();
}
