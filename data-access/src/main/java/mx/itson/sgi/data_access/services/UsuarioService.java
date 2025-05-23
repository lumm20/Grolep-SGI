package mx.itson.sgi.data_access.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mx.itson.sgi.data_access.entities.Rol;
import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.UsuarioRepository;
import mx.itson.sgi.data_access.utilities.JwtService;
import mx.itson.sgi.dto.AuthenticationResponse;
import mx.itson.sgi.dto.RolDTO;
import mx.itson.sgi.dto.UsuarioDTO;

@Service
@RequiredArgsConstructor
public class UsuarioService{
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder encoder;

    public AuthenticationResponse authenticate(UsuarioDTO usuario){
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    usuario.getCorreo(),
                    usuario.getContra()
                )
            );
            var user = repository.findByCorreo(usuario.getCorreo()).orElseThrow();
            var token = jwtService.generateToken(user);
            return AuthenticationResponse.builder().
                                            token(token).
                                            nombreUsuario(user.getNombre()).
                                            correoUsuario(user.getCorreo()).
                                            idUsuario(user.getId()).
                                            build();
        } catch (BadCredentialsException e) {
            return AuthenticationResponse.builder().token(null).build();
        }

    }

    public UsuarioDTO login(UsuarioDTO usuario){
        Optional<Usuario> optional = repository.findByNombre(usuario.getNombre());
        if(optional.isPresent()){
            // Usuario usuarioEncontrado = optional.get();
            // if (security.authenticate(usuario.getContra(), usuarioEncontrado.getContrasena())){
            //     UsuarioDTO loggedDTO = findUser(usuario.getNombre());
            //     return loggedDTO;
            // }
            return new UsuarioDTO();
        }
        return null;
    }

    public UsuarioDTO findUserById(Long id){
        Optional<Usuario> usuarioBuscado = repository.findById(id);
        
        if(usuarioBuscado.isPresent()){
            Usuario user = usuarioBuscado.get();
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(user.getId());
            return dto;
        }
        return null;
    }

    public UsuarioDTO findUser(String username){
        Optional<Usuario> usuarioBuscado = repository.findUserByNombre(username);
        
        if(usuarioBuscado.isPresent()){
            Usuario user = usuarioBuscado.get();
            RolDTO rol = (user.getRol().equals(Rol.ADMIN) ? RolDTO.ADMIN:RolDTO.CAJERO);
            System.out.println(user);
            UsuarioDTO dto = new UsuarioDTO(user.getId(),user.getNombre(), user.getCorreo(), rol);
            return dto;
        }
        return null;
    }

    public AuthenticationResponse registrarUsuario(UsuarioDTO usuario) {
        String contraHasheada = encoder.encode(usuario.getContra());
        Rol rol = (usuario.getRol().equals(RolDTO.ADMIN) ? Rol.ADMIN : Rol.CAJERO);
        Usuario user = new Usuario(usuario.getNombre(), contraHasheada, rol, usuario.getCorreo());     
        repository.save(user);
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.
                builder().
                token(token).
                build();
    }

    public List<UsuarioDTO> obtenerUsuarios(){
        List<Usuario> usuarios = repository.findUsers();
        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            RolDTO rol = (usuario.getRol().equals(Rol.ADMIN) ? RolDTO.ADMIN : RolDTO.CAJERO);
            dtos.add(new UsuarioDTO(usuario.getId(),usuario.getNombre(), usuario.getCorreo(), rol));
        }
        return dtos;
    }

}
