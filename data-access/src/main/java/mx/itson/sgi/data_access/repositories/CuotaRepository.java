package mx.itson.sgi.data_access.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.dto.AdeudoDTO;
import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.dto.CuotaConsultadaDTO;

@Repository
public interface CuotaRepository extends CrudRepository<Cuota,Long>{

    List<Cuota> findByAlumno_Matricula(String matriculaAlumno);
    List<Cuota> findByConcepto(Concepto concepto);
    Optional<Cuota> findByAlumno_MatriculaAndConcepto(Alumno alumno, Concepto concepto);
    Optional<Cuota> findByAlumnoAndConceptoAndCiclo(Alumno alumno,Concepto concepto, CicloEscolar ciclo);
    @Query(value = "call sp_obtener_adeudos()",nativeQuery = true)
    List<Object[]> findCuotasConAdeudo();
    @Query(value = "call sp_obtener_adeudos_alumno(:matricula,:ciclo)",nativeQuery = true)
    List<Object[]> findCuotasConAdeudoPorAlumno(@Param("matricula")String alumno, @Param("ciclo")String ciclo);
    @Query(value = "call sp_obtener_cuotas_alumno(:matricula,:ciclo)",nativeQuery = true)
    List<Object[]> findCuotasPorAlumno(@Param("matricula")String alumno, @Param("ciclo")String ciclo);
}
