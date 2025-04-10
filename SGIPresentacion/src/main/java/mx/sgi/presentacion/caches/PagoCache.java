package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.PagoDTO;

public class PagoCache {

    private static volatile PagoDTO instance;


    public static void limpiarCache() {
        PagoCache.instance = null;
    }

    private PagoCache() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized PagoDTO getInstance() {
        if (instance == null) {
            instance = new PagoDTO();
        }
        return instance;
    }

}
