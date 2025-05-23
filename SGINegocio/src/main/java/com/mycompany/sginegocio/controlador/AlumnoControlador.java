package com.mycompany.sginegocio.controlador;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mycompany.sginegocio.excepciones.StudentException;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.Beca;
import mx.itson.sgi.data_access.entities.TipoBeca;
import mx.itson.sgi.data_access.services.AlumnoService;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.AlumnoRegistroDTO;
import mx.itson.sgi.dto.BecaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.DetalleCicloDTO;
import mx.itson.sgi.dto.enums.Estatus;
import mx.itson.sgi.dto.enums.Nivel;
import mx.itson.sgi.dto.enums.Genero;

@Service
public class AlumnoControlador {

    @Autowired
    AlumnoService service;

    public List<AlumnoConsultaDTO> obtenerAlumnosPorNombre(String nombre)throws StudentException {
        if(!nombre.isBlank() && nombre.matches("[a-zA-Z]+")){
            return service.buscarAlumnosPorNombre(nombre);
        }
        throw new StudentException("Invalid name", StudentException.INVALID_NAME);
    }

    public void crearEstudiante(AlumnoRegistroDTO alumnoDTO) {
        Alumno alumno = new Alumno();
        alumno.setMatricula(alumnoDTO.getMatricula());
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setPromedio(alumnoDTO.getPromedio());
        alumno.setGrado(alumnoDTO.getGrado());
        alumno.setGrupo(alumnoDTO.getGrupo());
        alumno.setEstatus(alumnoDTO.getEstatus());
        alumno.setNivel(alumnoDTO.getNivel());
        alumno.setFechaNacimiento(LocalDate.parse(alumnoDTO.getFechaNacimiento()));
        alumno.setTelefono(alumnoDTO.getTelefono());
        alumno.setTelefonoPadre(alumnoDTO.getTelefono());
        alumno.setGenero(alumnoDTO.getGenero());
        alumno.setCorreo(alumnoDTO.getCorreo());
        alumno.setTutor(alumnoDTO.getTutor());

        Beca beca = new Beca(TipoBeca.valueOf(alumnoDTO.getBeca().getTipo()), alumnoDTO.getBeca().getDescuento().doubleValue());
        alumno.setBeca(beca);

        System.out.println("ya se creo el alumno: " + alumno.toString());
        service.registrarAlumnoConCuotas(alumno);
    }

    public void actualizarEstudiante(AlumnoRegistroDTO alumnoDTO) {
        // Convertir el DTO a la entidad Alumno
        Alumno alumno = new Alumno();
        alumno.setMatricula(alumnoDTO.getMatricula());
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setPromedio(alumnoDTO.getPromedio());
        alumno.setGrado(alumnoDTO.getGrado());
        alumno.setGrupo(alumnoDTO.getGrupo());
        alumno.setEstatus(alumnoDTO.getEstatus());
        alumno.setNivel(alumnoDTO.getNivel());
        alumno.setFechaNacimiento(LocalDate.parse(alumnoDTO.getFechaNacimiento()));
        alumno.setTelefono(alumnoDTO.getTelefono());
        alumno.setTelefonoPadre(alumnoDTO.getTelefono());
        alumno.setGenero(alumnoDTO.getGenero());
        alumno.setCorreo(alumnoDTO.getCorreo());
        alumno.setTutor(alumnoDTO.getTutor());

        Beca beca = new Beca(TipoBeca.valueOf(alumnoDTO.getBeca().getTipo()), alumnoDTO.getBeca().getDescuento().doubleValue());
        alumno.setBeca(beca);

        service.actualizarAlumno(alumno);
    }

    public List<AlumnoRegistroDTO> obtenerTodos(){
        List<Alumno> alumnos = service.obtenerTodosLosAlumnos();
        return convertirListaAlumnosADTO(alumnos);
    }

    public List<AlumnoRegistroDTO> obtenerTodosPaginados(int offset, int limit){
        List<Alumno> alumnos = service.obtenerTodosLosAlumnosPaginados(offset-1,limit);
        return convertirListaAlumnosADTO(alumnos);
    }

