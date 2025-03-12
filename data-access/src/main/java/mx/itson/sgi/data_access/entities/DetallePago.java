package mx.itson.sgi.data_access.entities;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_pagos")
public class DetallePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name ="folio_pago", referencedColumnName = "folio")
    private Pago pago;
    
    @Expose
    @ManyToOne
    @JoinColumn(name ="id_cuota",referencedColumnName = "id")
    private Cuota cuota;

    @Expose
    @Column(name = "monto_pagado")
    private Double montoPagado;
    
    public DetallePago() {
    }
    
    public DetallePago(Cuota cuota,  Double montoPagado,Pago pago) {
        this.cuota = cuota;
        this.montoPagado = montoPagado;
        this.pago = pago;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Pago getPago() {
        return pago;
    }
    public void setPago(Pago pago) {
        this.pago = pago;
    }
    public Cuota getCuota() {
        return cuota;
    }
    public void setCuota(Cuota cuota) {
        this.cuota = cuota;
    }
    public Double getMontoPagado() {
        return montoPagado;
    }
    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    @Override
    public String toString() {
        return "{folio pago=" + pago.getFolio() + ", concepto cuota=" + cuota.getConcepto().name()+ ", monto pagado=" + montoPagado + "}";
    }

    
    
}
