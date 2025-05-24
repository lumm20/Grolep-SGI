package mx.itson.sgi.data_access.services;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.CuotaMensual;
import mx.itson.sgi.data_access.entities.DetallePago;
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

    public CuotaMensual obtenerColegiaturaMesActual(Alumno alumno, CicloEscolar ciclo){
        LocalDate hoy = LocalDate.now();
        LocalDate fecha = LocalDate.of(hoy.getYear(), hoy.getMonth(), 1);
        Optional<CuotaMensual> op = mensualRepository.findByAlumnoAndCicloAndMes(alumno, ciclo, fecha);
        if(op.isPresent()){
            return op.get();
        }
        return null;
    }

    public Cuota obtenerCuotaPorAlumnoConceptoCiclo(Alumno matriculaAlumno, Concepto concepto, CicloEscolar ciclo){
        Optional<Cuota> optional = repository.findByAlumnoAndConceptoAndCiclo(matriculaAlumno, concepto, ciclo);
        if(optional.isPresent()){
            Cuota cuota = optional.get();
            System.out.println(cuota);
            return cuota;
        }
        return null;
    }
    public CuotaMensual obtenerCuotaPorAlumnoCicloMes(Alumno matriculaAlumno, CicloEscolar ciclo, LocalDate fecha){
        Optional<CuotaMensual> optional = mensualRepository.findByAlumnoAndCicloAndMes(matriculaAlumno, ciclo, fecha);
        if(optional.isPresent()){
            CuotaMensual cuota = optional.get();
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
                (Double)cuota[4] != null ? (Double)cuota[4] : 0.0,
                (Double)cuota[5] != null ? (Double)cuota[5] : 0.0
            )).collect(Collectors.toList());
            System.out.println("dto obtenido"+dto);
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

    public List<CuotaMensual> obtenerColegiaturasConAdeudo(String matricula, String idCiclo){
        Optional<List<CuotaMensual>> op = mensualRepository.findByAlumnoAndCicloAndAdeudoGreaterThanOrderByMesAsc(new Alumno(matricula),
         new CicloEscolar(idCiclo), 0.0);
        if(op.isPresent()){
            return op.get();
        }
        return null;
    }

    @Transactional
    protected void actualizarAdeudoCuotasMensuales(List<DetallePago> detallePagos){
        CuotaMensual cuota;
        Double montoPagado;
        for (DetallePago detallePago : detallePagos) {
            cuota = (CuotaMensual)detallePago.getCuota();
            // montoPagado = cuota.getMontoPagado();
            // cuota.setMontoPagado(montoPagado);
            mensualRepository.save(cuota);
        }
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

    //nuevo
    public List<DetalleAdeudoDTO> obtenerDetallesAdeudos(String matricula, String idCiclo){
        List<Object[]> detalles = mensualRepository.buscarDetalles(new Alumno(matricula), new CicloEscolar(idCiclo));
        List<DetalleAdeudoDTO> dtos = new ArrayList<>();
        if(detalles != null){
            detalles.stream().forEach(a->{
                dtos.add(new DetalleAdeudoDTO(
                    (LocalDate)a[0],
                    (Double)a[1],
                    (Double)a[2],
                    (Double)a[3]
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

    
}

