package com.mycompany.sginegocio.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.sginegocio.controlador.PagoControlador;

import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.TicketRegistrarDTO;

@RestController
@RequestMapping("/api/payment")
public class APIPago {

    @Autowired
    PagoControlador controlador;

    @PostMapping
    public ResponseEntity<TicketRegistrarDTO> registrarPago(@RequestBody PagoDTO pagoDTO) {
        try {
        	TicketRegistrarDTO ticket = controlador.registrarPago(pagoDTO);
        	System.out.println("prueba");
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
