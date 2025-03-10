package mx.itson.sgi.data_access.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(nullable = false)
    private String contra;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private String correo;
    
    public Usuario(String nombre, String contra, Rol rol, String correo) {
        this.nombre = nombre;
        this.contra = contra;
        this.rol = rol;
        this.correo = correo;
    }

    public Usuario(String nombre, Rol rol, String correo) {
        this.nombre = nombre;
        this.rol = rol;
        this.correo = correo;
    }

    public Usuario() {
    }

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
    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", nombre=" + nombre + ", rol=" + rol + ", correo=" + correo + "}";
    }

}
