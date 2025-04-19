package mx.itson.sgi.data_access;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.Beca;
import mx.itson.sgi.data_access.entities.TipoBeca;
import mx.itson.sgi.data_access.repositories.AlumnoRepository;

@Service
public class StudentDataInitializer {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Transactional
    protected List<Alumno> cargarAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        Alumno alumno1 = new Alumno();
        alumno1.setMatricula("A20220001");
        alumno1.setNombre("Carlos Rodríguez Vega");
        alumno1.setBeca(new Beca(TipoBeca.NINGUNA,0.0));
        alumno1.setTelefonoPadre("5551234567");
        alumnos.add(alumnoRepository.save(alumno1));
        
        Alumno alumno2 = new Alumno();
        alumno2.setMatricula("A20220002");
        alumno2.setNombre("Ana Martínez Soto");
        alumno2.setBeca(new Beca(TipoBeca.SEC,30.0));
        alumno2.setTelefonoPadre("5559876543");
        alumnos.add(alumnoRepository.save(alumno2));
        
        Alumno alumno3 = new Alumno();
        alumno3.setMatricula("A20220003");
        alumno3.setNombre("Miguel López Jiménez");
        alumno3.setBeca(new Beca(TipoBeca.DEPORTIVA,40.0));
        alumno3.setTelefonoPadre("5552468013");
        alumnos.add(alumnoRepository.save(alumno3));

        return alumnos;
    }
    
    @Transactional(readOnly = true)
    private Alumno buscarAlumno(String matricula){
        Optional<Alumno> alumno = alumnoRepository.findById(matricula);
        if(alumno.isPresent()){
            return alumno.get();
        }
        return null;
    }
}
