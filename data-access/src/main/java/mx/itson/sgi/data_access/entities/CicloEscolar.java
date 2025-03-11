package mx.itson.sgi.data_access.entities;

import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ciclos_escolares")
public class CicloEscolar {
    
    @Id
    private String id;
    @Column(name = "fecha_inicio",nullable = false)
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin",nullable = false)
    private LocalDate fechaFin;

    public CicloEscolar(){}

    public CicloEscolar(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.id = fechaInicio.getYear() + "-" + fechaFin.getYear();
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "}";
    }

    
}
