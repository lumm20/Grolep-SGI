package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.TicketRegistrarDTO;

public class TicketRegistrarCache {

    private static volatile TicketRegistrarDTO instance;

    public static void limpiarCache() {
        TicketRegistrarCache.instance = null;
    }

    private TicketRegistrarCache() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized TicketRegistrarDTO getInstance() {
        if (instance == null) {
            instance = new TicketRegistrarDTO();
        }
        return instance;
    }

    public static void setInstance(TicketRegistrarDTO instance) {
        TicketRegistrarCache.instance = instance;
    }

}
