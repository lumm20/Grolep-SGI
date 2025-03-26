package mx.sgi.datos.repositorios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import mx.sgi.datos.entidades.Descuento;
import mx.sgi.datos.enumeradores.TipoDescuento;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.IConexion;
import mx.sgi.datos.interfaces.IDescuentosRepository;

public class RepositorioDescuentos implements IDescuentosRepository {

    IConexion conexion;

    public  RepositorioDescuentos(IConexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Busca el descuento por su tipo de descuento.
     *
     * @param tipo enumerador con el tipo de descuento
     * @return tipo de descuento aplicable
     * @throws RepositoryException en caso de excepcion en la consulta.
     */
    @Override
    public Descuento obtenerDescuento(TipoDescuento tipo) throws RepositoryException {
        try {
            EntityManager em = conexion.getEntityManager();
            TypedQuery<Descuento> query = em.createQuery(
                    "SELECT d FROM Descuento d WHERE d.tipoDescuento = :tipo", Descuento.class);
            query.setParameter("tipo", tipo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // O podrías lanzar una excepción personalizada si lo prefieres
        } catch (Exception e) {
            throw new RepositoryException("Error al obtener el descuento", e);
        }
    }

}
