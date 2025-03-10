package mx.itson.sgi.data_access.entities;

import java.time.LocalDate;

public class CicloEscolar {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String id;

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

}
