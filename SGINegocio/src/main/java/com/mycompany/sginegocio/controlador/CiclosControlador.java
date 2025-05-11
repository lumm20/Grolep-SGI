package com.mycompany.sginegocio.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.services.CicloEscolarService;
import mx.itson.sgi.dto.CicloConDetallesDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.DetalleCicloDTO;

import java.util.List;

@Service
public class CiclosControlador {

    @Autowired
    private CicloEscolarService cicloEscolarService;

    public CicloEscolarDTO crearCicloEscolar(CicloEscolarDTO cicloDTO, DetalleCicloDTO detalleDTO) {
        return cicloEscolarService.crearCicloEscolarConDetalles(cicloDTO, detalleDTO);
    }

    public CicloEscolarDTO actualizarCicloEscolar(String id, CicloEscolarDTO cicloDTO, DetalleCicloDTO detalleDTO) {
        return cicloEscolarService.actualizarCicloEscolarConDetalles(id, cicloDTO, detalleDTO);
    }

    public List<CicloConDetallesDTO> obtenerCiclosConDetalles() {
        return cicloEscolarService.obtenerCiclosConDetalles();
    }
}