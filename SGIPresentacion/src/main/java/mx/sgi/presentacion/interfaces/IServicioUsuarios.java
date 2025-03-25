package mx.sgi.presentacion.interfaces;

import mx.itson.sgi.dto.UsuarioDTO;

public interface IServicioUsuarios {

    public UsuarioDTO obtenerUsuario(String id, String contrasena)  throws Exception;
    public UsuarioDTO login(String id, String contrasena);

}
