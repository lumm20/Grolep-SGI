package mx.itson.sgi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class PagoDTO {

    private BigDecimal montoTotal;
    private String folio;
    private LocalDate fecha;
    private LocalTime hora;
    private AlumnoConsultaDTO alumno;
    private String metodoPago;
    private List<PagoCuotaDTO> cuotasPagadas;
    private String descuento;
    private UsuarioDTO usuario;

}
