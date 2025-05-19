package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.FiltroPagoDTO;

public class FiltrosCache {

    private static FiltroPagoDTO instance;


    public static FiltroPagoDTO getInstance(){
        if (instance == null){
            return new FiltroPagoDTO();
        }
        else{
            return instance;
        }
    }

    public static void clean(){
        instance.setFechaDesde(null);
        instance.setFechaHasta(null);
        instance.setMontoMinimo(null);
        instance.setMontoMaximo(null);
        instance.setUsuario(null);
        instance.setAlumno(null);
        instance.setMetodoPago(null);
    }


}
