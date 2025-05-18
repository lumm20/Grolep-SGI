package mx.itson.sgi.data_access.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import mx.itson.sgi.data_access.entities.*;
import mx.itson.sgi.data_access.repositories.CuotaRepository;
import mx.itson.sgi.data_access.repositories.DetalleCicloRepository;
import mx.itson.sgi.dto.CicloEscolarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.repositories.AlumnoRepository;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlumnoService {

    @Autowired
    AlumnoRepository repository;

    @Autowired
    DetalleCicloRepository detalleCicloRepository;

    @Autowired
    CuotaRepository cuotaRepository;

    @Autowired
    CicloEscolarService cicloEscolarService;

    public Alumno registrarAlumno(AlumnoConsultaDTO alumno) {

        Alumno alumnoEntity = new Alumno(alumno.getMatricula(), alumno.getNombre(), null, alumno.getNumeroCelular());
        return repository.save(alumnoEntity);
    }

    public AlumnoConsultaDTO buscarAlumnoPorMatricula(String matricula) {
        Optional<Alumno> optional = repository.findById(matricula);
        if (optional.isPresent()) {
            Alumno alumno = optional.get();
            AlumnoConsultaDTO dto = new AlumnoConsultaDTO();
            dto.setMatricula(alumno.getMatricula());
            dto.setNombre(alumno.getNombre());
            dto.setNumeroCelular(alumno.getTelefonoPadre());
            return dto;
        }
        return null;
    }

    public List<AlumnoConsultaDTO> buscarAlumnosPorNombre(String nombre) {
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

    public List<Alumno> buscarAlumnoPorPadre(String telefonoPadre) {
        List<Alumno> list = repository.findByTelefonoPadre(telefonoPadre);

        if (list != null)
            return list;
        return new ArrayList<Alumno>();
    }

    // metodos de registro y actualizacion de alumnos nueva entidad

    /**
     * Metodo para registrar alumnos con sus respectivas cuotas.
     *
     * @param alumno Objeto de tipo Alumno a registrar
     */
    @Transactional
    public void registrarAlumnoConCuotas(Alumno alumno) {
        // Guardamos al alumno primero
        repository.save(alumno);

        // Obtenemos el ciclo escolar actual
        CicloEscolar cicloActual = cicloEscolarService.obtenerCicloActualEntidad();

        // Obtenemos los montos del detalle del ciclo (cuando vale cada cuota)
        DetalleCiclo detalle = detalleCicloRepository.findByCicloEscolar(cicloActual)
                .orElseThrow(() -> new IllegalStateException("No hay detalle para el ciclo actual"));

        // 4. Crear cuotas
        List<Cuota> cuotas = new ArrayList<>();

        cuotas.add(crearCuota(alumno, cicloActual, Concepto.INSCRIPCION, detalle.getCuotaInscripcion()));
        cuotas.add(crearCuota(alumno, cicloActual, Concepto.COLEGIATURA, detalle.getCuotaColegiatura()));
        cuotas.add(crearCuota(alumno, cicloActual, Concepto.LIBROS, detalle.getCuotaLibros()));
        cuotas.add(crearCuota(alumno, cicloActual, Concepto.EVENTOS, detalle.getCuotaEventos()));
        cuotas.add(crearCuota(alumno, cicloActual, Concepto.ACADEMIAS, detalle.getCuotaAcademias()));
        cuotas.add(crearCuota(alumno, cicloActual, Concepto.UNIFORMES, detalle.getCuotaUniforme()));

        // Guardamos las cuotas del alumno
        cuotaRepository.saveAll(cuotas);
    }

    /**
     * Metodo auxiliar para la creacion de cuotas para el metodo de registrar
     * alumno.
     *
     * @param alumno   Objeto de tipo alumno.
     * @param ciclo    Ciclo al que pertenecera el
     * @param concepto Tipo de concepto a generar para la cuota.
     * @param monto    Monto total de la cuota a generar.
     *
     * @return Objeto de tipo cuota.
     */
    private Cuota crearCuota(Alumno alumno, CicloEscolar ciclo, Concepto concepto, Double monto) {
        Cuota cuota = new Cuota();
        cuota.setAlumno(alumno);
        cuota.setCiclo(ciclo);
        cuota.setConcepto(concepto);
        cuota.setMontoBase(monto);
        return cuota;
    }

    /**
     * Actualiza los datos del alumno en la base de datos.
     *
     * @param datosActualizados Objeto de tipo Alumno con los nuevos datos.
     *
     */
    @Transactional
    public void actualizarAlumno(Alumno datosActualizados) {
        // Buscamos al alumno y si no existe le enviamos una excepcion bien macabra
        Alumno alumnoExistente = repository.findById(datosActualizados.getMatricula())
                .orElseThrow(() -> new EntityNotFoundException("Alumno no encontrado al actualizar"));

        // Actualizamos los campos, menos matricula obvio
        alumnoExistente.setNombre(datosActualizados.getNombre());
        alumnoExistente.setCorreo(datosActualizados.getCorreo());
        alumnoExistente.setGenero(datosActualizados.getGenero());
        alumnoExistente.setTelefonoPadre(datosActualizados.getTelefonoPadre());
        alumnoExistente.setFechaNacimiento(datosActualizados.getFechaNacimiento());
        alumnoExistente.setTutor(datosActualizados.getTutor());
        alumnoExistente.setGrupo(datosActualizados.getGrupo());
        alumnoExistente.setGrado(datosActualizados.getGrado());
        alumnoExistente.setNivel(datosActualizados.getNivel());
        alumnoExistente.setEstatus(datosActualizados.getEstatus());
        alumnoExistente.setPromedio(datosActualizados.getPromedio());
        alumnoExistente.setBeca(datosActualizados.getBeca());

        // Guardamos cambios
        repository.save(alumnoExistente);
    }

    /**
     * Busca a todos los alumnos en la base de datos.
     */
    @Transactional
    public List<Alumno> obtenerTodosLosAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        repository.findAll().forEach(alumnos::add);
        return alumnos;
    }

    /**
     * Busca por matricula
     */
    @Transactional
    public Alumno obtenerAlumnoPorMatricula(String matricula) {
        return repository.findById(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Alumno no encontrado con matrícula: " + matricula));
    }
}
