package mx.sgi.datos.repositorios;

import mx.sgi.datos.entidades.Usuario;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.IConexion;
import mx.sgi.datos.interfaces.IUsuariosRepository;

public class RepositorioUsuarios implements IUsuariosRepository {

    IConexion conexion;

    public  RepositorioUsuarios(IConexion conexion) {
        this.conexion = conexion;
    }


    @Override
    public Usuario obtenerUsuario(String correo) throws RepositoryException {
        return null;
    }
}
