package mx.itson.sgi.data_access.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.entities.Alumno;
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

}
