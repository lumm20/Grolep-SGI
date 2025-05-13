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

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.CuotaMensual;
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

    public CuotasDTO obtenerCuotasPorAlumno(String matricula, String cicloEscolar) throws IllegalArgumentException {
        CicloEscolar cicloE = cicloService.obtenerCicloEscolarPorId(cicloEscolar);
        System.out.println(cicloE);
        if (cicloE == null) {
            throw new IllegalArgumentException("El ciclo escolar ingresado, no existe");
        }
        List<CuotaConsultadaDTO> cuotas = cuotaService.obtenerCuotasPorAlumno(matricula, cicloEscolar);
        if (cuotas != null && !cuotas.isEmpty()) {
            CuotasDTO cuotasDTO = new CuotasDTO();
            System.out.println("el error ocurre en este for para " + cicloEscolar);
            for (CuotaConsultadaDTO cu : cuotas) {
                System.out.println(cu);
                String concepto = cu.getConcepto();
                Double adeudo = cu.getAdeudo();
                switch (concepto) {
                    case "COLEGIATURA":
                        CuotaMensual colegiaturaMesActual = null;
                        System.out.println(cicloEscolar + " es igual " + cicloService.obtenerCicloActual().getId());
                        if (cicloEscolar.equals(cicloService.obtenerCicloActual().getId())) {
                            System.out.println("primero");
                            colegiaturaMesActual = cuotaService.obtenerColegiaturaMesActual(new Alumno(matricula),
                                    new CicloEscolar(cicloEscolar));
                        } else {
                            System.out.println("segundo");
                            LocalDate fechaFin = cicloE.getFechaFin();// buscar otra fecha a obtener
                            fechaFin = fechaFin.minusDays(fechaFin.getDayOfMonth() - 1);
                            System.out.println("fecha fin: " + fechaFin);
                            colegiaturaMesActual = cuotaService.obtenerCuotaPorAlumnoCicloMes(new Alumno(matricula),
                                    new CicloEscolar(cicloEscolar), fechaFin);
                        }
                        System.out.println("Esta es la colegiatura actual: " + colegiaturaMesActual);
                        if (colegiaturaMesActual == null) {
                            System.out.println("No se encontró cuota mensual para el alumno y ciclo escolar.");
                            cuotasDTO.setAdeudoColegiatura(0.0); // O un valor predeterminado
                        } else {
                            cuotasDTO.setAdeudoColegiatura(colegiaturaMesActual.getAdeudo());
                        }
                        System.out.println("adeudo: " + adeudo);
                        cuotasDTO.setAdeudoVencido(adeudo);
                        System.out.println("Continua aqui si se ve el problema esta abajo");
                        // System.out.println("adeudo en colegiatura:"+adeudo);
                        // if (adeudo == null) {
                        // // Cuando no hay pagos registrados, el adeudo es el monto completo
                        // cuotasDTO.setAdeudoColegiatura(cu.getMontoBase());
                        // cuotasDTO.setAdeudoVencido(0.0); // No hay adeudos vencidos
                        // } else if (adeudo >= cu.getMontoBase()) {
                        // // Cuando el adeudo supera el monto base (caso especial)
                        // cuotasDTO.setAdeudoColegiatura(0.0); // Ya está pagado completamente
                        // cuotasDTO.setAdeudoVencido(adeudo - cu.getMontoBase()); // Excedente como
                        // vencido
                        // } else {
                        // // Caso normal: adeudo parcial
                        // cuotasDTO.setAdeudoColegiatura(cu.getMontoBase() - adeudo);
                        // cuotasDTO.setAdeudoVencido(adeudo);
                        // }
                        break;
                    case "UNIFORMES":
                        cuotasDTO.setAdeudoUniformes(adeudo != null ? adeudo : 0.0);
                        break;
                    case "LIBROS":
                        cuotasDTO.setAdeudoLibros(adeudo != null ? adeudo : 0.0);
                        break;
                    case "INSCRIPCION":
                        cuotasDTO.setAdeudoInscripcion(adeudo != null ? adeudo : 0.0);
                        break;
                    case "ACADEMIAS":
                        cuotasDTO.setAdeudoAcademias(adeudo != null ? adeudo : 0.0);
                        break;
                    case "EVENTOS":
                        cuotasDTO.setAdeudoEventos(adeudo != null ? adeudo : 0.0);
                        break;
                }
            }
            System.out.println("llega aca final" + cuotasDTO.getAdeudoVencido());
            return cuotasDTO;
        }
        return null;
    }

    public List<DetalleAdeudoDTO> obtenerDetallesAdeudo(String matricula, String idCiclo) {
        List<DetalleAdeudoDTO> detalles = cuotaService.obtenerDetallesAdeudos(matricula, idCiclo);
        if (detalles != null && !detalles.isEmpty()) {
            return detalles;
        }
        return null;
    }
    // public List<DetalleAdeudoDTO> obtenerPagosMensuales(String matricula, String
    // idCiclo){
    // List<DetalleAdeudoDTO> detalles =
    // cuotaService.obtenerPagosMensualesAlumno(matricula, idCiclo);
    // Double montoBase = cuotaService.obtenerMontoBaseColegiatura(new
    // AlumnoConsultaDTO(matricula), new CicloEscolarDTO(idCiclo));
    // if(detalles != null && !detalles.isEmpty()){
    // detalles.forEach(detalle->{
    // detalle.setMontoBase(montoBase);
    // });
    // return calcularAdeudos(detalles);
    // }
    // return null;
    // }

    private List<DetalleAdeudoDTO> calcularAdeudos(List<DetalleAdeudoDTO> detalles) {
        List<DetalleAdeudoDTO> detallesActualizados = new ArrayList<>();
        double adeudoAcumulado = 0, adeudoGenerado = 0;
        for (DetalleAdeudoDTO detalleAdeudoDTO : detalles) {
            if (detalleAdeudoDTO.getMontoBase() + adeudoAcumulado - detalleAdeudoDTO.getMontoPagado() > 0) {
                adeudoGenerado = detalleAdeudoDTO.getMontoBase() - detalleAdeudoDTO.getMontoPagado();
                adeudoAcumulado += adeudoGenerado;
            } else {
                adeudoAcumulado = 0;
            }
            detalleAdeudoDTO.setMontoAdeudo(adeudoAcumulado);
            detallesActualizados.add(detalleAdeudoDTO);
        }
        System.out.println("--------------------------------");
        System.out.println(detalles);
        System.out.println("--------------------------------");
        return detalles;
    }

    public CicloEscolarDTO obtenerCicloEscolarActual() {
        return cicloService.obtenerCicloActual();
    }

    public List<CicloEscolarDTO> obtenerCiclosEscolares() {
        return cicloService.obtenerCiclos();
    }

    public double obtenerMontoTotalColegiaturas(String matricula, String ciclo) {
        return cuotaService.obtenerMontoTotalColegiaturas(matricula, ciclo);
    }

}
