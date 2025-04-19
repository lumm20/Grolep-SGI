/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio.controlador;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.services.CicloEscolarService;
import mx.itson.sgi.data_access.services.CuotaService;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.CuotaConsultadaDTO;
import mx.itson.sgi.dto.CuotasDTO;
import mx.itson.sgi.dto.DetalleAdeudoDTO;

/**
 *
 */
@Service
public class CuotaControlador {
    
    @Autowired
    private CuotaService cuotaService;
    @Autowired
    private CicloEscolarService cicloService;
    
    public CuotasDTO obtenerCuotasPorAlumno(String matricula, String cicloEscolar){
        List<CuotaConsultadaDTO> cuotas = cuotaService.obtenerCuotasPorAlumno(matricula, cicloEscolar);
        if(cuotas != null && !cuotas.isEmpty()){
            CuotasDTO cuotasDTO = new CuotasDTO();
            for (CuotaConsultadaDTO cu : cuotas) {
                System.out.println(cu);
                String concepto= cu.getConcepto();
                Double adeudo = cu.getAdeudo();
                switch (concepto) {
                    case "COLEGIATURA":
                        if(adeudo >=cu.getMontoBase()){
                            cuotasDTO.setAdeudoColegiatura(cu.getMontoBase());
                        }else{
                            cuotasDTO.setAdeudoColegiatura(cu.getMontoBase()-adeudo);
                        }
                        cuotasDTO.setAdeudoVencido(adeudo);
                        break;
                    case "UNIFORMES":
                        cuotasDTO.setAdeudoUniformes(adeudo);
                        break;
                    case "LIBROS":
                        cuotasDTO.setAdeudoLibros(adeudo);
                        break;
                    case "INSCRIPCION":
                        cuotasDTO.setAdeudoInscripcion(adeudo);
                        break;
                    case "ACADEMIAS":
                        cuotasDTO.setAdeudoAcademias(adeudo);    
                        break;
                    case "EVENTOS":
                        cuotasDTO.setAdeudoEventos(adeudo);
                        break;
                }
            }
            return cuotasDTO;
        }
        return null;
    }

    public List<DetalleAdeudoDTO> obtenerDetallesAdeudo(String matricula, String idCiclo){
        System.out.println("entre al metodo en controlador");
        List<DetalleAdeudoDTO> detalles = cuotaService.obtenerDetalleAdeudosColegiatura(matricula, idCiclo);
        if(detalles != null && !detalles.isEmpty()){

            return detalles;
        }
        return null;
    }
    public List<DetalleAdeudoDTO> obtenerPagosMensuales(String matricula, String idCiclo){
        List<DetalleAdeudoDTO> detalles = cuotaService.obtenerPagosMensualesAlumno(matricula, idCiclo);
        Double montoBase = cuotaService.obtenerMontoBaseColegiatura(new AlumnoConsultaDTO(matricula), new CicloEscolarDTO(idCiclo));
        if(detalles != null && !detalles.isEmpty()){
            detalles.forEach(detalle->{
                detalle.setMontoBase(montoBase);
            });
            return calcularAdeudos(detalles);
        }
        return null;
    }

    private List<DetalleAdeudoDTO> calcularAdeudos(List<DetalleAdeudoDTO> detalles){
        List<DetalleAdeudoDTO> detallesActualizados = new ArrayList<>();
        double adeudoAcumulado = 0, adeudoGenerado = 0;
        for (DetalleAdeudoDTO detalleAdeudoDTO : detalles) {
            if(detalleAdeudoDTO.getMontoBase()+adeudoAcumulado - detalleAdeudoDTO.getMontoPagado() > 0){
                adeudoGenerado = detalleAdeudoDTO.getMontoBase() - detalleAdeudoDTO.getMontoPagado();
                adeudoAcumulado+= adeudoGenerado;
            }else{
                adeudoAcumulado =0;
            }
            detalleAdeudoDTO.setMontoAdeudo(adeudoAcumulado);
            detallesActualizados.add(detalleAdeudoDTO);
        }
        System.out.println("--------------------------------");
        System.out.println(detalles);
        System.out.println("--------------------------------");
        return detalles;
    }

    public CicloEscolarDTO obtenerCicloEscolarActual(){
        return cicloService.obtenerCicloActual();
    }
    public List<CicloEscolarDTO> obtenerCiclosEscolares(){
        return cicloService.obtenerCiclos();
    }
    public double obtenerMontoTotalColegiaturas(String matricula, String ciclo ){
        return cuotaService.obtenerMontoTotalColegiaturas(matricula, ciclo);
    }

}

