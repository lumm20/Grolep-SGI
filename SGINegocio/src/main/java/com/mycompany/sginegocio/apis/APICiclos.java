package com.mycompany.sginegocio.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mycompany.sginegocio.controlador.CiclosControlador;

import mx.itson.sgi.dto.CicloConDetallesDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.DetalleCicloDTO;

import java.util.List;

@RestController
@RequestMapping("/cycles")
public class APICiclos {

    @Autowired
    private CiclosControlador cicloControlador;

    @PostMapping
    public ResponseEntity<?> crearCicloEscolar(@RequestBody CicloConDetallesDTO cicloConDetallesDTO) {
        try {
            CicloEscolarDTO nuevoCiclo = cicloControlador.crearCicloEscolar(
                    cicloConDetallesDTO.getCicloEscolar(),
                    cicloConDetallesDTO.getDetalleCiclo());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCiclo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCicloEscolar(@PathVariable String id,
            @RequestBody CicloConDetallesDTO cicloConDetallesDTO) {
        try {
            CicloEscolarDTO cicloActualizado = cicloControlador.actualizarCicloEscolar(
                    id,
                    cicloConDetallesDTO.getCicloEscolar(),
                    cicloConDetallesDTO.getDetalleCiclo());
            return ResponseEntity.ok().body(cicloActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obtenerCiclosEscolares(
            @RequestParam(required = false) String begin,
            @RequestParam(required = false) String end) {
        List<CicloConDetallesDTO> ciclos;
        if (begin != null && end != null) {
            ciclos = cicloControlador.obtenerCiclosConDetallesPorFechas(begin, end);
        } else {
            ciclos = cicloControlador.obtenerCiclosConDetalles();
        }
        if (ciclos != null && !ciclos.isEmpty()) {
            return ResponseEntity.ok().body(ciclos);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCiclosEscolaresPorId(@PathVariable String id) {
        try {
            CicloConDetallesDTO cicloConDetalles = cicloControlador.obtenerCiclosConDetallesPorId(id);
            return ResponseEntity.ok().body(cicloConDetalles);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el ciclo escolar");
        }
    }

    
}