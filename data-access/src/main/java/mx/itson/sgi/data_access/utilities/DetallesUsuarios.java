package mx.itson.sgi.data_access.utilities;


import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = repository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("User not found " + correo));
        return usuario;
    }
}
