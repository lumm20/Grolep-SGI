package com.mycompany.sginegocio.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.sginegocio.excepciones.StudentException;

import mx.itson.sgi.data_access.services.AlumnoService;
import mx.itson.sgi.dto.AlumnoConsultaDTO;

@Service
public class AlumnoControlador {

    @Autowired
    AlumnoService service;

    public List<AlumnoConsultaDTO> obtenerAlumnosPorNombre(String nombre)throws StudentException {
        if(!nombre.isBlank() && nombre.matches("[a-zA-Z]+")){
            return service.buscarAlumnosPorNombre(nombre);
        }
        throw new StudentException("Invalid name", StudentException.INVALID_NAME);
    }
}
