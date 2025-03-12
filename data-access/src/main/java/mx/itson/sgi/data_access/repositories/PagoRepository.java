package mx.itson.sgi.data_access.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.Pago;
import java.time.LocalDateTime;


public interface PagoRepository extends CrudRepository<Pago,String>{

    List<Pago> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    @Query("select p from Pago p where p.alumno = ?1")
    List<Pago> findPagosPorEstudiante(Alumno alumno);


}
