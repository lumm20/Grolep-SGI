package mx.sgi.datos.repositorios;

import jakarta.persistence.EntityManager;
import mx.sgi.datos.entidades.CicloEscolar;
import mx.sgi.datos.entidades.Cuota;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.IConexion;
import mx.sgi.datos.interfaces.ICuotasRepository;

import java.util.List;

public class RepositorioCuotas implements ICuotasRepository {

    IConexion conexion;

    public  RepositorioCuotas(IConexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene las cuotas pertenecientes a un alumno y un ciclo escolar.
     *
     * @param matricula matricula del alumno.
     * @param cicloEscolar ciclo escolar a obtener las cuotas.
     * @return lista de todas las cuotas pertencientes a la matricula y el ciclo escolar.
     * @throws RepositoryException excepcion en caso de error en la consulta.
     */
    @Override
    public List<Cuota> obtenerCuotas(String matricula, CicloEscolar cicloEscolar) throws RepositoryException {
        try {
            EntityManager em = conexion.getEntityManager();
            return em.createQuery(
                            "SELECT c FROM Cuota c " +
                                    "WHERE c.alumno.id = (SELECT a.id FROM Alumno a WHERE a.matricula = :matricula) " +
                                    "AND c.cicloEscolar.id = :cicloEscolarId", Cuota.class)
                    .setParameter("matricula", matricula)
                    .setParameter("cicloEscolarId", cicloEscolar.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Error al obtener las cuotas del alumno", e);
        }
    }

}
