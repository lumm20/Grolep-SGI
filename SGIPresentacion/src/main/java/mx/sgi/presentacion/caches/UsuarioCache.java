package mx.sgi.presentacion.caches;

public class UsuarioCache {

    private static volatile UsuarioCache instance;

    private UsuarioCache() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized UsuarioCache getInstance() {
        if (instance == null) {
            instance = new UsuarioCache();
        }
        return instance;
    }

    public static void limpiarInstancia() {
        instance = null;
    }
}

