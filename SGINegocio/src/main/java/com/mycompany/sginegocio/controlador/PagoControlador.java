/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio.controlador;

import java.awt.List;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.itson.sgi.data_access.services.PagoService;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.TicketRegistrarDTO;

/**
 *
 */
@RestController
@RequestMapping("/SGI/pagos")
public class PagoControlador {

    private final RegistrarPagoControlador registrarPago;

    public PagoControlador(RegistrarPagoControlador registrarPago) {
        this.registrarPago = registrarPago;
    }

    @PostMapping("/registrar")
    public ResponseEntity<TicketRegistrarDTO> registrarPago(@RequestBody PagoDTO pagoDTO) {
        try {
        	TicketRegistrarDTO ticket = registrarPago.registrarPago(pagoDTO);
        	System.out.println("prueba");
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}