/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sgi.data_access.entities;

import java.time.LocalDate;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ciclos_escolares")
@Data
public class CicloEscolar {
    @Expose
    @Id
    private String id;
    @Column(name = "fecha_inicio",nullable = false)
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin",nullable = false)
    private LocalDate fechaFin;

    public CicloEscolar(){}

    public CicloEscolar(String id){this.id=id;}

    public CicloEscolar(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.id = (fechaInicio.getYear() % 100) + "-" + (fechaFin.getYear() % 100);
    }
    
    @Override
    public String toString() {
        return "{id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "}";
    }
    
}