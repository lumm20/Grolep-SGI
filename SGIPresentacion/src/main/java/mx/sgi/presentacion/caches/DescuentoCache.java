package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.DescuentoDTO;

public class DescuentoCache {

    private static volatile DescuentoDTO instance;

    private DescuentoCache() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized DescuentoDTO getInstance() {
        if (instance == null) {
            instance = new DescuentoDTO();
        }
        return instance;
    }

    public static void setInstance(DescuentoDTO instance ){
        DescuentoCache.instance = instance;
    }

    public static void limpiarCache() {
        DescuentoCache.instance = null;
    }

}
