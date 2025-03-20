package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.UsuarioDTO;

public class UsuarioCache {

    private static volatile UsuarioDTO instance;

    private UsuarioCache() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized UsuarioDTO getInstance() {
        if (instance == null) {
            instance = new UsuarioDTO();
        }
        return instance;
    }

    public static void setInstance(UsuarioDTO usuario){
        UsuarioCache.instance = usuario;
    }

    public static void limpiarInstancia() {
        instance = null;
    }
}

