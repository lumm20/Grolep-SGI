package mx.itson.sgi.data_access.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.DetalleCiclo;
import mx.itson.sgi.data_access.repositories.AlumnoRepository;
import mx.itson.sgi.data_access.repositories.CicloRepository;
import mx.itson.sgi.data_access.repositories.CuotaRepository;
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

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

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

    /**
     * devuelve el ciclo actual como una entidad
     * 
     * @return
     */
    public CicloEscolar obtenerCicloActualEntidad() {
        return repository.findCicloActual().orElse(null);
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
        try {
            // Crear el ciclo escolar
            CicloEscolar ciclo = new CicloEscolar();
            ciclo.setFechaInicio(LocalDate.parse(cicloDTO.getInicio()));
            ciclo.setFechaFin(LocalDate.parse(cicloDTO.getFin()));
            ciclo.setId((ciclo.getFechaInicio().getYear() % 100) + "-" + (ciclo.getFechaFin().getYear() % 100));
            repository.save(ciclo);

            if (detalleCicloRepository.findByCicloEscolarId(ciclo.getId()).isPresent()) {
                throw new RuntimeException("El detalle del ciclo escolar con el ID proporcionado ya existe.");
            }

            // CicloEscolar cicloEscolar, Double cuotaInscripcion, Double cuotaColegiatura,
            // Double cuotaLibros, Double cuotaEventos, Double cuotaAcademias, Double
            // cuotaUniforme
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

            List<Alumno> alumnos = new ArrayList<>();
            alumnoRepository.findAll().forEach(alumnos::add);

            // Crear las cuotas para cada alumno y concepto
            for (Alumno alumno : alumnos) {
                for (Concepto concepto : Concepto.values()) {
                    Double montoBase = obtenerMontoBasePorConcepto(concepto, detalle);
                    Cuota cuota = new Cuota(montoBase, ciclo, alumno, concepto);
                    cuotaRepository.save(cuota);
                }
            }

            return new CicloEscolarDTO(ciclo.getFechaInicio().toString(), ciclo.getFechaFin().toString());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("El ciclo escolar ya tiene un detalle asociado. No se puede duplicar.");
        }
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

        // inicio
        // para actualizar los detalles de los ciclos escolares
        System.out.println("Buscando detalles del ciclo escolar con ID: " + id);
        Optional<DetalleCiclo> detalleOpt = detalleCicloRepository.findByCicloEscolarId(id);

        DetalleCiclo detalle;
        if (detalleOpt.isPresent()) {
            // Si se encuentra el detalle, se actualiza
            detalle = detalleOpt.get();
            System.out.println("Detalle encontrado, actualizando...");
        } else {
            // Si no existe, se crea uno nuevo
            System.out.println("Detalle no encontrado, creando uno nuevo...");
            detalle = crearDetalle(detalleDTO, ciclo);
        }

        // Actualizar los valores (en ambos casos)
        detalle.setCuotaInscripcion(detalleDTO.getCuotaInscripcion());
        detalle.setCuotaColegiatura(detalleDTO.getCuotaColegiatura());
        detalle.setCuotaLibros(detalleDTO.getCuotaLibros());
        detalle.setCuotaEventos(detalleDTO.getCuotaEventos());
        detalle.setCuotaAcademias(detalleDTO.getCuotaAcademias());
        detalle.setCuotaUniforme(detalleDTO.getCuotaUniforme());

        System.out.println("Guardando detalle...");
        detalleCicloRepository.save(detalle);

        // final

        System.out.println("continua aca");
        // Actualizar las cuotas para cada alumno y concepto
        List<Alumno> alumnos = new ArrayList<>();
        alumnoRepository.findAll().forEach(alumnos::add);

        for (Alumno alumno : alumnos) {
            for (Concepto concepto : Concepto.values()) {
                // Buscar la cuota existente
                Optional<Cuota> cuotaOpt = cuotaRepository.findByAlumnoAndConceptoAndCiclo(alumno, concepto, ciclo);

                if (cuotaOpt.isPresent()) {
                    // Actualizar la cuota existente
                    Cuota cuota = cuotaOpt.get();
                    cuota.setMontoBase(obtenerMontoBasePorConcepto(concepto, detalle));
                    cuotaRepository.save(cuota);
                } else {
                    // Crear una nueva cuota si no existe
                    Double montoBase = obtenerMontoBasePorConcepto(concepto, detalle);
                    Cuota nuevaCuota = new Cuota(montoBase, ciclo, alumno, concepto);
                    cuotaRepository.save(nuevaCuota);
                }
            }
        }

        // Devolver el ciclo actualizado
        return new CicloEscolarDTO(ciclo.getFechaInicio().toString(), ciclo.getFechaFin().toString());
    }

    public DetalleCiclo crearDetalle(DetalleCicloDTO detalleDTO, CicloEscolar ciclo) {
        System.out.println("Buscando si ya existe un detalle para el ciclo escolar con ID: " + ciclo.getId());

        if (detalleCicloRepository.findByCicloEscolarId(ciclo.getId()).isPresent()) {
            throw new RuntimeException("Ya existe un detalle para este ciclo escolar. No se puede crear otro.");
        }

        System.out.println("Detalle no encontrado, creando uno nuevo...");
        DetalleCiclo nuevoDetalle = new DetalleCiclo();
        nuevoDetalle.setCicloEscolar(ciclo);
        nuevoDetalle.setCuotaInscripcion(detalleDTO.getCuotaInscripcion());
        nuevoDetalle.setCuotaColegiatura(detalleDTO.getCuotaColegiatura());
        nuevoDetalle.setCuotaLibros(detalleDTO.getCuotaLibros());
        nuevoDetalle.setCuotaEventos(detalleDTO.getCuotaEventos());
        nuevoDetalle.setCuotaAcademias(detalleDTO.getCuotaAcademias());
        nuevoDetalle.setCuotaUniforme(detalleDTO.getCuotaUniforme());

        System.out.println("Guardando nuevo detalle...");
        return detalleCicloRepository.save(nuevoDetalle);
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
            CicloConDetallesDTO cicloConDetallesDTO = new CicloConDetallesDTO(cicloDTO, detalleDTO);

            ciclosConDetalles.add(cicloConDetallesDTO);
        }

        return ciclosConDetalles;
    }

    public CicloConDetallesDTO obtenerCicloConDetallesPorId(String id) {
        CicloEscolar ciclo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciclo escolar no encontrado"));

        Optional<DetalleCiclo> detalleOpt = detalleCicloRepository.findByCicloEscolarId(id);
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
        cicloDTO.setId(ciclo.getId());

        CicloConDetallesDTO cicloConDetallesDTO = new CicloConDetallesDTO(cicloDTO, detalleDTO);

        return cicloConDetallesDTO;
    }

    public List<CicloConDetallesDTO> obtenerCiclosConDetallesPorFechas(String begin, String end) {
        LocalDate fechaInicio = LocalDate.parse(begin);
        LocalDate fechaFin = LocalDate.parse(end);
        List<CicloEscolar> ciclos = repository.findByFechaFinGreaterThanEqualAndFechaInicioLessThanEqual(fechaInicio, fechaFin);
        List<CicloConDetallesDTO> ciclosConDetalles = new ArrayList<>();
        for (CicloEscolar ciclo : ciclos) {
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
            CicloEscolarDTO cicloDTO = new CicloEscolarDTO(ciclo.getFechaInicio().toString(), ciclo.getFechaFin().toString());
            cicloDTO.setId(ciclo.getId());
            CicloConDetallesDTO cicloConDetallesDTO = new CicloConDetallesDTO(cicloDTO, detalleDTO);
            ciclosConDetalles.add(cicloConDetallesDTO);
        }
        return ciclosConDetalles;
    }

    private Double obtenerMontoBasePorConcepto(Concepto concepto, DetalleCiclo detalle) {
        switch (concepto) {
            case LIBROS:
                return detalle.getCuotaLibros();
            case EVENTOS:
                return detalle.getCuotaEventos();
            case ACADEMIAS:
                return detalle.getCuotaAcademias();
            case UNIFORMES:
                return detalle.getCuotaUniforme();
            case COLEGIATURA:
                return detalle.getCuotaColegiatura();
            case INSCRIPCION:
                return detalle.getCuotaInscripcion();
            case PAGO_ATRASADO:
                return 0.0;
            default:
                throw new IllegalArgumentException("Concepto no reconocido: " + concepto);
        }
    }
}
