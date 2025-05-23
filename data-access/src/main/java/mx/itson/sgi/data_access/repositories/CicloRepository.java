package mx.itson.sgi.data_access.repositories;

import java.util.List;
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
    
    // Buscar ciclos cuyo inicio sea mayor o igual a fechaInicio y fin menor o igual a fechaFin
    List<CicloEscolar> findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar ciclos que intersectan el rango de fechas: fechaFin >= begin AND fechaInicio <= end
    List<CicloEscolar> findByFechaFinGreaterThanEqualAndFechaInicioLessThanEqual(LocalDate begin, LocalDate end);
}
