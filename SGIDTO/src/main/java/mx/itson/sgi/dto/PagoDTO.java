package mx.itson.sgi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class PagoDTO {
    private Double montoTotal;
    private String folio;
    private LocalDateTime fecha;
    private AlumnoConsultaDTO alumno;
    private MetodosPagoDTO metodoPago;
    private List<DetallePagoDTO> cuotasPagadas;
    private String descuento;
    private UsuarioDTO usuario;

    public PagoDTO() {
    }


    public PagoDTO(Double montoTotal, AlumnoConsultaDTO alumno, MetodosPagoDTO metodoPago,
            List<DetallePagoDTO> cuotasPagadas, String descuento, UsuarioDTO usuario) {
        this.montoTotal = montoTotal;
        this.alumno = alumno;
        this.metodoPago = metodoPago;
        this.cuotasPagadas = cuotasPagadas;
        this.descuento = descuento;
        this.usuario = usuario;
    }


    public PagoDTO(Double montoTotal, String folio, LocalDateTime fecha, AlumnoConsultaDTO alumno,
            MetodosPagoDTO metodoPago, List<DetallePagoDTO> cuotasPagadas, String descuento, UsuarioDTO usuario) {
        this.montoTotal = montoTotal;
        this.folio = folio;
        this.fecha = fecha;
        this.alumno = alumno;
        this.metodoPago = metodoPago;
        this.cuotasPagadas = cuotasPagadas;
        this.descuento = descuento;
        this.usuario = usuario;
    }

    

    
}
