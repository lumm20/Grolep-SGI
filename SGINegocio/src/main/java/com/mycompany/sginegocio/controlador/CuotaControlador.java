/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio.controlador;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            /*si en la lista de detalles no se incluye el mes actual, significa que no se
            ha registrado ningun pago para la colegiatura de este mes, por lo tanto, se debe
            agregar como adeudo (el monto del adeudo seria el monto base de la colegiatura, porque no ha pagado nada)
            */
            //obtener el mes inicial
            // LocalDate inicioAnio = LocalDate.of(2025, 1, 1);
            // //obtener el mes actual
            // LocalDate hoy = LocalDate.now();
            // long mesesDiferencia = ChronoUnit.MONTHS.between(inicioAnio, hoy);
            
            // Double montoBase = cuotaService.obtenerMontoBaseColegiatura(new AlumnoConsultaDTO(matricula));
            // Month sigMes;
            // for(int i=0;i<=mesesDiferencia;i++){
            //     sigMes = inicioAnio.plusMonths(i).getMonth();
            //     if(!detalles.contains(new DetalleAdeudoDTO(sigMes))){
            //         detalles.add(new DetalleAdeudoDTO(sigMes, montoBase,0.0));
            //     }
            // }

            // detalles.sort((d1,d2)->d2.getMesAdeudo().compareTo(d1.getMesAdeudo()));
            // System.out.println("detalles: "+ detalles);
            // return detalles;
        }
        return null;
    }

    public CicloEscolarDTO obtenerCicloEscolarActual(){
        return cuotaService.obtenerCicloActual();
    }
    public double obtenerMontoTotalColegiaturas(String matricula, String ciclo ){
        return cuotaService.obtenerMontoTotalColegiaturas(matricula, ciclo);
    }

}

