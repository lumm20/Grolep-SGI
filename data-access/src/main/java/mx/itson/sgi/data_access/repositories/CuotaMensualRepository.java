package mx.itson.sgi.data_access.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.CuotaMensual;

@Repository
public interface CuotaMensualRepository extends JpaRepository<CuotaMensual, Long> {

    List<CuotaMensual> findByAlumno_Matricula(String matriculaAlumno);
    Optional<CuotaMensual> findByAlumnoAndCicloAndMes(Alumno alumno, CicloEscolar ciclo, LocalDate mes);
    Optional<List<CuotaMensual>> findByAlumnoAndCiclo(Alumno alumno, CicloEscolar ciclo);
    Optional<List<CuotaMensual>> findByAlumnoAndCicloAndAdeudoGreaterThanOrderByMesAsc(Alumno alumno, CicloEscolar ciclo, Double montoAdeudo);
    @Query("select cm.mes, cm.montoPagado, cm.montoBase, cm.adeudo from CuotaMensual cm where cm.alumno = ?1 and cm.ciclo = ?2 and cm.adeudo > 0.0")
    List<Object[]> buscarDetalles(Alumno alumno, CicloEscolar ciclo);
}
