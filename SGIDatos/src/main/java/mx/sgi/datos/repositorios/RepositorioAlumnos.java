package mx.sgi.datos.repositorios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mx.sgi.datos.entidades.Alumno;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.IAlumnoRepository;
import mx.sgi.datos.interfaces.IConexion;

import java.util.List;

public class RepositorioAlumnos implements IAlumnoRepository {

    IConexion conexion;

    public  RepositorioAlumnos(IConexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene a los primeros dies alumnos cuyo nombre coincida mas con
     * lo escrito en el parametro
     *
     * @param nombre nombre del alumno a buscar
     * @return Lista de alumnos que mas coincidan.
     * @throws RepositoryException en caso de excepcion en la consulta
     */
    @Override
    public List<Alumno> getAlumnosPorNombre(String nombre) throws RepositoryException {
        EntityManager em = conexion.getEntityManager();
        try {
            // Crear la consulta con JPQL
            String queryStr = "SELECT a FROM Alumno a WHERE a.nombre LIKE :nombre ORDER BY a.nombre ASC";
            TypedQuery<Alumno> query = em.createQuery(queryStr, Alumno.class);
            query.setParameter("nombre", "%" + nombre + "%"); // Usamos el LIKE para encontrar coincidencias parciales

            // Establecer un límite de resultados
            query.setMaxResults(10);

            // Ejecutar la consulta y devolver los resultados
            return query.getResultList();

        } catch (Exception e) {
            throw new RepositoryException("Error al obtener los alumnos por nombre", e);
        }
    }

}
