package mx.itson.sgi.data_access.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.repositories.AlumnoRepository;

@Service
public class AlumnoController {

    @Autowired
    AlumnoRepository repository;

    public Alumno registrarAlumno(Alumno alumno){
        return repository.save(alumno);
    }

    public Alumno buscarAlumnoPorMatricula(String matricula){
        Optional<Alumno> optional = repository.findById(matricula);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public List<Alumno> buscarAlumnoPorPadre(String telefonoPadre){
        List<Alumno> list = repository.findByTelefonoPadre(telefonoPadre);

        if(list != null) return list;
        return new ArrayList<Alumno>();
    }
}
