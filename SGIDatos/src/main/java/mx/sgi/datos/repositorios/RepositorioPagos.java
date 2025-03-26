package mx.sgi.datos.repositorios;

import mx.sgi.datos.entidades.Pago;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.IConexion;
import mx.sgi.datos.interfaces.IPagosRepository;

public class RepositorioPagos implements IPagosRepository {

    IConexion conexion;

    public  RepositorioPagos(IConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public void registrarPago(Pago pago) throws RepositoryException {

    }
}
