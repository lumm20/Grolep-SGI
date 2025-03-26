package mx.sgi.datos.repositorios;

import mx.sgi.datos.entidades.CicloEscolar;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.ICicloEscolarRepository;
import mx.sgi.datos.interfaces.IConexion;

import java.util.List;

public class RepositorioCiclosEscolares implements ICicloEscolarRepository {

    IConexion conexion;

    public  RepositorioCiclosEscolares(IConexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene los ultimos dos ciclos escolares registrados.
     *
     * @throws RepositoryException en caso de error en la consulta.
     */
    @Override
    public List<CicloEscolar> obtenerCiclosEscolares() throws RepositoryException {
        try {
            return conexion.getEntityManager()
                    .createQuery("SELECT c FROM CicloEscolar c ORDER BY c.inicio DESC", CicloEscolar.class)
                    .setMaxResults(2)
                    .getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Error al obtener los últimos ciclos escolares", e);
        }
    }

}
