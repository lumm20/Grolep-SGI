package mx.itson.sgi.dto;

import java.time.LocalDate;

import lombok.Data;
import mx.itson.sgi.dto.enums.MetodoParaPagos;
import com.google.gson.annotations.Expose;

//este es el nuevo filtto
@Data
public class FiltroPagoDTO {
    @Expose
    private LocalDate fechaDesde;
    @Expose
    private LocalDate fechaHasta;
    @Expose
    private Double montoMinimo;
    @Expose
    private Double montoMaximo;
    @Expose
    private UsuarioDTO usuario;
    @Expose
    private AlumnoConsultaDTO alumno;
    @Expose
    private MetodoParaPagos metodoPago;
}
