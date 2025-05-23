package mx.itson.sgi.dto;

import com.google.gson.annotations.Expose;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UsuarioDTO {

    @Expose
	private Long id;
    // @Expose
    @Expose
    private String nombre;
    @Expose
    private String contra;
    // @Expose
    @Expose
    private RolDTO rol;
    // @Expose
    @Expose
    private String correo;

    /**
     * constructor vacio
     */
    public UsuarioDTO() {}
    public UsuarioDTO(Long id) {this.id=id;}

    // Constructor
    public UsuarioDTO(Long identificador, String nombre, String correo, String contra, RolDTO rol) {
        this.id = identificador;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.rol=rol;
    }

    public UsuarioDTO(String correo, String contra){
        this.correo = correo;
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

    @Override
    public String toString() { return nombre;}
}

