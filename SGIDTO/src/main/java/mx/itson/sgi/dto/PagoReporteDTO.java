package mx.itson.sgi.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

//retorna este en filtro

@Data
@Builder
//@NoArgsConstructor
@AllArgsConstructor
public class PagoReporteDTO {

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
    private String tipoDescuento;

    @Expose
    private Double montoDescuento;

    @Expose
    private UsuarioDTO usuario;

    @Expose
    private List<DetallePagoDTO> cuotasPagadas;

    public PagoReporteDTO() {
    }
}
