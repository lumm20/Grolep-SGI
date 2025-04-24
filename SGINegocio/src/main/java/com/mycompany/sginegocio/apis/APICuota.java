package com.mycompany.sginegocio.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.sginegocio.controlador.CuotaControlador;

import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.CuotasDTO;
import mx.itson.sgi.dto.DetalleAdeudoDTO;

@RestController
@RequestMapping("/fees")
public class APICuota {

    @Autowired
    private CuotaControlador controlador;

    @GetMapping
    public ResponseEntity<?> obtenerCuotasPorEstudiante(
        @RequestParam String matricula,
        @RequestParam("ciclo") String cicloEscolar){
        CuotasDTO cuotas= controlador.obtenerCuotasPorAlumno(matricula,cicloEscolar);
        if(cuotas != null){
            return ResponseEntity.ok().body(cuotas);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/actual-cycle")
    public ResponseEntity<?> obtenerCicloEscolarActual(){
        CicloEscolarDTO ciclo = controlador.obtenerCicloEscolarActual();
        if(ciclo != null){
            return ResponseEntity.ok().body(ciclo);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/all-cycles")
    public ResponseEntity<?> obtenerCiclosEscolares(){
        List<CicloEscolarDTO> ciclo = controlador.obtenerCiclosEscolares();
        if(ciclo != null){
            return ResponseEntity.ok().body(ciclo);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/debit-details")
    public ResponseEntity<?> obtenerDetallesAdeudosPorEstudiante(
            @RequestParam String matricula,
            @RequestParam("ciclo") String idCiclo) {
                // List<DetalleAdeudoDTO> detalles = controlador.obtenerPagosMensuales(matricula,idCiclo);
        List<DetalleAdeudoDTO> detalles = controlador.obtenerDetallesAdeudo(matricula, idCiclo);
        if(detalles != null){
            return ResponseEntity.ok().body(detalles);
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/tuition-tcharges")
    public ResponseEntity<?> obtenerMontoTotalColegiaturas(
            @RequestParam String matricula,
            @RequestParam("ciclo") String idCiclo) {
                double total = controlador.obtenerMontoTotalColegiaturas(matricula, idCiclo);
            return ResponseEntity.ok().body(total);
    }
}