     private List<AlumnoRegistroDTO> convertirListaAlumnosADTO(List<Alumno> alumnos) {
        // List<Alumno> alumnos = service.obtenerTodosLosAlumnos();
        List<AlumnoRegistroDTO> alumnosDTO = new ArrayList<>();

        for (Alumno alumno : alumnos) {
            AlumnoRegistroDTO dto = new AlumnoRegistroDTO();
            dto.setMatricula(alumno.getMatricula());
            dto.setNombre(alumno.getNombre());
            dto.setCorreo(alumno.getCorreo());
            dto.setGenero(alumno.getGenero());
            dto.setTelefono(alumno.getTelefonoPadre());
            dto.setFechaNacimiento(alumno.getFechaNacimiento().toString());
            dto.setTutor(alumno.getTutor());
            dto.setGrupo(alumno.getGrupo());
            dto.setGrado(alumno.getGrado());
            dto.setNivel(alumno.getNivel());
            dto.setEstatus(alumno.getEstatus());
            dto.setPromedio(alumno.getPromedio());

            if (alumno.getBeca() != null) {
                dto.setBeca(new BecaDTO(alumno.getBeca().getTipo().toString(), BigDecimal.valueOf(alumno.getBeca().getPorcentajeDescuento())));
            }

            alumnosDTO.add(dto);
        }

        return alumnosDTO;
    }

    public AlumnoRegistroDTO convertirAlumnoADTO(String matricula) {
        Alumno alumno = service.obtenerAlumnoPorMatricula(matricula);

        AlumnoRegistroDTO dto = new AlumnoRegistroDTO();
        dto.setMatricula(alumno.getMatricula());
        dto.setNombre(alumno.getNombre());
        dto.setCorreo(alumno.getCorreo());
        dto.setGenero(alumno.getGenero());
        dto.setTelefono(alumno.getTelefonoPadre());
        dto.setFechaNacimiento(alumno.getFechaNacimiento().toString());
        dto.setTutor(alumno.getTutor());
        dto.setGrupo(alumno.getGrupo());
        dto.setGrado(alumno.getGrado());
        dto.setNivel(alumno.getNivel());
        dto.setEstatus(alumno.getEstatus());
        dto.setPromedio(alumno.getPromedio());

        if (alumno.getBeca() != null) {
            dto.setBeca(new BecaDTO(alumno.getBeca().getTipo().toString(), BigDecimal.valueOf(alumno.getBeca().getPorcentajeDescuento())));
        }

        return dto;
    }

    public List<AlumnoRegistroDTO> obtenerAlumnosPorNombreCompleto(String nombre, int page, int size) {
        nombre = nombre.replace("+", " ");
        Page<Alumno> pageResult = service.buscarAlumnosCompletosPorNombre(nombre, page-1,  size);
        List<Alumno> alumnos = pageResult.getContent();
        List<AlumnoRegistroDTO> dtos = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            AlumnoRegistroDTO dto = new AlumnoRegistroDTO();
            dto.setMatricula(alumno.getMatricula());
            dto.setNombre(alumno.getNombre());
            dto.setTelefono(alumno.getTelefonoPadre());
            dto.setCorreo(alumno.getCorreo());
            dto.setFechaNacimiento(alumno.getFechaNacimiento().toString());
            dto.setTutor(alumno.getTutor());
            dto.setGrupo(alumno.getGrupo());
            dto.setGrado(alumno.getGrado());
            dto.setNivel(alumno.getNivel());
            dto.setEstatus(alumno.getEstatus());
            dto.setPromedio(alumno.getPromedio());
            if (alumno.getBeca() != null) {
                dto.setBeca(new BecaDTO(alumno.getBeca().getTipo().toString(), BigDecimal.valueOf(alumno.getBeca().getPorcentajeDescuento())));
            }

            dtos.add(dto);
        }
        return dtos;
    }
}
