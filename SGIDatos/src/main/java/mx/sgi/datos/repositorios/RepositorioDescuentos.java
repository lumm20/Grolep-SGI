package mx.sgi.datos.repositorios;

import mx.sgi.datos.enumeradores.Descuento;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.IConexion;
import mx.sgi.datos.interfaces.IDescuentosRepository;

public class RepositorioDescuentos implements IDescuentosRepository {

    IConexion conexion;

    public  RepositorioDescuentos(IConexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Busca el descuento por su tipo de descuento.
     *
     * @param descuento enumerador con el tipo de descuento
     * @return tipo de descuento aplicable
     * @throws RepositoryException en caso de excepcion en la consulta.
     */
    @Override
    public Descuento obtenerDescuento(Descuento descuento) throws RepositoryException {
        return null;
    }
}
