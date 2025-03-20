package mx.itson.sgi.data_access.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.entities.Rol;
import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.UsuarioRepository;
import mx.itson.sgi.data_access.utilities.Security;
import mx.itson.sgi.dto.RolDTO;
import mx.itson.sgi.dto.UsuarioDTO;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private Security security;
    
    public UsuarioDTO login(UsuarioDTO usuario){
        Optional<Usuario> optional = repository.findByNombre(usuario.getNombre());
        if(optional.isPresent()){
            Usuario usuarioEncontrado = optional.get();
            String passEncoded= security.encodePassword(usuario.getContra());
            usuario.setContra(passEncoded);
            if (security.authenticate(usuario.getContra(), usuarioEncontrado.getContrasena())){
                UsuarioDTO loggedDTO = findUser(usuario.getNombre());
                return loggedDTO;
            }
            return new UsuarioDTO();
        }
        return null;
    }

    public UsuarioDTO findUser(String username){
        Optional<Usuario> usuarioBuscado = repository.findUserByNombre(username);
        
        if(usuarioBuscado.isPresent()){
            Usuario user = usuarioBuscado.get();
            RolDTO rol = (user.getRol().equals(Rol.ADMIN) ? RolDTO.ADMIN:RolDTO.CAJERO);
            UsuarioDTO dto = new UsuarioDTO(user.getNombre(), user.getCorreo(), rol);
            return dto;
        }
        return null;
    }

    public void registrarUsuario(UsuarioDTO usuario) {
        String contraHasheada = security.encodePassword(usuario.getContra());
        Rol rol = (usuario.getRol().equals(RolDTO.ADMIN) ? Rol.ADMIN : Rol.CAJERO);
        Usuario user = new Usuario(usuario.getNombre(), contraHasheada, rol, usuario.getCorreo());     
        repository.save(user);
    }

    public List<UsuarioDTO> obtenerUsuarios(){
        List<Usuario> usuarios = repository.findUsers();
        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            RolDTO rol = (usuario.getRol().equals(Rol.ADMIN) ? RolDTO.ADMIN : RolDTO.CAJERO);
            dtos.add(new UsuarioDTO(usuario.getNombre(), usuario.getCorreo(), rol));
        }
        return dtos;
    }
}
