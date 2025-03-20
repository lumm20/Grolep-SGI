/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.services.PagoService;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.TicketRegistrarDTO;


@Service
public class PagoControlador {
    @Autowired
    PagoService pagoService;

    public TicketRegistrarDTO registrarPago(PagoDTO pago){
        return null;
    }
}