package mx.itson.sgi.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.annotations.Expose;

@Data
public class PagoDTO {
    @Expose
    private Double montoTotal;
    @Expose
    private String folio;
    @Expose(serialize = false)
    private LocalDateTime fecha;
    @Expose
    private AlumnoConsultaDTO alumno;
    @Expose
    private MetodosPagoDTO metodoPago;
    @Expose
    private List<DetallePagoDTO> cuotasPagadas;
    
    @Expose
    private String tipoDescuento;
    @Expose
    private Double montoDescuento;
    @Expose
    private String idCicloEscolar;
    @Expose
    private Long idUsuario;

    public PagoDTO() {
    }


    public PagoDTO(Double montoTotal, AlumnoConsultaDTO alumno, MetodosPagoDTO metodoPago,
            List<DetallePagoDTO> cuotasPagadas, String tipoDescuento, Long idUsuario, String idCicloEscolar,Double montoDescuento) {
        this.montoTotal = montoTotal;
        this.alumno = alumno;
        this.metodoPago = metodoPago;
        this.cuotasPagadas = cuotasPagadas;
        this.tipoDescuento = tipoDescuento;
        this.idUsuario = idUsuario;
        this.idCicloEscolar = idCicloEscolar;
        this.montoDescuento= montoDescuento;
    }


    public PagoDTO(Double montoTotal, String folio, LocalDateTime fecha, AlumnoConsultaDTO alumno,
            MetodosPagoDTO metodoPago, List<DetallePagoDTO> cuotasPagadas, String descuento, 
            Long idUsuario, String idCicloEscolar, Double montoDescuento) {
        this.montoTotal = montoTotal;
        this.folio = folio;
        this.fecha = fecha;
        this.alumno = alumno;
        this.metodoPago = metodoPago;
        this.cuotasPagadas = cuotasPagadas;
        this.tipoDescuento = descuento;
        this.idUsuario = idUsuario;
        this.idCicloEscolar = idCicloEscolar;
        this.montoDescuento = montoDescuento;
    }

    

    
}
