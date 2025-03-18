package mx.itson.sgi.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class UsuarioDTO {

    private UUID identificador;
    private String nombre;
    private String correo;

    // Constructor
    public UsuarioDTO(UUID identificador, String nombre, String correo) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.correo = correo;
    }
}

