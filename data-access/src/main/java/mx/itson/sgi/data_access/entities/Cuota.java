/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sgi.data_access.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cuotas")
@Data
public class Cuota {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Expose
    @Column(name = "monto_base", nullable = false)
    private Double montoBase;

    @Expose
    @ManyToOne
    @JoinColumn(name = "ciclo_escolar",nullable=false)
    private CicloEscolar ciclo;
    
    @Expose
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_alumno",referencedColumnName = "matricula", nullable = false)
    private Alumno alumno;

    @Expose
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Concepto concepto;

    public Cuota() {
    }

    public Cuota(Double montoBase, CicloEscolar ciclo, Concepto concepto) {
        this.montoBase = montoBase;
        this.ciclo = ciclo;
        this.concepto = concepto;
    }

    public Cuota(Double montoBase, Concepto concepto) {
        this.montoBase = montoBase;
        this.concepto = concepto;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    } 
}
