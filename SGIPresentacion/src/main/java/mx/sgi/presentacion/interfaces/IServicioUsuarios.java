package mx.sgi.presentacion.interfaces;

import mx.itson.sgi.dto.AuthenticationResponse;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.excepciones.ConexionServidorException;

import java.util.List;

public interface IServicioUsuarios {

    public AuthenticationResponse loginSec(String id, String contrasena);
    public List<UsuarioDTO> getUserByName(String name) throws ConexionServidorException;
}
