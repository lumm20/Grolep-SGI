package mx.itson.sgi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class PagoDTO {

    private String folio;
    private LocalDate fecha;
    private LocalTime hora;
    private UsuarioDTO usuario;
    private AlumnoConsultaDTO alumno;
    private String metodoPago;
    private List<PagoCuotaDTO> cuotasPagadas;
    private String tipoDescuento;
    private BigDecimal descuento;
    private BigDecimal subTotal;
    private BigDecimal montoTotal;

}
