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
import lombok.Data;

@Entity
@Table(name = "detalles_pagos")
@Data
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
    
    public DetallePago(Cuota cuota, Double montoPagado) {
        this.cuota = cuota;
        this.montoPagado = montoPagado;
    }

    public DetallePago(Cuota cuota,  Double montoPagado,Pago pago) {
        this.cuota = cuota;
        this.montoPagado = montoPagado;
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "{folio pago=" + pago.getFolio() + ", concepto cuota=" + cuota.getConcepto().name()+ ", monto pagado=" + montoPagado + "}";
    }

    
    
}
