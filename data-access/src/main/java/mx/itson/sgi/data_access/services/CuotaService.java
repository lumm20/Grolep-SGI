package mx.itson.sgi.data_access.services;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.dto.AdeudoDTO;
import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.repositories.CicloRepository;
import mx.itson.sgi.data_access.repositories.CuotaRepository;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.CuotaConsultadaDTO;
import mx.itson.sgi.dto.CuotasDTO;
import mx.itson.sgi.dto.DetalleAdeudoDTO;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository repository;
    @Autowired
	CicloRepository cicloRepository;

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

    // public List<AdeudoDTO> obtenerCuotasConAdeudos(){
    //     List<Object[]> adeudos = repository.findCuotasConAdeudo();
        
    //     if(adeudos != null){
    //         List<AdeudoDTO> dtos = new ArrayList<>();
    //         adeudos.stream().forEach(a->{
    //             dtos.add(new AdeudoDTO(
    //                 (Double)a[0],
    //                 (Long)a[1],
    //                 (String)a[2],
    //                 (String)a[3],
    //                 (String)a[4],
    //                 (Double)a[5]
    //             ));
    //         });
    //         return dtos;
    //     }
    //     return new ArrayList<AdeudoDTO>();
    // }

    // public List<AdeudoDTO> obtenerCuotasConAdeudosPorAlumno(String matriculaAlumno, String ciclo){
    //     List<Object[]> adeudos = repository.findCuotasConAdeudoPorAlumno(matriculaAlumno, ciclo);
    //     List<AdeudoDTO> dtos = new ArrayList<>();
    //     if(adeudos != null){
    //         adeudos.stream().forEach(a->{
    //             dtos.add(new AdeudoDTO(
    //                 (Double)a[0],
    //                 (Long)a[1],
    //                 (String)a[2],
    //                 (String)a[3],
    //                 (String)a[4],
    //                 (Double)a[5]
    //             ));
    //         });
    //         return dtos;
    //     }
    //     return new ArrayList<AdeudoDTO>();
    // }

    public void agregarCuota(Cuota cuota){
        Optional<Cuota> optional = repository.findByAlumnoAndConceptoAndCiclo(
            cuota.getAlumno(),cuota.getConcepto(),cuota.getCiclo());
        optional.ifPresentOrElse(c->{
            System.out.println(c);
            c.setMontoBase(cuota.getMontoBase());
            repository.save(c);
        },()->repository.save(cuota));
    }

    public CicloEscolar obtenerCicloEscolarPorId(String id){
        Optional<CicloEscolar> optional = cicloRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
    public CicloEscolar obtenerCicloEscolarPorFechas(CicloEscolar ciclo){
        Optional<CicloEscolar> optional = cicloRepository.findByFechaInicioAndFechaFin(ciclo.getFechaInicio(),ciclo.getFechaFin());
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
    public CicloEscolarDTO obtenerCicloActual(){
        Optional<CicloEscolar> optional = cicloRepository.findCicloActual();
        if(optional.isPresent()){
            CicloEscolar ciclo = optional.get();
            CicloEscolarDTO dto = new CicloEscolarDTO(ciclo.getFechaInicio().toString(),ciclo.getFechaFin().toString());
            dto.setId(ciclo.getId());
            return dto;
        }
        return null;
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

    public Double obtenerMontoBaseColegiatura(AlumnoConsultaDTO alumno){
        Cuota cuota = repository.findMontoBaseColegiaturaAlumno(new Alumno(alumno.getMatricula()));
        return cuota.getMontoBase();
    }
}

