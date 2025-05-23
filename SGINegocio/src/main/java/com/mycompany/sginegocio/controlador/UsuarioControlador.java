package com.mycompany.sginegocio.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.sginegocio.excepciones.UserException;

import mx.itson.sgi.data_access.services.UsuarioService;
import mx.itson.sgi.dto.AuthenticationResponse;
import mx.itson.sgi.dto.UsuarioDTO;

@Service
public class UsuarioControlador {

    @Autowired
    UsuarioService service;

    public AuthenticationResponse loginSecure(UsuarioDTO usuario)throws UserException{
        if(usuario.getCorreo() != null && usuario.getContra() != null){
            AuthenticationResponse response = service.authenticate(usuario);
            if(response.getToken() != null){
                return response;
            }
            throw new UserException("Invalid credentials", UserException.BAD_CREDENTIALS);
        }
        throw new UserException("Empty field",UserException.EMPTY_FIELD);
    }
    public UsuarioDTO login(UsuarioDTO usuario)throws UserException{
        if(usuario.getNombre() != null && usuario.getContra() != null){
            UsuarioDTO logged = service.login(usuario);
            if(logged!= null){
                if(logged.getNombre() != null){
                    return logged;
                }
                throw new UserException("Wrong password", UserException.WRONG_PASS);
            }
            throw new UserException("User not found", UserException.USER_NOT_FOUND);
        }
        throw new UserException("Empty field",UserException.EMPTY_FIELD);
    }

    public void registrarUsuario(UsuarioDTO usuario)throws UserException{
        if(usuario.getNombre() != null && usuario.getContra()!= null && usuario.getRol() != null){
            validateUserFields(usuario);
            service.registrarUsuario(usuario);
            UsuarioDTO user = service.findUser(usuario.getNombre());
            if(user == null){
                throw new UserException("Error while signing in", UserException.UNSUCCESFUL_SIGNIN);
            }
        }else{
            throw new UserException("Empty field", UserException.EMPTY_FIELD);
        }
    }

    private void validateUserFields(UsuarioDTO usuario)throws UserException {
        List<UsuarioDTO> users = service.obtenerUsuarios();
        for (UsuarioDTO u : users) {
            if(u.getNombre().equalsIgnoreCase(usuario.getNombre())){
                throw new UserException("Username already exists", UserException.USERNAME_CONFLICT);
            }if(u.getCorreo().equalsIgnoreCase(usuario.getCorreo()))
                throw new UserException("User with that email already exists", UserException.EMAIL_CONFLICT);
        }
    }

    public List<UsuarioDTO> obtenerUsuarios() {
        return service.obtenerUsuarios();
    }
}
