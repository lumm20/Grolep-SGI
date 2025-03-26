package mx.sgi.datos.interfaces;

import mx.sgi.datos.entidades.CicloEscolar;
import mx.sgi.datos.excepciones.RepositoryException;

import java.util.List;

public interface ICicloEscolarRepository {

    public List<CicloEscolar> obtenerCiclosEscolares() throws RepositoryException;

}
