package mx.sgi.datos.repositorios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import mx.sgi.datos.entidades.Pago;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.IConexion;
import mx.sgi.datos.interfaces.IPagosRepository;

public class RepositorioPagos implements IPagosRepository {

    IConexion conexion;

    public  RepositorioPagos(IConexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Registra un pago dentro de la base de datos.
     *
     * @param pago
     * @throws RepositoryException
     */
    @Override
    public void registrarPago(Pago pago) throws RepositoryException {
        EntityTransaction transaction = null;
        try {
            EntityManager em = conexion.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            em.persist(pago);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RepositoryException("Error al registrar el pago", e);
        }
    }

}
