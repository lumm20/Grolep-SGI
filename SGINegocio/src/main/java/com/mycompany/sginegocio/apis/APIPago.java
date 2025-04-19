package com.mycompany.sginegocio.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.sginegocio.controlador.PagoControlador;
import com.mycompany.sginegocio.excepciones.PaymentException;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.vistas.TicketRegistrarDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/payment")
public class APIPago {

    @Autowired
    PagoControlador controlador;

    @PostMapping
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registrarPago(@RequestBody PagoDTO pagoDTO) {
        try {
        	TicketRegistrarDTO ticket = controlador.registrarPago(pagoDTO);
        	System.out.println("prueba");
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/tuition/all")
    public ResponseEntity<?> obtenerTotalPagadoColegiatura(@RequestParam String matricula, @RequestParam String ciclo) {
        try {
            Double totalPagado = controlador.obtenerTotalPagadoColegiatura(new AlumnoConsultaDTO(matricula), ciclo);
            if(totalPagado == null || totalPagado == 0.0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el alumno o no tiene pagos registrados");
            }
            return ResponseEntity.ok(totalPagado);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body("algo ha salido mal");
        }

    }
    @GetMapping("/tuition/all-monthly")
    public ResponseEntity<?> obtenerCantidadPagosMensuales(@RequestParam String matricula, @RequestParam String ciclo) {
        try {
            Long cantidadPagos = controlador.getCantidadPagosMensuales(new AlumnoConsultaDTO(matricula), ciclo);
            if(cantidadPagos == null || cantidadPagos == 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el alumno o no tiene pagos registrados");
            }
            return ResponseEntity.ok(cantidadPagos);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body("algo ha salido mal");
        }

    }
    
}
