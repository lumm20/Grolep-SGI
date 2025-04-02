package com.mycompany.sginegocio.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.sginegocio.controlador.AlumnoControlador;
import com.mycompany.sginegocio.excepciones.StudentException;

import mx.itson.sgi.dto.AlumnoConsultaDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/students")
public class APIAlumno {

    @Autowired
    private AlumnoControlador controlador;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> buscarAlumnos(@RequestParam String matricula) {
        try {
            List<AlumnoConsultaDTO> alumnos = controlador.obtenerAlumnosPorNombre(matricula);
            return ResponseEntity.ok().body(alumnos);
        } catch (StudentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
