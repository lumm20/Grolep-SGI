package mx.itson.sgi.data_access.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.DetallePago;
import mx.itson.sgi.data_access.entities.MetodoPago;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.PagoRepository;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.DetallePagoDTO;
import mx.itson.sgi.dto.MetodosPagoDTO;
import mx.itson.sgi.dto.PagoDTO;

@Service
public class PagoService {
    @Autowired
    private PagoRepository repository;
    @Autowired
    private CuotaService cuotaService;

    public Pago obtenerPagoPorFolio(String folio){
        Optional<Pago> pago = repository.findById(folio);
        if(pago.isPresent()){
            return pago.get();
        }
        return null;
    }

    public List<Pago> obtenerPagosPorEstudiante(String matriculaEstudiante){
        List<Pago> pago = repository.findPagosPorEstudiante(new Alumno(matriculaEstudiante));

        if(pago!= null && !pago.isEmpty()){
            return pago;
        }
        return new ArrayList<Pago>();
    }

    public List<Pago> obtenerPagosPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        List<Pago> pago = repository.findByFechaBetween(fechaFin, fechaFin);

        if(pago!= null && !pago.isEmpty()){
            return pago;
        }
        return new ArrayList<Pago>();
    }

    public void registrarPago(PagoDTO pagoDTO){
        MetodoPago metodo;
        if(pagoDTO.getMetodoPago().equals(MetodosPagoDTO.Efectivo)){
            metodo = MetodoPago.EFECTIVO;
        }else if(pagoDTO.getMetodoPago().equals(MetodosPagoDTO.Tarjeta)){
            metodo = MetodoPago.TARJETA;
        }else{
            metodo = MetodoPago.TRANSFERENCIA;
        }
        Usuario cajero = new Usuario(pagoDTO.getIdUsuario());
        Alumno alumno = new Alumno(pagoDTO.getAlumno().getMatricula());
        Pago pago = new Pago(pagoDTO.getFolio(), pagoDTO.getFecha(), pagoDTO.getMontoTotal(),
        cajero, metodo,alumno,pagoDTO.getMontoDescuento(),pagoDTO.getTipoDescuento());
        CicloEscolar ciclo = new CicloEscolar(pagoDTO.getIdCicloEscolar());
        List<DetallePagoDTO> detallesDTO = pagoDTO.getCuotasPagadas();
        List<DetallePago> detalles = convertirDetallesPagos(detallesDTO, alumno,ciclo, pago);
        pago.setDetalles(detalles);
        repository.save(pago);
        
    }
    
    private List<DetallePago> convertirDetallesPagos(List<DetallePagoDTO> dtos,Alumno alumno, CicloEscolar ciclo, Pago pago){
        List<DetallePago> detalles = new ArrayList<>();
        Cuota cuota;
        Concepto concepto;
        for (DetallePagoDTO dto : dtos) {
            concepto = Concepto.valueOf(dto.getConceptoCuota());
            cuota = cuotaService.obtenerCuotaPorAlumnoConceptoCiclo(alumno, concepto, ciclo);
            System.out.println("-------------------");
            System.out.println(cuota);
            System.out.println("-------------------");
            detalles.add(new DetallePago(cuota, dto.getMontoPagado(),pago));
        }
        return detalles;
    }

    public long getCantidadPagos(){
        return repository.count();
    }
    public long getCantidadPagosMensualesAlumno(AlumnoConsultaDTO alumno, String ciclo){
        return repository.countPagosMensuales(new Alumno(alumno.getMatricula()), new CicloEscolar(ciclo));
    }

    public Double obtenerTotalPagadoColegiatura(AlumnoConsultaDTO alumno, String ciclo){
        return repository.findTotalPagadoColegiatura(new Alumno(alumno.getMatricula()), new CicloEscolar(ciclo));
    }

    @Transactional
    protected void cargarPagosCiclo2425(List<Pago> pagosAlumno1, List<Pago> pagosAlumno2, List<Pago> pagosAlumno3) {        
        // Pagos de Alumno 1
        pagosAlumno1.forEach(pago -> repository.save(pago));
        // Pagos de Alumno 2
        pagosAlumno2.forEach(pago -> repository.save(pago));
        // Pagos de Alumno 3
        pagosAlumno3.forEach(pago -> repository.save(pago));
    }

    @Transactional
    protected void cargarPagosCiclo2324(List<Pago> pagosAlumno1, List<Pago> pagosAlumno2, List<Pago> pagosAlumno3) {        
        // Pagos de Alumno 1
        pagosAlumno1.forEach(pago -> repository.save(pago));
        // Pagos de Alumno 2
        pagosAlumno2.forEach(pago -> repository.save(pago));
        // Pagos de Alumno 3
        pagosAlumno3.forEach(pago -> repository.save(pago));
        // Pago pago1 = new Pago("P202300004", LocalDateTime.of(2023, 8, 15, 9, 15, 30),
        //  2300.0, 0.0, "No aplica", cajero, alumno1,
        //   List.of(new DetallePago(new Cuota(1L), 2300.0)), MetodoPago.EFECTIVO);
        
        //   Pago pago2 = new Pago("P202300006", LocalDateTime.of(2023, 8, 20, 10, 0, 0),
        //  1600.0, 0.0, "No aplica", cajero, alumno1,
        //   List.of(new DetallePago(new Cuota(2L), 1600.0)), MetodoPago.EFECTIVO);

        // Pago pago3 = new Pago("P202300003", LocalDateTime.of(2023, 8, 13, 10, 15, 0),
        //  2350.0, 0.0, "No aplica", cajero, alumno1,
        //   List.of(new DetallePago(new Cuota(3L), 2000.0),new DetallePago(new Cuota(4L), 350.0)), MetodoPago.EFECTIVO);

        // Pago pago4 = new Pago("P202300008", LocalDateTime.of(2023, 9, 10, 9, 50, 0), 1600.00,
        //  0.0, "No aplica", cajero, alumno1,
        //   List.of(new DetallePago(new CuotaMensual(1L), 2300.0)), MetodoPago.TRANSFERENCIA);

        // Pago pago5 = new Pago("P202300004", LocalDateTime.of(2023, 8, 15, 9, 15, 30),
        //  2300.0, 0.0, "No aplica", cajero, alumno1,
        //   List.of(new DetallePago(new Cuota(1L), 2300.0)), MetodoPago.TARJETA);
        // Pago pago6 = new Pago("P202300004", LocalDateTime.of(2023, 8, 15, 9, 15, 30),
        //  2300.0, 0.0, "No aplica", cajero, alumno1,
        //   List.of(new DetallePago(new Cuota(1L), 2300.0)), MetodoPago.TARJETA);
        // Pago pago7 = new Pago("P202300004", LocalDateTime.of(2023, 8, 15, 9, 15, 30),
        //  2300.0, 0.0, "No aplica", cajero, alumno1,
        //   List.of(new DetallePago(new Cuota(1L), 2300.0)), MetodoPago.TARJETA);
        // Pago pago8 = new Pago("P202300004", LocalDateTime.of(2023, 8, 15, 9, 15, 30),
        //  2300.0, 0.0, "No aplica", cajero, alumno1,
        //   List.of(new DetallePago(new Cuota(1L), 2300.0)), MetodoPago.TARJETA);
        // Pago pago9 = new Pago("P202300004", LocalDateTime.of(2023, 8, 15, 9, 15, 30),
        //  2300.0, 0.0, "No aplica", cajero, alumno1,
        //   List.of(new DetallePago(new Cuota(1L), 2300.0)), MetodoPago.TARJETA);
    
        
        // registrarPago("P202300008", LocalDateTime.of(2023, 9, 10, 9, 50, 0), 1600.00, cajero, alumno1, "TRANSFERENCIA", 
        //         List.of(new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.SEPTEMBER), 1600.00)));
        
        // registrarPago("P202300011", LocalDateTime.of(2023, 11, 15, 8, 30, 0), 3200.00, cajero, alumno1, "EFECTIVO", 
        //         List.of(
        //             new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.OCTOBER), 1600.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.NOVEMBER), 1600.00)
        //         ));
        
        // registrarPago("P202300013", LocalDateTime.of(2023, 12, 5, 12, 0, 0), 1600.00, cajero, alumno1, "EFECTIVO", 
        //         List.of(new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.DECEMBER), 1600.00)));
        
        // registrarPago("P202400002", LocalDateTime.of(2024, 2, 10, 10, 10, 0), 3200.00, cajero, alumno1, "EFECTIVO", 
        //         List.of(
        //             new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.JANUARY), 1600.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.FEBRUARY), 1600.00)
        //         ));
        
        // registrarPago("P202400003", LocalDateTime.of(2024, 3, 8, 11, 20, 0), 1600.00, cajero, alumno1, "TRANSFERENCIA", 
        //         List.of(new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.MARCH), 1600.00)));
        
        // registrarPago("P202400007", LocalDateTime.of(2024, 5, 13, 8, 45, 0), 3200.00, cajero, alumno1, "EFECTIVO", 
        //         List.of(
        //             new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.APRIL), 1600.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.MAY), 1600.00)
        //         ));
        
        // registrarPago("P202400010", LocalDateTime.of(2024, 6, 5, 10, 30, 0), 1600.00, cajero, alumno1, "EFECTIVO", 
        //         List.of(new DetalleItem(obtenerCuotaMensualId(alumno1, ciclo, Month.JUNE), 1600.00)));
        
        // // Pagos de Alumno 2
        // registrarPago("P202300002", LocalDateTime.of(2023, 8, 10, 11, 0, 0), 2350.00, cajero, alumno2, "EFECTIVO", 
        //         List.of(new DetalleItem(6L, 2000.00), new DetalleItem(5L, 350.00)));
        
        // registrarPago("P202300007", LocalDateTime.of(2023, 8, 20, 11, 0, 0), 1200.00, cajero, alumno2, "EFECTIVO", 
        //         List.of(new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.AUGUST), 1200.00)));
        
        // registrarPago("P202300010", LocalDateTime.of(2023, 11, 5, 9, 20, 0), 3600.00, cajero, alumno2, "EFECTIVO", 
        //         List.of(
        //             new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.SEPTEMBER), 1200.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.OCTOBER), 1200.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.NOVEMBER), 1200.00)
        //         ));
        
        // registrarPago("P202400001", LocalDateTime.of(2024, 1, 15, 10, 40, 0), 2400.00, cajero, alumno2, "EFECTIVO", 
        //         List.of(
        //             new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.DECEMBER), 1200.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.JANUARY), 1200.00)
        //         ));
        
        // registrarPago("P202400004", LocalDateTime.of(2024, 3, 15, 9, 0, 0), 2400.00, cajero, alumno2, "TRANSFERENCIA", 
        //         List.of(
        //             new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.FEBRUARY), 1200.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.MARCH), 1200.00)
        //         ));
        
        // registrarPago("P202400006", LocalDateTime.of(2024, 4, 15, 11, 0, 0), 1200.00, cajero, alumno2, "EFECTIVO", 
        //         List.of(new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.APRIL), 1200.00)));
        
        // registrarPago("P202400011", LocalDateTime.of(2024, 5, 20, 10, 30, 0), 1200.00, cajero, alumno2, "TRANSFERENCIA", 
        //         List.of(new DetalleItem(obtenerCuotaMensualId(alumno2, ciclo, Month.MAY), 1200.00)));
        
        // // Pagos de Alumno 3
        // registrarPago("P202300001", LocalDateTime.of(2023, 8, 5, 10, 0, 0), 3450.00, cajero, alumno3, "EFECTIVO", 
        //         List.of(
        //             new DetalleItem(11L, 2300.00),
        //             new DetalleItem(10L, 800.00),
        //             new DetalleItem(8L, 350.00)
        //         ));
        
        // registrarPago("P202300005", LocalDateTime.of(2023, 8, 18, 9, 30, 0), 1300.00, cajero, alumno3, "EFECTIVO", 
        //         List.of(new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.AUGUST), 1300.00)));
        
        // registrarPago("P202300009", LocalDateTime.of(2023, 9, 18, 9, 30, 0), 1300.00, cajero, alumno3, "TRANSFERENCIA", 
        //         List.of(new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.SEPTEMBER), 1300.00)));
        
        // registrarPago("P202300012", LocalDateTime.of(2023, 12, 1, 11, 0, 0), 3900.00, cajero, alumno3, "EFECTIVO", 
        //         List.of(
        //             new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.OCTOBER), 1300.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.NOVEMBER), 1300.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.DECEMBER), 1300.00)
        //         ));
        
        // registrarPago("P202400005", LocalDateTime.of(2024, 4, 10, 9, 15, 0), 5200.00, cajero, alumno3, "EFECTIVO", 
        //         List.of(
        //             new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.JANUARY), 1300.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.FEBRUARY), 1300.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.MARCH), 1300.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.APRIL), 1300.00)
        //         ));
        
        // registrarPago("P202400009", LocalDateTime.of(2024, 6, 2, 10, 20, 0), 2600.00, cajero, alumno3, "TRANSFERENCIA", 
        //         List.of(
        //             new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.MAY), 1300.00),
        //             new DetalleItem(obtenerCuotaMensualId(alumno3, ciclo, Month.JUNE), 1300.00)
        //         ));
    }
}
