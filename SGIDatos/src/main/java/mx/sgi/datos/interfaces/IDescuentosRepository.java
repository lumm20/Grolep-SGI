package mx.sgi.datos.interfaces;

import mx.sgi.datos.enumeradores.Descuento;
import mx.sgi.datos.excepciones.RepositoryException;

public interface IDescuentosRepository {

    public Descuento obtenerDescuento(Descuento descuento) throws RepositoryException;

}
