package mx.sgi.datos.interfaces;

import mx.sgi.datos.entidades.Pago;
import mx.sgi.datos.excepciones.RepositoryException;

public interface IPagosRepository {

    public void registrarPago(Pago pago) throws RepositoryException;

}
