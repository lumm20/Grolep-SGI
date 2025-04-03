/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sgi.data_access.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;
    @Expose
    @Column(nullable = false, unique = true)
    private String nombre;

    @Expose
    @Column(unique = true)
    private String correo;
    
    @Expose
    @Column(nullable = false)
    private String contrasena;
    
    @Expose
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
    
    @OneToMany(mappedBy = "cajero")
    private List<Pago> pagos = new ArrayList<>();

    public Usuario(Long id,String nombre, Rol rol, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.correo = correo;
    }
    public Usuario(Long id,String contrasena,  String correo,String nombre, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
        this.correo = correo;
    }

    public Usuario(String nombre, String contrasena, Rol rol, String correo) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
        this.correo = correo;
    }

    public Usuario(Long id){
        this.id = id;
    }
    public Usuario() {
    }
    
    @Override
    public String toString(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+rol));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
