package mx.sgi.datos.interfaces;

import mx.sgi.datos.entidades.Usuario;
import mx.sgi.datos.excepciones.RepositoryException;

public interface IUsuariosRepository {

    public Usuario obtenerUsuario(String correo) throws RepositoryException;

}
