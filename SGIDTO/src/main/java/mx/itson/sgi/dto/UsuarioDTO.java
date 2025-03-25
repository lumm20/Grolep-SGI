package mx.itson.sgi.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class UsuarioDTO {

	private Long id;
    // @Expose
    private String nombre;
    private String contra;
    // @Expose
    private RolDTO rol;
    // @Expose
    private String correo;

    /**
     * constructor vacio
     */
    public UsuarioDTO() {}

    // Constructor
    public UsuarioDTO(Long identificador, String nombre, String correo, String contra, RolDTO rol) {
        this.id = identificador;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.rol=rol;
    }

    public UsuarioDTO(String nombre, String contra){
        this.nombre = nombre;
        this.contra = contra;
    }

    public UsuarioDTO(Long id, String nombre, String correo, RolDTO rol){
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }
    public UsuarioDTO(String nombre, String correo, RolDTO rol, String contra){
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.contra = contra;
    }

    public String toString(){
        return "{id: "+id+", nombre: "+nombre+", correo: "+correo+", rol: "+rol+"}";
    }
}

