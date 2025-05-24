package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.FiltroPagoDTO;

public class FiltrosCache {

    private static FiltroPagoDTO instance;


    public static FiltroPagoDTO getInstance() {
        if (instance == null) {
            instance = new FiltroPagoDTO(); // ← Guarda la nueva instancia
        }
        return instance;
    }


}
