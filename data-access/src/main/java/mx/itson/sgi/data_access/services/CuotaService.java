package mx.itson.sgi.data_access.services;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.CuotaMensual;
import mx.itson.sgi.data_access.repositories.CuotaMensualRepository;
import mx.itson.sgi.data_access.repositories.CuotaRepository;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.CuotaConsultadaDTO;
import mx.itson.sgi.dto.DetalleAdeudoDTO;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository repository;
    @Autowired
    private CuotaMensualRepository mensualRepository;

    public Cuota obtenerCuotaPorAlumnoConceptoCiclo(Alumno matriculaAlumno, Concepto concepto, CicloEscolar ciclo){
        Optional<Cuota> optional = repository.findByAlumnoAndConceptoAndCiclo(matriculaAlumno, concepto, ciclo);
        if(optional.isPresent()){
            Cuota cuota = optional.get();
            System.out.println(cuota);
            return cuota;
        }
        return null;
    }

    public List<CuotaConsultadaDTO> obtenerCuotasPorAlumno(String matriculaAlumno, String cicloEscolar){
        List<Object[]> cuotas = repository.findCuotasPorAlumno(matriculaAlumno, cicloEscolar);
        List<CuotaConsultadaDTO> dto = new ArrayList<>();
        if(cuotas != null){
            dto = cuotas.stream().map(cuota -> new CuotaConsultadaDTO(
                (Long)cuota[0],
                (String)cuota[1],
                (String)cuota[2],
                (String)cuota[3],
                (Double)cuota[4],
                (Double)cuota[5]
            )).collect(Collectors.toList());
            
            return dto;
        }
        return null;
    }
    public List<Cuota> obtenerCuotasPorConcepto(Concepto concepto){
        List<Cuota> cuotas = repository.findByConcepto(concepto);
        if(cuotas != null){
            return cuotas;
        }
        return new ArrayList<Cuota>();
    }

    public void agregarCuota(Cuota cuota){
        Optional<Cuota> optional = repository.findByAlumnoAndConceptoAndCiclo(
            cuota.getAlumno(),cuota.getConcepto(),cuota.getCiclo());
        optional.ifPresentOrElse(c->{
            System.out.println(c);
            c.setMontoBase(cuota.getMontoBase());
            repository.save(c);
        },()->repository.save(cuota));
    }
    public List<DetalleAdeudoDTO> obtenerDetalleAdeudosColegiatura(String matricula, String idCiclo){
        System.out.println("entre al servicio");
        List<Object[]> detalles = repository.findDetallesAdeudoColegiatura(matricula, idCiclo);
        List<DetalleAdeudoDTO> dtos = new ArrayList<>();
        if(detalles != null){
            detalles.stream().forEach(a->{
                Month mes = Month.valueOf(((String)a[0]).toUpperCase());
                dtos.add(new DetalleAdeudoDTO(
                    mes,
                    (Double)a[1],
                    (Double)a[2]
                ));
            });
            return dtos;
        }
        return new ArrayList<DetalleAdeudoDTO>();
    }
    public List<DetalleAdeudoDTO> obtenerPagosMensualesAlumno(String matricula, String idCiclo){
        List<Object[]> detalles = repository.findDetallesAdeudoColegiatura(matricula, idCiclo);
        List<DetalleAdeudoDTO> dtos = new ArrayList<>();
        if(detalles != null){
            detalles.stream().forEach(a->{
                // LocalDate fecha = LocalDate.parse((String)a[0]);
                dtos.add(new DetalleAdeudoDTO(
                    (String)a[0],
                    (Double)a[1],
                    (Double)a[2]
                ));
            });
            return dtos;
        }
        return new ArrayList<DetalleAdeudoDTO>();
    }

    public Double obtenerMontoBaseColegiatura(AlumnoConsultaDTO alumno, CicloEscolarDTO ciclo){
        Cuota cuota = repository.findMontoBaseColegiaturaAlumno(new Alumno(alumno.getMatricula()), new CicloEscolar(ciclo.getId()));
        return cuota.getMontoBase();
    }
    public double obtenerMontoTotalColegiaturas(String matricula, String ciclo ){
        Double total = 0.0;
        repository.sp_monto_total_colegiaturas(matricula, ciclo, total);
        return total;
    }

    protected void cargarCuotas(CicloEscolar ciclo2324,CicloEscolar ciclo2425, Alumno alumno1, Alumno alumno2, Alumno alumno3) {
        Map<Long, Cuota> cuotasMap = new HashMap<>();
        
        // Cuotas del ciclo 23-24
        // ALUMNO 1
        cuotasMap.put(1L, crearCuota(2300.00, ciclo2324, alumno1, Concepto.INSCRIPCION));
        cuotasMap.put(3L, crearCuota(2000.00, ciclo2324, alumno1, Concepto.UNIFORMES));
        crearCuotasMensualesCiclo2324(alumno1, ciclo2324, 1600.00);//2L
        cuotasMap.put(4L, crearCuota(350.00, ciclo2324, alumno1, Concepto.LIBROS));
        
        // ALUMNO 2
        cuotasMap.put(5L, crearCuota(350.00, ciclo2324, alumno2, Concepto.LIBROS));
        cuotasMap.put(6L, crearCuota(2000.00, ciclo2324, alumno2, Concepto.UNIFORMES));
        crearCuotasMensualesCiclo2324(alumno2, ciclo2324, 1200.00);//7L
        
        // ALUMNO 3
        cuotasMap.put(8L, crearCuota(350.00, ciclo2324, alumno3, Concepto.LIBROS));
        crearCuotasMensualesCiclo2324(alumno3, ciclo2324, 1300.00);//9L
        cuotasMap.put(10L, crearCuota(800.00, ciclo2324, alumno3, Concepto.EVENTOS));
        cuotasMap.put(11L, crearCuota(2300.00, ciclo2324, alumno3, Concepto.INSCRIPCION));

        // Cuotas del ciclo 24-25
        // ALUMNO 1
        cuotasMap.put(12L, crearCuota(2500.00, ciclo2425, alumno1, Concepto.INSCRIPCION));
        crearCuotasMensualesCiclo2425(alumno1, ciclo2425, 1800.00);//13L
        cuotasMap.put(14L, crearCuota(2000.00, ciclo2425, alumno1, Concepto.UNIFORMES));
        cuotasMap.put(15L, crearCuota(400.00, ciclo2425, alumno1, Concepto.LIBROS));
        
        // ALUMNO 2
        cuotasMap.put(16L, crearCuota(400.00, ciclo2425, alumno2, Concepto.LIBROS));
        cuotasMap.put(17L, crearCuota(2500.00, ciclo2425, alumno2, Concepto.INSCRIPCION));
        cuotasMap.put(18L, crearCuota(2000.00, ciclo2425, alumno2, Concepto.UNIFORMES));
        crearCuotasMensualesCiclo2425(alumno2, ciclo2425, 1350.00);//19L
        
        // ALUMNO 3
        cuotasMap.put(20L, crearCuota(400.00, ciclo2425, alumno3, Concepto.LIBROS));
        cuotasMap.put(22L, crearCuota(2500.00, ciclo2425, alumno3, Concepto.INSCRIPCION));
        crearCuotasMensualesCiclo2425(alumno3, ciclo2425, 1500.00);//21L

        System.out.println("Cuotas creadas: " + cuotasMap);
    }

    @Transactional
    private Cuota crearCuota(Double montoBase, CicloEscolar ciclo, Alumno alumno, Concepto concepto) {
        Cuota cuota = new Cuota();
        cuota.setMontoBase(montoBase);
        cuota.setCiclo(ciclo);
        cuota.setAlumno(alumno);
        cuota.setConcepto(concepto);
        return repository.save(cuota);
    }

    @Transactional
    private void crearCuotasMensualesCiclo2324(Alumno alumno, CicloEscolar ciclo, Double montoBase) {
        // Crear 10 cuotas mensuales de agosto 2023 a junio 2024
        LocalDate fechaInicio = ciclo.getFechaInicio();
        
        for (int i = 0; i < 10; i++) {
            LocalDate mesCuota = fechaInicio.plusMonths(i);
            
            CuotaMensual cuotaMensual = new CuotaMensual();
            cuotaMensual.setMontoBase(montoBase);
            cuotaMensual.setCiclo(ciclo);
            cuotaMensual.setAlumno(alumno);
            cuotaMensual.setConcepto(Concepto.COLEGIATURA);
            cuotaMensual.setMes(mesCuota);
            cuotaMensual.setMontoPagado(0.0); // Se actualizará con los pagos
            cuotaMensual.setMontoAdeudo(montoBase); // Inicialmente todo es adeudo
            
            mensualRepository.save(cuotaMensual);
        }
    }

    @Transactional
    private void crearCuotasMensualesCiclo2425(Alumno alumno, CicloEscolar ciclo, Double montoBase) {
        // Crear 9 cuotas mensuales de agosto 2024 a abril 2025 (hasta la fecha actual)
        LocalDate fechaInicio = ciclo.getFechaInicio();
        
        for (int i = 0; i < 9; i++) {
            LocalDate mesCuota = fechaInicio.plusMonths(i);
            
            CuotaMensual cuotaMensual = new CuotaMensual();
            cuotaMensual.setMontoBase(montoBase);
            cuotaMensual.setCiclo(ciclo);
            cuotaMensual.setAlumno(alumno);
            cuotaMensual.setConcepto(Concepto.COLEGIATURA);
            cuotaMensual.setMes(mesCuota);
            cuotaMensual.setMontoPagado(0.0); // Se actualizará con los pagos
            cuotaMensual.setMontoAdeudo(montoBase); // Inicialmente todo es adeudo
            
            mensualRepository.save(cuotaMensual);
        }
    }

}

