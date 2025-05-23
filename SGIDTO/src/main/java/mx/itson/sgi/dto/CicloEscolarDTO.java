package mx.itson.sgi.dto;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CicloEscolarDTO {

    @Expose
    private String id;
    private String inicio;
    private String fin;

    public CicloEscolarDTO() {
    }

    public CicloEscolarDTO(String id) {
        this.id = id;
    }

    // Constructor
    public CicloEscolarDTO(String inicio, String fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public String toString() {
        return inicio.substring(0, 4) + " - " + fin.substring(0, 4);
    }

    public String getIDString() {
        return inicio.substring(2, 4) + "-" + fin.substring(2, 4);
    }

}
