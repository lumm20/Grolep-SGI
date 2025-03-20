package mx.itson.sgi.dto;

import java.util.UUID;
import lombok.Data;

public class UsuarioDTO {

	private Long id;
    private String nombre;
    private String contra;
    private RolDTO rol;
    private String correo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public RolDTO getRol() {
        return rol;
    }

    public void setRol(RolDTO rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


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

    public UsuarioDTO(String nombre, String correo, RolDTO rol){
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
}

