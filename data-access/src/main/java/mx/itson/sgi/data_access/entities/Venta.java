/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sgi.data_access.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
@Table(name = "ventas")
@Data
public class Venta {
    
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Expose
    @Column(name = "fecha_hora",nullable = false)
    private LocalDateTime fechaHora;
    
    @Expose
    @Column(name = "monto",nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;
    
    @Expose
    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;
    
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    } 
}
