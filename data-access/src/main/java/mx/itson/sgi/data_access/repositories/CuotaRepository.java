package mx.itson.sgi.data_access.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;

@Repository
public interface CuotaRepository extends CrudRepository<Cuota,Long>{

    List<Cuota> findByAlumno_Matricula(String matriculaAlumno);
    List<Cuota> findByConcepto(Concepto concepto);
    Optional<Cuota> findByAlumno_MatriculaAndConcepto(Alumno alumno, Concepto concepto);
    Optional<Cuota> findByAlumnoAndConceptoAndCiclo(Alumno alumno,Concepto concepto, CicloEscolar ciclo);
    // @Query(value = "call sp_obtener_adeudos()",nativeQuery = true)
    // List<Object[]> findCuotasConAdeudo();
    // @Query(value = "call sp_obtener_adeudos_alumno(:matricula,:ciclo)",nativeQuery = true)
    // List<Object[]> findCuotasConAdeudoPorAlumno(@Param("matricula")String alumno, @Param("ciclo")String ciclo);
    @Query(value = "call sp_obtener_cuotas_alumno(:matricula,:ciclo)",nativeQuery = true)
    List<Object[]> findCuotasPorAlumno(@Param("matricula")String alumno, @Param("ciclo")String ciclo);
    // @Query(value = "call sp_detalles_adeudo_colegiaturas(:matricula,:ciclo)",nativeQuery = true)
    // List<Object[]> findDetallesAdeudoColegiatura(@Param("matricula")String alumno, @Param("ciclo")String ciclo);
    
    @Query(value = "call sp_obtener_detalles_adeudos(:matricula,:ciclo)",nativeQuery = true)
    List<Object[]> findDetallesAdeudoColegiatura(@Param("matricula")String alumno, @Param("ciclo")String ciclo);
    
    // @Query(value = "call sp_obtener_pagos_mensuales(:matricula,:ciclo)",nativeQuery = true)
    // List<Object[]> findPagosMensualesAlumno(@Param("matricula")String alumno, @Param("ciclo")String ciclo);
    
    @Query("select new Cuota(c.montoBase) from Cuota c where c.alumno = ?1 and c.concepto = COLEGIATURA and c.ciclo = ?2")
    Cuota findMontoBaseColegiaturaAlumno(Alumno alumno, CicloEscolar ciclo);

    @Procedure(name = "sp_monto_total_colegiaturas")
    void sp_monto_total_colegiaturas(@Param("matricula")String matricula, @Param("ciclo")String ciclo, @Param("monto_total") Double monto_total);
}
