package com.mycompany.sginegocio.apis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.sginegocio.controlador.CuotaControlador;

import mx.itson.sgi.dto.CuotasDTO;

@RestController
@RequestMapping("/api/fees")
public class APICuota {

    @Autowired
    private CuotaControlador controlador;

    @GetMapping
    public ResponseEntity<?> obtenerCuotasPorEstudiante(
        @RequestParam String matricula,
        @RequestParam String cicloEscolar){
        CuotasDTO cuotas= controlador.obtenerCuotasPorAlumno(matricula,cicloEscolar);
        if(cuotas != null){
            return ResponseEntity.ok().body(cuotas);
        }
        return ResponseEntity.noContent().build();
    }
}
