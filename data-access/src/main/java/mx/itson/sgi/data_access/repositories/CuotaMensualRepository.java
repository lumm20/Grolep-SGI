package mx.itson.sgi.data_access.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.CuotaMensual;

public interface CuotaMensualRepository extends JpaRepository<CuotaMensual, Long> {

    List<CuotaMensual> findByAlumno_Matricula(String matriculaAlumno);
    Optional<CuotaMensual> findByAlumnoAndCicloAndMes(Alumno alumno, CicloEscolar ciclo, LocalDate mes);
    Optional<List<CuotaMensual>> findByAlumnoAndCiclo(Alumno alumno, CicloEscolar ciclo);
    Optional<List<CuotaMensual>> findByAlumnoAndCicloAndMontoAdeudoGreaterThan(Alumno alumno, CicloEscolar ciclo, Double montoAdeudo);
}
