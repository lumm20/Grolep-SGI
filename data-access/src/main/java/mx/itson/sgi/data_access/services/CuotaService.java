package mx.itson.sgi.data_access.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.dto.AdeudoDTO;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.repositories.CicloRepository;
import mx.itson.sgi.data_access.repositories.CuotaRepository;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository repository;
    @Autowired
	CicloRepository cicloRepository;

    public List<Cuota> obtenerCuotasPorAlumno(String matriculaAlumno){
        List<Cuota> cuotas = repository.findByAlumno_Matricula(matriculaAlumno);
        if(cuotas != null){
            return cuotas;
        }
        return new ArrayList<Cuota>();
    }
    public List<Cuota> obtenerCuotasPorConcepto(Concepto concepto){
        List<Cuota> cuotas = repository.findByConcepto(concepto);
        if(cuotas != null){
            return cuotas;
        }
        return new ArrayList<Cuota>();
    }

    public List<AdeudoDTO> obtenerCuotasConAdeudos(){
        List<AdeudoDTO> adeudos = repository.findCuotasConAdeudo();
        if(adeudos != null) return adeudos;
        return new ArrayList<AdeudoDTO>();
    }

    public List<AdeudoDTO> obtenerCuotasConAdeudosPorAlumno(String matriculaAlumno, String ciclo){
        List<AdeudoDTO> adeudos = repository.findCuotasConAdeudoPorAlumno(matriculaAlumno, ciclo);
        if(adeudos != null) return adeudos;
        return new ArrayList<AdeudoDTO>();
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
}
