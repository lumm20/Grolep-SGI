package mx.itson.sgi.dto;

import java.time.LocalDate;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class CicloEscolarDTO {

    @Expose
    private String id;
    private LocalDate inicio;
    private LocalDate fin;

    public CicloEscolarDTO(String id){
        this.id = id;
    }
    // Constructor
    public CicloEscolarDTO(LocalDate inicio, LocalDate fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public String toString() {
        return inicio + " - " + fin;
    }

}
