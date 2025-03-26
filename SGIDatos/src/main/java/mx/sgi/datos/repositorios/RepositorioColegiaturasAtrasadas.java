package mx.sgi.datos.repositorios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import mx.sgi.datos.entidades.CicloEscolar;
import mx.sgi.datos.entidades.ColegiaturaAtrasada;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.IColegiaturasAtrasadasRepository;
import mx.sgi.datos.interfaces.IConexion;

import java.util.List;

public class RepositorioColegiaturasAtrasadas implements IColegiaturasAtrasadasRepository {

    IConexion conexion;

    public  RepositorioColegiaturasAtrasadas(IConexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene las cuotas de todos los tipos de un alumno basandose en su matricula
     * asociada.
     *
     * @param matricula matricula del alumno.
     * @return lista de todos las cuotas de un alumno.
     * @throws RepositoryException en caso de excepcion en la consulta.
     */
    @Override
    public List<ColegiaturaAtrasada> obtenerColegiaturasAtrasadas(String matricula, CicloEscolar cicloEscolar) throws RepositoryException {
        try {
            EntityManager em = conexion.getEntityManager();
            return em.createQuery(
                            "SELECT c FROM ColegiaturaAtrasada c " +
                                    "WHERE c.alumno.id = (SELECT a.id FROM Alumno a WHERE a.matricula = :matricula) " +
                                    "AND c.cicloEscolar.id = :cicloEscolarId", ColegiaturaAtrasada.class)
                    .setParameter("matricula", matricula)
                    .setParameter("cicloEscolarId", cicloEscolar.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Error al obtener las colegiaturas atrasadas del alumno", e);
        }
    }


}
