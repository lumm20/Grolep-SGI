package mx.itson.sgi.data_access.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.UsuarioRepository;
import mx.itson.sgi.data_access.utilities.Security;

@Service
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private Security security;
    
    public boolean login(Usuario usuario){
        Optional<Usuario> optional = repository.findByNombre(usuario.getNombre());
        if(optional.isPresent()){
            Usuario usuarioEncontrado = optional.get();
            return security.authenticate(usuario.getContrasena(), usuarioEncontrado.getContrasena());
        }
        return false;
    }

    public Usuario findUser(String username){
        Optional<Usuario> usuarioBuscado = repository.findUserByNombre(username);
        
        if(usuarioBuscado.isPresent()){
            return usuarioBuscado.get();
        }
        return null;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        String contraHasheada = security.encodePassword(usuario.getContrasena());
        usuario.setContrasena(contraHasheada);
        
        return repository.save(usuario);
    }
}
