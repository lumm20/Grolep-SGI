/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.services.CuotaService;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.CuotaConsultadaDTO;
import mx.itson.sgi.dto.CuotasDTO;

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
                        cuotasDTO.setAdeudoColegiatura(cu.getMontoBase());
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

    // public CicloEscolarDTO obtenerCicloEscolar(String idCiclo){
    //     if(idCiclo != null && !idCiclo.isBlank() && idCiclo.matches("2[]")){
    //         return cuotaService.obtenerCicloEscolar(idCiclo);
    //     }
    // }
}


