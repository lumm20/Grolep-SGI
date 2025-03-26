package mx.sgi.datos.conexiones;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import mx.sgi.datos.interfaces.IConexion;

public class Conexion implements IConexion {

    // La instancia Singleton de la clase
    private static Conexion instance;

    // La fábrica de EntityManager
    private EntityManagerFactory entityManagerFactory;

    // El EntityManager para interactuar con la base de datos
    private EntityManager entityManager;

    // Constructor privado para evitar la creación de instancias externas
    private Conexion() {
        // Crear la EntityManagerFactory usando la unidad de persistencia definida en persistence.xml
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        // Crear un EntityManager desde la fábrica
        entityManager = entityManagerFactory.createEntityManager();
    }

    // Método estático para obtener la instancia Singleton
    public static synchronized Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    // Obtener el EntityManager para operaciones CRUD
    public EntityManager getEntityManager() {
        return entityManager;
    }

    // Cerrar la conexión cuando ya no sea necesaria
    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
