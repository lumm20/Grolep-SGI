/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio.controlador;

import java.time.LocalDate;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.itson.sgi.data_access.services.CuotaService;
import mx.itson.sgi.data_access.dto.AdeudoDTO;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Cuota;

/**
 *
 */
@RestController
@RequestMapping("/SGI/cuotas")
public class CuotaControlador {

    private final CuotaService cuotaService;

    public CuotaControlador(CuotaService cuotaService) {
        this.cuotaService = cuotaService;
    }

    @GetMapping("/obtenerCuotas/{matricula}")
    public ResponseEntity<?> obtenerCuotasConAdeudosPorAlumno(
            @PathVariable String matricula,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
    ) {
        try {
            System.out.println("Buscando ciclo escolar para fechas: " + fechaInicio + " - " + fechaFin);
            CicloEscolar cicloEscolar = cuotaService.obtenerCicloEscolarPorFechas(new CicloEscolar(fechaInicio, fechaFin));
            
            if (cicloEscolar == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            
            System.out.println("Ciclo escolar encontrado: " + cicloEscolar.getId());
            List<AdeudoDTO> adeudos = cuotaService.obtenerCuotasConAdeudosPorAlumno(matricula, cicloEscolar.getId());
            return ResponseEntity.ok(adeudos);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null
            );
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrarCuota(@RequestBody Cuota cuota) {
        try {
            cuotaService.agregarCuota(cuota);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


