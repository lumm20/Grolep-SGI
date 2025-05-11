package mx.itson.sgi.data_access.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.entities.DetalleCiclo;

import java.util.Optional;

@Repository
public interface DetalleCicloRepository extends JpaRepository<DetalleCiclo, Long> {
    Optional<DetalleCiclo> findByCicloEscolarId(String idCiclo);
}
