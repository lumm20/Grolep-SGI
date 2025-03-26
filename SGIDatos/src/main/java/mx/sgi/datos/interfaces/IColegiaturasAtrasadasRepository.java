package mx.sgi.datos.interfaces;

import mx.sgi.datos.entidades.CicloEscolar;
import mx.sgi.datos.entidades.ColegiaturaAtrasada;
import mx.sgi.datos.excepciones.RepositoryException;

import java.util.List;

public interface IColegiaturasAtrasadasRepository {

    public List<ColegiaturaAtrasada> obtenerColegiaturasAtrasadas
            (String matricula, CicloEscolar cicloEscolar) throws RepositoryException;

}
