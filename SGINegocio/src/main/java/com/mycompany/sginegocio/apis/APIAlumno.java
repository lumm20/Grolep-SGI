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

import jakarta.persistence.EntityNotFoundException;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.AlumnoRegistroDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<?> registrarAlumno(@RequestBody AlumnoRegistroDTO alumnoDTO) {
        try {
            System.out.println("AlumnoDTO recibido: " + alumnoDTO.toString());
            controlador.crearEstudiante(alumnoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Alumno registrado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar alumno: " + e.getMessage());
        }
    }
    
    @PutMapping
    public ResponseEntity<?> actualizarAlumno(@RequestBody AlumnoRegistroDTO alumnoDTO) {
        try {
            controlador.actualizarEstudiante(alumnoDTO);
            return ResponseEntity.ok().body("Alumno actualizado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar alumno: " + e.getMessage());
        }
    }

    @GetMapping("/allStudents")
    public ResponseEntity<?> obtenerListaAlumnos() {
        List<AlumnoRegistroDTO> alumnosDTO = controlador.convertirListaAlumnosADTO();
        return ResponseEntity.ok().body(alumnosDTO);
    }

    @GetMapping("/byID/{matricula}")
    public ResponseEntity<?> obtenerAlumnoPorMatricula(@PathVariable String matricula) {
        try {
            AlumnoRegistroDTO alumnoDTO = controlador.convertirAlumnoADTO(matricula);
            return ResponseEntity.ok().body(alumnoDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

     @GetMapping("/byName/{nombre}")
    public ResponseEntity<?> obtenerAlumnoPorNombre(@PathVariable String nombre) {
        try {
            List<AlumnoRegistroDTO> alumnoDTO = controlador.obtenerAlumnosPorNombreCompleto(nombre);
            return ResponseEntity.ok().body(alumnoDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
