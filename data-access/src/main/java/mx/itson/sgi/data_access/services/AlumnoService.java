package mx.itson.sgi.data_access.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.Beca;
import mx.itson.sgi.data_access.entities.TipoBeca;
import mx.itson.sgi.data_access.repositories.AlumnoRepository;
import mx.itson.sgi.dto.AlumnoConsultaDTO;

@Service
public class AlumnoService {

    @Autowired
    AlumnoRepository repository;

    public Alumno registrarAlumno(AlumnoConsultaDTO alumno){

        Alumno alumnoEntity = new Alumno(alumno.getMatricula(), alumno.getNombre(), null, alumno.getNumeroCelular());
        return repository.save(alumnoEntity);
    }

    public AlumnoConsultaDTO buscarAlumnoPorMatricula(String matricula){
        Optional<Alumno> optional = repository.findById(matricula);
        if(optional.isPresent()){
            Alumno alumno = optional.get();
            AlumnoConsultaDTO dto = new AlumnoConsultaDTO();
            dto.setMatricula(alumno.getMatricula());
            dto.setNombre(alumno.getNombre());
            dto.setNumeroCelular(alumno.getTelefonoPadre());
            return dto;
        }
        return null;
    }

    public List<AlumnoConsultaDTO> buscarAlumnosPorNombre(String nombre){
        List<Alumno> alumnos = repository.encontrarAlumnosConNombre(nombre);
        List<AlumnoConsultaDTO> dtos = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            AlumnoConsultaDTO dto = new AlumnoConsultaDTO();
            dto.setMatricula(alumno.getMatricula());
            dto.setNombre(alumno.getNombre());
            dto.setNumeroCelular(alumno.getTelefonoPadre());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<Alumno> buscarAlumnoPorPadre(String telefonoPadre){
        List<Alumno> list = repository.findByTelefonoPadre(telefonoPadre);

        if(list != null) return list;
        return new ArrayList<Alumno>();
    }

    @Transactional
    protected void cargarAlumnos() {
        Alumno alumno1 = new Alumno();
        alumno1.setMatricula("A20220001");
        alumno1.setNombre("Carlos Rodríguez Vega");
        alumno1.setBeca(new Beca(TipoBeca.NINGUNA,0.0));
        alumno1.setTelefonoPadre("5551234567");
        repository.save(alumno1);

        Alumno alumno2 = new Alumno();
        alumno2.setMatricula("A20220002");
        alumno2.setNombre("Ana Martínez Soto");
        alumno2.setBeca(new Beca(TipoBeca.SEC,30.0));
        alumno2.setTelefonoPadre("5559876543");
        repository.save(alumno2);

        Alumno alumno3 = new Alumno();
        alumno3.setMatricula("A20220003");
        alumno3.setNombre("Miguel López Jiménez");
        alumno3.setBeca(new Beca(TipoBeca.DEPORTIVA,40.0));
        alumno3.setTelefonoPadre("5552468013");
        repository.save(alumno3);
    }

}
