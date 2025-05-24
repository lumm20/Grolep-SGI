package mx.itson.sgi.data_access.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Predicate;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.CuotaMensual;
import mx.itson.sgi.data_access.entities.DetallePago;
import mx.itson.sgi.data_access.entities.MetodoPago;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.data_access.entities.Rol;
import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.PagoRepository;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.CuotaConsultadaDTO;
import mx.itson.sgi.dto.DetallePagoDTO;
import mx.itson.sgi.dto.MetodosPagoDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.PagoReporteDTO;
import mx.itson.sgi.dto.RolDTO;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.itson.sgi.dto.FiltroPagoDTO;

@Service
public class PagoService {
    @Autowired
    private PagoRepository repository;
    @Autowired
    private CuotaService cuotaService;

    public Pago obtenerPagoPorFolio(String folio) {
        Optional<Pago> pago = repository.findById(folio);
        if (pago.isPresent()) {
            return pago.get();
        }
        return null;
    }

    public List<Pago> obtenerPagosPorEstudiante(String matriculaEstudiante) {
        List<Pago> pago = repository.findPagosPorEstudiante(new Alumno(matriculaEstudiante));

        if (pago != null && !pago.isEmpty()) {
            return pago;
        }
        return new ArrayList<Pago>();
    }

    public List<Pago> obtenerPagosPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Pago> pago = repository.findByFechaBetween(fechaFin, fechaFin);

