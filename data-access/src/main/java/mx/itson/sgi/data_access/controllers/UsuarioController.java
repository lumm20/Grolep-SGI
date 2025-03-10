package mx.itson.sgi.data_access.controllers;

import java.util.Optional;

import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.UsuarioRepository;
import mx.itson.sgi.data_access.utilities.Security;


public class UsuarioController {
    private UsuarioRepository repository;
    private Security security;
    
    public boolean login(Usuario usuario){
        Optional<Usuario> optional = repository.findByName(usuario.getNombre());
        if(optional.isPresent()){
            Usuario usuarioEncontrado = optional.get();
            return security.authenticate(usuario.getContra(), usuarioEncontrado.getContra());
        }
        return false;
    }

    public Usuario findUser(String username){
        Optional<Usuario> usuarioBuscado = repository.findUserByName(username);
        
        if(usuarioBuscado.isPresent()){
            return usuarioBuscado.get();
        }
        return null;
    }
}
