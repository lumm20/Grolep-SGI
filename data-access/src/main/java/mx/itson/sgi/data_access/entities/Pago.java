package mx.itson.sgi.data_access.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    private String folio;
    @Column(name = "fecha_hora",nullable=false)
    private LocalDateTime fechaHora;
    @Column(name = "monto_total",nullable=false)
    private Double montoTotal;
    @ManyToOne
    @JoinColumn(name = "id_cajero")
    private Usuario cajero;
    
    @ManyToOne
    @JoinColumn(name = "id_cuota")
    private Cuota cuota;
    
    @Column(name = "metodo_pago",nullable=false)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    public Pago() {
    }
    
    public Pago(String folio, LocalDateTime fechaHora, Double montoTotal,
     Usuario cajero, Cuota cuota, MetodoPago metodoPago) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.montoTotal = montoTotal;
        this.cajero = cajero;
        this.cuota = cuota;
        this.metodoPago = metodoPago;
    }

    public String getFolio() {
        return folio;
    }
    public void setFolio(String folio) {
        this.folio = folio;
    }
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    public Double getMontoTotal() {
        return montoTotal;
    }
    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }
    public Usuario getCajero() {
        return cajero;
    }
    public void setCajero(Usuario cajero) {
        this.cajero = cajero;
    }

    public Cuota getCuota() {
        return cuota;
    }

    public void setCuota(Cuota cuota) {
        this.cuota = cuota;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

}