        if (pago != null && !pago.isEmpty()) {
            return pago;
        }
        return new ArrayList<Pago>();
    }

    @Transactional
    public void registrarPago(PagoDTO pagoDTO) {
        MetodoPago metodo;
        if (pagoDTO.getMetodoPago().equals(MetodosPagoDTO.Efectivo)) {
            metodo = MetodoPago.EFECTIVO;
        } else if (pagoDTO.getMetodoPago().equals(MetodosPagoDTO.Tarjeta)) {
            metodo = MetodoPago.TARJETA;
        } else {
            metodo = MetodoPago.TRANSFERENCIA;
        }
        Usuario cajero = new Usuario(pagoDTO.getIdUsuario());
        Alumno alumno = new Alumno(pagoDTO.getAlumno().getMatricula());
        Pago pago = new Pago(pagoDTO.getFolio(), pagoDTO.getFecha(), pagoDTO.getMontoTotal(),
                cajero, metodo, alumno, pagoDTO.getMontoDescuento(), pagoDTO.getTipoDescuento());
        CicloEscolar ciclo = new CicloEscolar(pagoDTO.getIdCicloEscolar());
        List<DetallePagoDTO> detallesDTO = pagoDTO.getCuotasPagadas();
        List<DetallePago> detalles = convertirDetallesPagos(detallesDTO, alumno, ciclo, pago);
        pago.setDetalles(detalles);
        repository.save(pago);
    }

    private List<DetallePago> convertirDetallesPagos(List<DetallePagoDTO> dtos, Alumno alumno, CicloEscolar ciclo,
            Pago pago) {
        List<DetallePago> detalles = new ArrayList<>();
        Cuota cuota;
        Concepto concepto;
        for (DetallePagoDTO dto : dtos) {
            concepto = Concepto.valueOf(dto.getConceptoCuota());
            if (concepto.equals(Concepto.COLEGIATURA)) {
                List<DetallePago> detallesColegiaturas = agregarDetallesColegiaturas(detalles, alumno, ciclo, dto,
                        pago);
                cuotaService.actualizarAdeudoCuotasMensuales(detallesColegiaturas);
                detalles.addAll(detallesColegiaturas);
            } else {
                cuota = cuotaService.obtenerCuotaPorAlumnoConceptoCiclo(alumno, concepto, ciclo);
                detalles.add(new DetallePago(cuota, dto.getMontoPagado(), pago));
            }
        }
        return detalles;
    }

    private List<DetallePago> agregarDetallesColegiaturas(List<DetallePago> detalles, Alumno alumno,
            CicloEscolar ciclo, DetallePagoDTO dto, Pago pago) {
        List<CuotaMensual> colegiaturas = cuotaService.obtenerColegiaturasConAdeudo(alumno.getMatricula(),
                ciclo.getId());
        if (colegiaturas != null) {
            System.out.println(colegiaturas);
            Double montoPagado = dto.getMontoPagado();
            Double adeudo;
            for (CuotaMensual colegiatura : colegiaturas) {
                adeudo = colegiatura.getAdeudo();
                if (montoPagado >= adeudo) {
                    colegiatura.setAdeudo(0.0);
                    colegiatura.setMontoPagado(colegiatura.getMontoPagado() + adeudo);
                    detalles.add(new DetallePago(colegiatura, montoPagado, pago));
                    montoPagado -= adeudo;
                } else if (montoPagado > 0.0) {
                    colegiatura.setAdeudo(adeudo - montoPagado);
                    colegiatura.setMontoPagado(colegiatura.getMontoPagado() + montoPagado);
                    detalles.add(new DetallePago(colegiatura, montoPagado, pago));
                    montoPagado = 0.0;
                } else {
                    break;
                }

            }
        }
        return detalles;
    }

    public long getCantidadPagos() {
        return repository.count();
    }

    public long getCantidadPagosMensualesAlumno(AlumnoConsultaDTO alumno, String ciclo) {
        return repository.countPagosMensuales(new Alumno(alumno.getMatricula()), new CicloEscolar(ciclo));
    }

    public Double obtenerTotalPagadoColegiatura(AlumnoConsultaDTO alumno, String ciclo) {
        return repository.findTotalPagadoColegiatura(new Alumno(alumno.getMatricula()), new CicloEscolar(ciclo));
    }

    // Metodo para filtros de pagos
    public List<PagoReporteDTO> filtrarPagos(FiltroPagoDTO filtro) {
        return repository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por rango de fechas
            if (filtro.getFechaDesde() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), filtro.getFechaDesde().atStartOfDay()));
            }
            if (filtro.getFechaHasta() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fecha"),
                        filtro.getFechaHasta().atTime(23, 59, 59)));
            }

            // Filtro por rango de montos
            if (filtro.getMontoMinimo() != null && filtro.getMontoMinimo() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("montoTotal"), filtro.getMontoMinimo()));
            }
            if (filtro.getMontoMaximo() != null && filtro.getMontoMaximo() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("montoTotal"), filtro.getMontoMaximo()));
            }

            if (filtro.getUsuario() != null && filtro.getUsuario().getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cajero").get("id"), filtro.getUsuario().getId()));
            }
            if (filtro.getAlumno() != null && filtro.getAlumno().getMatricula() != null
                    && !filtro.getAlumno().getMatricula().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(root.get("alumno").get("nombre"), "%" + filtro.getAlumno().getNombre() + "%"));
            }

            // Filtro por método de pago
            if (filtro.getMetodoPago() != null) {
                // Convertir de DTO a Entidad antes de comparar
                MetodoPago metodoPagoBD = MetodoPago.valueOf(filtro.getMetodoPago().name());
                predicates.add(criteriaBuilder.equal(root.get("metodoPago"), metodoPagoBD));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }).stream().map(this::convertirPagoReporteADTO).toList(); // Convertir Pago a PagoDTO
    }

    private PagoReporteDTO convertirPagoReporteADTO(Pago pago) {
        PagoReporteDTO dto = new PagoReporteDTO();
        dto.setMontoTotal(pago.getMontoTotal());
        dto.setFolio(pago.getFolio());
        dto.setFecha(pago.getFecha());
        dto.setAlumno(new AlumnoConsultaDTO(pago.getAlumno().getMatricula(), pago.getAlumno().getNombre(),
                pago.getAlumno().getTelefonoPadre()));
        dto.setMetodoPago(MetodosPagoDTO.fromString(pago.getMetodoPago().name())); // Conversión aquí
        dto.setTipoDescuento(pago.getTipoDescuento());
        dto.setMontoDescuento(pago.getMontoDescuento());
        dto.setUsuario(new UsuarioDTO(pago.getCajero().getId(), pago.getCajero().getNombre(),
                pago.getCajero().getCorreo(), RolDTO.valueOf(pago.getCajero().getRol().name())));
        dto.setCuotasPagadas(pago.getDetalles().stream()
                .map(detalle -> new DetallePagoDTO(detalle.getCuota().getConcepto().name(), detalle.getMontoPagado()))
                .toList());
        return dto;
    }
}
