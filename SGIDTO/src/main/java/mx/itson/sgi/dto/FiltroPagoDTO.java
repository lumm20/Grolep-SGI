package mx.itson.sgi.dto;

import java.time.LocalDate;

import lombok.Data;
import mx.itson.sgi.dto.enums.MetodoParaPagos;

//este es el nuevo filtto
@Data
public class FiltroPagoDTO {
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private Double montoMinimo;
    private Double montoMaximo;
    private UsuarioDTO usuario;
    private AlumnoConsultaDTO alumno;
    private MetodoParaPagos metodoPago;
}
