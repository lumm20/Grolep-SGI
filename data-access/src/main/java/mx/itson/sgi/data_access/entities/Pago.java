/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sgi.data_access.entities;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

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

import lombok.Data;

@Entity
@Table(name = "pagos")
@Data
public class Pago {

    @Expose
    @Id
    @Column(nullable = false, unique = true)
    private String folio;

    @Expose
    @Column(name = "fecha_hora",nullable=false)
    private LocalDateTime fecha;

    @Expose
    @Column(name = "monto_total",nullable=false)
    private Double montoTotal;
    @Expose
    @Column(name = "monto_descuento_aplicado",nullable=false)
    private Double montoDescuento;
    @Expose
    @Column(name = "tipo_descuento_aplicado",nullable=false)
    private String tipoDescuento;
    
    @Expose
    @ManyToOne
    @JoinColumn(name = "id_cajero")
    private Usuario cajero;
    
    @Expose
    @ManyToOne
    @JoinColumn(name = "matricula_alumno")
    private Alumno alumno;
    
    @Expose
    @OneToMany(mappedBy = "pago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePago> detalles;
    
    @Expose
    @Column(name = "metodo_pago",nullable=false)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    public Pago() {
        this.detalles = new ArrayList<>();
    }
    
    public Pago(String folio, LocalDateTime fechaHora, Double montoTotal,
     Usuario cajero, MetodoPago metodoPago, Alumno alumno,Double montoDescuento,String tipoDescuento) {
        this.folio = folio;
        this.fecha = fechaHora;
        this.montoTotal = montoTotal;
        this.cajero = cajero;
        this.detalles = new ArrayList<>();
        this.metodoPago = metodoPago;
        this.alumno = alumno;
        this.montoDescuento = montoDescuento;
        this.tipoDescuento = tipoDescuento;
    }

    public Pago(Double montoTotal, Usuario cajero, Alumno alumno, List<DetallePago> detalles,
     MetodoPago metodoPago,Double montoDescuento,String tipoDescuento) {
        this.montoTotal = montoTotal;
        this.cajero = cajero;
        this.alumno = alumno;
        this.detalles = detalles;
        this.metodoPago = metodoPago;
        this.montoDescuento = montoDescuento;
        this.tipoDescuento = tipoDescuento;
    }

    public String getFolio() {
        return folio;
    }
    public void setFolio(String folio) {
        this.folio = folio;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fechaHora) {
        this.fecha = fechaHora;
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

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().
        setPrettyPrinting().
        excludeFieldsWithoutExposeAnnotation().
        registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
        }).create();
        return gson.toJson(this);
    }

    
}
