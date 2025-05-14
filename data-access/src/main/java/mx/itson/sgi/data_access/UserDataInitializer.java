package mx.itson.sgi.data_access;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.itson.sgi.data_access.entities.Rol;
import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.UsuarioRepository;

@Service
public class UserDataInitializer {

    @Autowired
	private UsuarioRepository userRepository;

    @Transactional(readOnly = true)
    private Usuario buscarUsuario(String correo){
        Optional<Usuario> usuario = userRepository.findByCorreo(correo);
        if(usuario.isPresent()){
            return usuario.get();
        }
        return null;
    }

    
    @Transactional
    protected Usuario cargarUsuarios(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("juan");
        String encodedPass1 = encoder.encode("admin123");
        usuario1.setContrasena(encodedPass1);
        usuario1.setRol(Rol.ADMIN);
        usuario1.setCorreo("admin@escuela.edu");
        userRepository.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("luisa");
        String encodedPass2 = encoder.encode("lumm20");
        usuario2.setContrasena(encodedPass2);
        usuario2.setRol(Rol.CAJERO);
        usuario2.setCorreo("lum@escuela.com");
        usuario2 = userRepository.save(usuario2);
        
        Usuario usuario3 = new Usuario();
        usuario3.setNombre("maria");
        String encodedPass3 = encoder.encode("dir789");
        usuario3.setContrasena(encodedPass3);
        usuario3.setRol(Rol.ADMIN);
        usuario3.setCorreo("directora@escuela.edu");
        userRepository.save(usuario3);
        
        return usuario2;
    }
}
