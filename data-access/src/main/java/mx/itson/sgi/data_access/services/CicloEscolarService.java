package mx.itson.sgi.data_access.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.DetalleCiclo;
import mx.itson.sgi.data_access.repositories.CicloRepository;
import mx.itson.sgi.data_access.repositories.DetalleCicloRepository;
import mx.itson.sgi.dto.CicloConDetallesDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.DetalleCicloDTO;

@Component
public class CicloEscolarService {

    @Autowired
    CicloRepository repository;

    @Autowired
    private DetalleCicloRepository detalleCicloRepository;

    public CicloEscolar obtenerCicloEscolarPorId(String id) {
        Optional<CicloEscolar> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public CicloEscolar obtenerCicloEscolarPorFechas(CicloEscolar ciclo) {
        Optional<CicloEscolar> optional = repository.findByFechaInicioAndFechaFin(ciclo.getFechaInicio(),
                ciclo.getFechaFin());
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public CicloEscolarDTO obtenerCicloActual() {
        Optional<CicloEscolar> optional = repository.findCicloActual();
        if (optional.isPresent()) {
            CicloEscolar ciclo = optional.get();
            CicloEscolarDTO dto = new CicloEscolarDTO(ciclo.getFechaInicio().toString(),
                    ciclo.getFechaFin().toString());
            dto.setId(ciclo.getId());
            return dto;
        }
        return null;
    }

    public List<CicloEscolarDTO> obtenerCiclos() {
        List<CicloEscolar> ciclos = (List<CicloEscolar>) repository.findAll();
        List<CicloEscolarDTO> dtos = new ArrayList<>();
        if (ciclos != null) {
            ciclos.stream().forEach(c -> {
                CicloEscolarDTO dto = new CicloEscolarDTO(c.getFechaInicio().toString(), c.getFechaFin().toString());
                dto.setId(c.getId());
                dtos.add(dto);
            });
            System.out.println("??");
            System.out.println(dtos);
            return dtos;
        }
        return new ArrayList<CicloEscolarDTO>();
    }

    @Transactional
    protected void cargarCiclosEscolares() {
        CicloEscolar ciclo1 = new CicloEscolar();
        ciclo1.setId("23-24");
        ciclo1.setFechaInicio(LocalDate.of(2023, 8, 15));
        ciclo1.setFechaFin(LocalDate.of(2024, 7, 15));
        repository.save(ciclo1);

        CicloEscolar ciclo2 = new CicloEscolar();
        ciclo2.setId("24-25");
        ciclo2.setFechaInicio(LocalDate.of(2024, 8, 15));
        ciclo2.setFechaFin(LocalDate.of(2025, 7, 15));
        repository.save(ciclo2);
    }

    // metodos del controlador de la API de ciclos escolares

    @Transactional
    public CicloEscolarDTO crearCicloEscolarConDetalles(CicloEscolarDTO cicloDTO, DetalleCicloDTO detalleDTO) {
        // Crear el ciclo escolar
        CicloEscolar ciclo = new CicloEscolar();
        ciclo.setFechaInicio(LocalDate.parse(cicloDTO.getInicio()));
        ciclo.setFechaFin(LocalDate.parse(cicloDTO.getFin()));
        ciclo.setId((ciclo.getFechaInicio().getYear() % 100) + "-" + (ciclo.getFechaFin().getYear() % 100));
        repository.save(ciclo);

        //CicloEscolar cicloEscolar, Double cuotaInscripcion, Double cuotaColegiatura, Double cuotaLibros, Double cuotaEventos, Double cuotaAcademias, Double cuotaUniforme
        // Crear los detalles del ciclo
        DetalleCiclo detalle = new DetalleCiclo();
        detalle.setCicloEscolar(ciclo);
        detalle.setCuotaInscripcion(detalleDTO.getCuotaInscripcion());
        detalle.setCuotaColegiatura(detalleDTO.getCuotaColegiatura());
        detalle.setCuotaLibros(detalleDTO.getCuotaLibros());
        detalle.setCuotaEventos(detalleDTO.getCuotaEventos());
        detalle.setCuotaAcademias(detalleDTO.getCuotaAcademias());
        detalle.setCuotaUniforme(detalleDTO.getCuotaUniforme());
        detalleCicloRepository.save(detalle);

        return new CicloEscolarDTO(ciclo.getFechaInicio().toString(), ciclo.getFechaFin().toString());
    }

    @Transactional
    public CicloEscolarDTO actualizarCicloEscolarConDetalles(String id, CicloEscolarDTO cicloDTO,
            DetalleCicloDTO detalleDTO) {
        // Buscar el ciclo escolar existente
        System.out.println("Entro, el id del ciclo es: " + id);
        CicloEscolar ciclo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciclo no encontrado"));

        System.out.println("el ciclo es: " + ciclo.getId());

        // Actualizar las fechas del ciclo escolar
        ciclo.setFechaInicio(LocalDate.parse(cicloDTO.getInicio()));
        ciclo.setFechaFin(LocalDate.parse(cicloDTO.getFin()));

        repository.save(ciclo);

        // para actualizar los detalles de los ciclos escolares
        DetalleCiclo detalle = detalleCicloRepository.findByCicloEscolarId(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        detalle.setCuotaInscripcion(detalleDTO.getCuotaInscripcion());
        detalle.setCuotaColegiatura(detalleDTO.getCuotaColegiatura());
        detalle.setCuotaLibros(detalleDTO.getCuotaLibros());
        detalle.setCuotaEventos(detalleDTO.getCuotaEventos());
        detalle.setCuotaAcademias(detalleDTO.getCuotaAcademias());
        detalle.setCuotaUniforme(detalleDTO.getCuotaUniforme());
        detalleCicloRepository.save(detalle);

        // Devolver el ciclo actualizado
        return new CicloEscolarDTO(ciclo.getFechaInicio().toString(), ciclo.getFechaFin().toString());
    }

    @Transactional
    public List<CicloConDetallesDTO> obtenerCiclosConDetalles() {
        List<CicloEscolar> ciclos = new ArrayList<>();
        repository.findAll().forEach(ciclos::add);
        List<CicloConDetallesDTO> ciclosConDetalles = new ArrayList<>();

        for (CicloEscolar ciclo : ciclos) {
            // Obtener los detalles del ciclo
            Optional<DetalleCiclo> detalleOpt = detalleCicloRepository.findByCicloEscolarId(ciclo.getId());
            DetalleCicloDTO detalleDTO = null;

            if (detalleOpt.isPresent()) {
                DetalleCiclo detalle = detalleOpt.get();
                detalleDTO = new DetalleCicloDTO();
                detalleDTO.setId(detalle.getId());
                detalleDTO.setIdCicloEscolar(detalle.getCicloEscolar().getId());
                detalleDTO.setCuotaInscripcion(detalle.getCuotaInscripcion());
                detalleDTO.setCuotaColegiatura(detalle.getCuotaColegiatura());
                detalleDTO.setCuotaLibros(detalle.getCuotaLibros());
                detalleDTO.setCuotaEventos(detalle.getCuotaEventos());
                detalleDTO.setCuotaAcademias(detalle.getCuotaAcademias());
                detalleDTO.setCuotaUniforme(detalle.getCuotaUniforme());
            }

            // Construir el DTO combinado
            CicloEscolarDTO cicloDTO = new CicloEscolarDTO(ciclo.getFechaInicio().toString(),
                    ciclo.getFechaFin().toString());
            CicloConDetallesDTO cicloConDetallesDTO = new CicloConDetallesDTO();
            cicloConDetallesDTO.setCicloEscolar(cicloDTO);
            cicloConDetallesDTO.setDetalleCiclo(detalleDTO);

            ciclosConDetalles.add(cicloConDetallesDTO);
        }

        return ciclosConDetalles;
    }
}
