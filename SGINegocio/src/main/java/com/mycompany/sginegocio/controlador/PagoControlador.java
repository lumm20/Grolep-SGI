/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio.controlador;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import mx.itson.sgi.dto.DetallePagoDTO;
import mx.itson.sgi.notificaciones.whatsapp.NotificacionesWhatsapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.sginegocio.excepciones.PaymentException;

import mx.itson.sgi.data_access.services.AlumnoService;
import mx.itson.sgi.data_access.services.PagoService;
import mx.itson.sgi.data_access.services.UsuarioService;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.vistas.TicketRegistrarDTO;
import mx.itson.sgi.dto.UsuarioDTO;


@Service
public class PagoControlador {
    
    @Autowired
    PagoService service;
    @Autowired
    AlumnoService alumnoService;
    @Autowired
    UsuarioService usuarioService;

    public Double obtenerTotalPagadoColegiatura(AlumnoConsultaDTO alumno, String ciclo){
        return service.obtenerTotalPagadoColegiatura(alumno, ciclo);
    }

    public TicketRegistrarDTO registrarPago(PagoDTO pago)throws PaymentException{
        System.out.println("Pago "+pago);
        String folio = generarFolio();
        System.out.println("folio: "+folio);

        validatePaymentFields(pago);
        System.out.println("fields validated");
        UsuarioDTO cajero = validateCashier(pago.getIdUsuario());
        System.out.println("cashier validated");
        AlumnoConsultaDTO alumno = validateStudent(pago.getAlumno().getMatricula());
        System.out.println("student validated");

        pago.setFecha(LocalDateTime.now());
        pago.setFolio(folio);
        service.registrarPago(pago);

        TicketRegistrarDTO ticket = new TicketRegistrarDTO();
        ticket.setFolio(folio);
        // ticket.setFecha(pago.getFecha().toLocalDate());
        // ticket.setHora(pago.getFecha().toLocalTime());
        ticket.setAlumno(alumno);
        ticket.setDetalles(pago.getCuotasPagadas());
        ticket.setMontoTotal(pago.getMontoTotal());
        ticket.setIdUsuario(cajero.getId());
        enviarNotificacion(pago);

        return ticket;
    }

    private void enviarNotificacion(PagoDTO pago) {
        NotificacionesWhatsapp notificaciones = new NotificacionesWhatsapp();
        String nombreAlumno = pago.getAlumno().getNombre();
        String numeroCel = pago.getAlumno().getNumeroCelular();
        String descuento = pago.getMontoDescuento().toString();
        String monto = pago.getMontoTotal().toString();
        String folio = pago.getFolio();
        double subtotal = pago.getMontoTotal() + pago.getMontoDescuento();
        List<DetallePagoDTO> detalles = pago.getCuotasPagadas();
        String metodoPago = pago.getMetodoPago().toString();
        StringBuilder cuotas = new StringBuilder();

        for (DetallePagoDTO detalle : detalles) cuotas.append(detalle.getConceptoCuota() + " - " + detalle.getMontoPagado() + "\n");

        notificaciones.enviarNotificacion(folio, monto, cuotas.toString(), nombreAlumno, Double.toString(subtotal), descuento, metodoPago, numeroCel);
    }

    private void validatePaymentFields(PagoDTO pago)throws PaymentException{
        String msj = null;
        System.out.println("en validacionnnn");
        if(pago.getCuotasPagadas() == null || pago.getCuotasPagadas().isEmpty()){
            msj = "No quotas selected";
        }
        System.out.println("en validacionnnn");
        if(pago.getMontoTotal() <= 0){
            msj = "Invalid total amount";
        }
        System.out.println("en validacionnnn");
        if(pago.getMetodoPago() == null){
            msj = "No payment method selected";
        }
        System.out.println("en validacionnnn");
        if(pago.getAlumno() == null || pago.getAlumno().getMatricula() == null || pago.getAlumno().getMatricula().isBlank()){
            msj = "No student selected";
        }
        System.out.println("en validacionnnn");
        if(pago.getIdUsuario() <= 0){
            msj = "No cashier selected";
        }
        System.out.println("mensaje: "+msj);
        if(msj != null){
            throw new PaymentException(msj, PaymentException.EMPTY_FIELD);
        }
        System.out.println("se acabo la validacionnn");
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