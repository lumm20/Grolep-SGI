package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.CicloEscolarDTO;

public class CicloEscolarCache {

    private static volatile CicloEscolarDTO instance;

    private CicloEscolarCache() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized CicloEscolarDTO getInstance() {
        if (instance == null) {
            instance = new CicloEscolarDTO();
        }
        return instance;
    }

    public static void setInstance(CicloEscolarDTO instance ){
        CicloEscolarCache.instance = instance;
    }

    public static void limpiarCache() {
        CicloEscolarCache.instance = null;
    }

}
