/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio.controlador;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.sginegocio.excepciones.PaymentException;

import mx.itson.sgi.data_access.services.AlumnoService;
import mx.itson.sgi.data_access.services.PagoService;
import mx.itson.sgi.data_access.services.UsuarioService;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.TicketRegistrarDTO;
import mx.itson.sgi.dto.UsuarioDTO;


@Service
public class PagoControlador {
    
    @Autowired
    PagoService service;
    @Autowired
    AlumnoService alumnoService;
    @Autowired
    UsuarioService usuarioService;

    public TicketRegistrarDTO registrarPago(PagoDTO pago)throws PaymentException{
        String folio = generarFolio();

        validatePaymentFields(pago);
        UsuarioDTO cajero = validateCashier(pago.getUsuario().getId());
        AlumnoConsultaDTO alumno = validateStudent(pago.getAlumno().getMatricula());

        pago.setFecha(LocalDateTime.now());
        pago.setFolio(folio);
        service.registrarPago(pago);

        TicketRegistrarDTO ticket = new TicketRegistrarDTO(folio, pago.getFecha(), 
        pago.getCuotasPagadas(), pago.getMontoTotal(), 
        cajero.getNombre(),alumno.getNombre());

        return ticket;
    }


    private void validatePaymentFields(PagoDTO pago)throws PaymentException{
        String msj = null;
        if(pago.getCuotasPagadas() == null || pago.getCuotasPagadas().isEmpty()){
            msj = "No quotas selected";
        }
        if(pago.getMontoTotal() <= 0){
            msj = "Invalid total amount";
        }
        if(pago.getMetodoPago() == null){
            msj = "No payment method selected";
        }
        if(pago.getAlumno() == null || pago.getAlumno().getMatricula().isBlank()){
            msj = "No student selected";
        }
        if(pago.getUsuario() == null || pago.getUsuario().getId() == 0){
            msj = "No cashier selected";
        }
        if(msj != null){
            throw new PaymentException(msj, PaymentException.EMPTY_FIELD);
        }
    }

    private UsuarioDTO validateCashier(long cashierId)throws PaymentException{
        UsuarioDTO usuario = usuarioService.findUserById(cashierId);
        if (usuario == null) {
            throw new PaymentException("User not found", PaymentException.CASHIER_NOT_FOUND);
        }
        return usuario;
    }

    private AlumnoConsultaDTO validateStudent(String matricula)throws PaymentException{
        AlumnoConsultaDTO alumno = alumnoService.buscarAlumnoPorMatricula(matricula);
        if(alumno == null){
            throw new PaymentException("Student not found", PaymentException.STUDENT_NOT_FOUND);
        }
        return alumno;
    }
    
    /**
     * Genera un folio unico para un pago con el formato: PYYYYMMDDXXXXX
     * Donde YYYYMMDD es la fecha actual y XXXXX es un numero secuencial
     * 
     * @return String con el folio generado
     */
    private String generarFolio() {
        Long lastNumber = service.getCantidadPagos();
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        Long nuevoNumero = lastNumber + 1;

        String numeroFormateado = String.format("%05d", nuevoNumero);
        return "P".concat(fechaFormateada).concat(numeroFormateado);
    }
}