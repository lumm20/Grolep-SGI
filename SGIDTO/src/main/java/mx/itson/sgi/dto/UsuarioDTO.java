package mx.itson.sgi.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class UsuarioDTO {

	private Long id;
    private String nombre;
    private String contra;
    private RolDTO rol;
    private String correo;

    /**
     * constructor vacio
     */
    public UsuarioDTO() {}

    // Constructor
    public UsuarioDTO(Long identificador, String nombre, String correo, String contra, RolDTO rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.rol=rol;
    }
}

