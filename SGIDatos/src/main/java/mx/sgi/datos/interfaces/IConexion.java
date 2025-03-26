package mx.sgi.datos.interfaces;

import jakarta.persistence.EntityManager;

public interface IConexion {

    // Método para obtener el EntityManager
    public EntityManager getEntityManager();

    // Método para cerrar la conexión
    public void close();

}
