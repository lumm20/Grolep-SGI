/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio.controlador;

import java.time.LocalDate;

import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.itson.sgi.data_access.services.PagoService;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.CuotaDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.TicketRegistrarDTO;

/**
 *
 */
@Service
public class RegistrarPagoControlador {

    private final PagoService pagoService;

    public RegistrarPagoControlador(PagoService pagoService) {
        this.pagoService = pagoService;
    }
    public TicketRegistrarDTO registrarPago(PagoDTO pagoDTO) {
        Pago pago = new Pago();
        pago.setFolio(pagoDTO.getFolio());
        pago.setFecha(pagoDTO.getFecha());
        pago.setMontoTotal(pagoDTO.getMonto());
        // pago.setAlumno(pagoDTO.getAlumno());
        // pago.setAlumno(new Alumno(pagoDTO.getMatriculaAlumno()));

        // Delegar al PagoController para registrar el pago
        Pago pagoRegistrado = pagoService.registrarPago(pago);

        // Generar y retornar el comprobante
        TicketRegistrarDTO ticket = new TicketRegistrarDTO();
        ticket.setFolio(pagoRegistrado.getFolio());
        ticket.setFecha(pagoRegistrado.getFecha());
        ticket.setMonto(pagoRegistrado.getMontoTotal());
        // ticket.setCajero(pagoRegistrado.getCajero());
        ticket.setMatriculaAlumno(pagoRegistrado.getAlumno().getMatricula());
        ticket.setMensaje("Pago registrado y comprobante generado exitosamente.");
        return ticket;
    }
}

