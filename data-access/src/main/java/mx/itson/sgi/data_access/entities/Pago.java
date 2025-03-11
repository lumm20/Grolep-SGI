package mx.itson.sgi.data_access.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    
    @OneToMany(mappedBy = "pago", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<DetallePago> detalles;
    
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
        this.detalles = new ArrayList<>();
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

    public List<DetallePago> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePago> detalles) {
        this.detalles = detalles;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

}
