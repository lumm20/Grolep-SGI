package mx.itson.sgi.data_access.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.data_access.repositories.PagoRepository;

@Service
public class PagoService {
    @Autowired
    private PagoRepository repository;

    public Pago obtenerPagoPorFolio(String folio){
        Optional<Pago> pago = repository.findById(folio);
        if(pago.isPresent()){
            return pago.get();
        }
        return null;
    }

    public List<Pago> obtenerPagosPorEstudiante(String matriculaEstudiante){
        List<Pago> pago = repository.findPagosPorEstudiante(new Alumno(matriculaEstudiante));

        if(pago!= null && !pago.isEmpty()){
            return pago;
        }
        return new ArrayList<Pago>();
    }

    public List<Pago> obtenerPagosPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        List<Pago> pago = repository.findByFechaBetween(fechaFin, fechaFin);

        if(pago!= null && !pago.isEmpty()){
            return pago;
        }
        return new ArrayList<Pago>();
    }

    public Pago registrarPago(Pago pago){
        Pago pagoRegistrado = repository.save(pago);
        return pagoRegistrado;
    }
    
    /**
     * Genera un folio único para un pago con el formato: PYYYYMMDDXXXXX
     * Donde YYYYMMDD es la fecha actual y XXXXX es un numero secuencial
     * 
     * @return String con el folio generado
     */
    public String generarFolio() {
        Long lastNumber = repository.count();
        LocalDate fechaActual = LocalDate.of(2023, 1, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        Long nuevoNumero = lastNumber + 1;

        String numeroFormateado = String.format("%05d", nuevoNumero);
        return "P".concat(fechaFormateada).concat(numeroFormateado);
    }
}
