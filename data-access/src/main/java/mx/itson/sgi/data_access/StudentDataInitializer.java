package mx.itson.sgi.data_access;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mx.itson.sgi.dto.enums.Estatus;
import mx.itson.sgi.dto.enums.Genero;
import mx.itson.sgi.dto.enums.Nivel;
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

        Alumno alumno1 = Alumno.builder()
                .matricula("A20220001")
                .nombre("Carlos Rodríguez Vega")
                .promedio(8.7)
                .grado(3)
                .grupo("B")
                .estatus(Estatus.Baja)
                .nivel(Nivel.Primaria)
                .fechaNacimiento(LocalDate.of(2008, 5, 20))
                .telefono("5551112233")
                .genero(Genero.Masculino)
                .correo("carlos.rodriguez@example.com")
                .tutor("Luis Rodríguez")
                .telefonoPadre("5551234567")
                .beca(new Beca(TipoBeca.NINGUNA, 0.0))
                .cuotas(new ArrayList<>())      // evita null
                .compras(new ArrayList<>())     // evita null
                .build();
        alumnos.add(alumnoRepository.save(alumno1));

        Alumno alumno2 = Alumno.builder()
                .matricula("A20220002")
                .nombre("Jesus Vega Dominguez")
                .promedio(8.7)
                .grado(3)
                .grupo("B")
                .estatus(Estatus.Activo)
                .nivel(Nivel.Primaria)
                .fechaNacimiento(LocalDate.of(2008, 5, 20))
                .telefono("5551112233")
                .genero(Genero.Masculino)
                .correo("carlos.rodriguez@example.com")
                .tutor("Luis Rodríguez")
                .telefonoPadre("5551234567")
                .beca(new Beca(TipoBeca.DEPORTIVA, 0.0))
                .cuotas(new ArrayList<>())      // evita null
                .compras(new ArrayList<>())     // evita null
                .build();
        alumnos.add(alumnoRepository.save(alumno2));

        Alumno alumno3 = Alumno.builder()
                .matricula("A20220001")
                .nombre("Maria Martinez Chavez")
                .promedio(8.7)
                .grado(3)
                .grupo("B")
                .estatus(Estatus.Egresado)
                .nivel(Nivel.Preescolar)
                .fechaNacimiento(LocalDate.of(2008, 5, 20))
                .telefono("5551112233")
                .genero(Genero.Masculino)
                .correo("carlos.rodriguez@example.com")
                .tutor("Luis Rodríguez")
                .telefonoPadre("5551234567")
                .beca(new Beca(TipoBeca.NINGUNA, 0.0))
                .cuotas(new ArrayList<>())      // evita null
                .compras(new ArrayList<>())     // evita null
                .build();
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
