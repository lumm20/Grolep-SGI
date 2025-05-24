package com.mycompany.sginegocio.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.sginegocio.controlador.UsuarioControlador;
import com.mycompany.sginegocio.excepciones.UserException;

import mx.itson.sgi.dto.AuthenticationResponse;
import mx.itson.sgi.dto.UsuarioDTO;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/users")
public class APIUsuario {

    @Autowired
    UsuarioControlador controlador;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuario) {
        UsuarioDTO user;
        try {
            user = controlador.login(usuario);
            System.out.println(user);
            return ResponseEntity.ok().body(user);
        } catch (UserException e) {
            if(e.getType() == UserException.USER_NOT_FOUND){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());            
            }else if(e.getType() == UserException.WRONG_PASS){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());   
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/login-sec")
    public ResponseEntity<?> login2(@RequestBody UsuarioDTO usuario) {
        try {
            AuthenticationResponse resp = controlador.loginSecure(usuario);
            System.out.println(resp);
            return ResponseEntity.ok().body(resp);
        } catch (UserException e) {
            if(e.getType() == UserException.BAD_CREDENTIALS){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());   
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/register")
    // @PreAuthorize("hasRole(ROLE_ADMIN)")
    public ResponseEntity<?> registrar(@RequestBody UsuarioDTO usuario){
        try {
            controlador.registrarUsuario(usuario);
            return ResponseEntity.ok().build();
        } catch (UserException e) {
            if(e.getType()== UserException.EMPTY_FIELD){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }else if(e.getType() == UserException.USERNAME_CONFLICT || e.getType() == UserException.EMAIL_CONFLICT){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
    
    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            return ResponseEntity.ok(controlador.obtenerUsuarios());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener usuarios: " + e.getMessage());
        }
    }
    
}
