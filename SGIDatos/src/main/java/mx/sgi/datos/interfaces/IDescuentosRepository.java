package mx.sgi.datos.interfaces;

import mx.sgi.datos.entidades.Descuento;
import mx.sgi.datos.enumeradores.TipoDescuento;
import mx.sgi.datos.excepciones.RepositoryException;

public interface IDescuentosRepository {

    public Descuento obtenerDescuento(TipoDescuento descuento) throws RepositoryException;

}
