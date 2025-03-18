package mx.itson.sgi.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CicloEscolarDTO {

    private LocalDate inicio;
    private LocalDate fin;

    // Constructor
    public CicloEscolarDTO(LocalDate inicio, LocalDate fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public String toString() {
        return String.format("%s %s", inicio + " - " + fin);
    }

}
