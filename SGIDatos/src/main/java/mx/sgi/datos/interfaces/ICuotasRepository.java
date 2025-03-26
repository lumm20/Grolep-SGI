package mx.sgi.datos.interfaces;

import mx.sgi.datos.entidades.CicloEscolar;
import mx.sgi.datos.entidades.Cuota;
import mx.sgi.datos.excepciones.RepositoryException;

import java.util.List;

public interface ICuotasRepository {

    public List<Cuota> obtenerCuotas(String matricula, CicloEscolar cicloEscolar) throws RepositoryException;

}
