package mx.itson.sgi.data_access.utilities;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.UsuarioRepository;

@Service
public class DetallesUsuarios implements UserDetailsService{

    @Autowired
    private UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByNombre(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));
        
        return new org.springframework.security.core.userdetails.User(
            usuario.getNombre(),
            usuario.getContrasena(),
            authorities
        );
    }
}
