package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.CicloConDetallesDTO;

public class CycleWithDetailsCache {

    private static CicloConDetallesDTO instance;


    private CycleWithDetailsCache() {

    }

    public static CicloConDetallesDTO getInstance() {
        return instance;
    }

    public static void setInstance(CicloConDetallesDTO instance) {
        CycleWithDetailsCache.instance = instance;
    }
}
