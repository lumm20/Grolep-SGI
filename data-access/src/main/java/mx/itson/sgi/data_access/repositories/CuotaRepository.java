package mx.itson.sgi.data_access.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import mx.itson.sgi.data_access.dto.AdeudoDTO;
import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.ConceptoCuota;
import mx.itson.sgi.data_access.entities.Cuota;

public interface CuotaRepository extends CrudRepository<Cuota,Long>{

    List<Cuota> findByAlumno_Matricula(String matriculaAlumno);
    List<Cuota> findByConcepto(ConceptoCuota concepto);
    Optional<Cuota> findByAlumno_MatriculaAndConcepto(Alumno alumno, ConceptoCuota concepto);
    Optional<Cuota> findByAlumnoAndConceptoAndCiclo(Alumno alumno,ConceptoCuota concepto, CicloEscolar ciclo);
    @Query(value = "call sp_obtener_adeudos()",nativeQuery = true)
    List<AdeudoDTO> findCuotasConAdeudo();
    @Query(value = "call sp_obtener_adeudos_alumno(:matricula,:ciclo)",nativeQuery = true)
    List<AdeudoDTO> findCuotasConAdeudoPorAlumno(@Param("matricula")String alumno, @Param("ciclo")String ciclo);
}
