package mx.itson.sgi.data_access.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Pago;
import java.time.LocalDateTime;

@Repository
public interface PagoRepository extends CrudRepository<Pago,String>{

    List<Pago> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    @Query("select p from Pago p where p.alumno = ?1")
    List<Pago> findPagosPorEstudiante(Alumno alumno);

    // @Query(value = "call sp_total_pagado_colegiaturas(:matricula,:ciclo)",nativeQuery = true)
    @Query("SELECT COALESCE(SUM(dp.montoPagado), 0) FROM DetallePago dp RIGHT JOIN dp.cuota c WHERE c.concepto = COLEGIATURA "+ 
    "AND c.alumno = ?1 AND c.ciclo =?2")
    Double findTotalPagadoColegiatura(Alumno alumno, CicloEscolar ciclo);

    @Query("SELECT COUNT(p) " +
           "FROM Pago p " +
           "INNER JOIN p.detalles dp " +
           "RIGHT JOIN dp.cuota c " +
           "WHERE c.concepto = 'COLEGIATURA' " +
           "AND p.alumno = ?1 " +
           "AND c.ciclo = ?2 " +
           "AND FUNCTION('MONTH', p.fechaHora) = FUNCTION('MONTH', CURRENT_DATE)")
    Long countPagosMensuales(@Param("alumno") Alumno alumno, @Param("ciclo") CicloEscolar ciclo);

}
