package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.PagoDTO;

public class AlumnoCache {

    public static volatile AlumnoConsultaDTO  instance;

    public static void limpiarAlumno() {
        AlumnoCache.instance = null;
    }

    private AlumnoCache() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized AlumnoConsultaDTO getInstance() {
        if (instance == null) {
            instance = new AlumnoConsultaDTO();
        }
        return instance;
    }

    public static void setInstance(AlumnoConsultaDTO instance) {
        AlumnoCache.instance = instance;
    }

}
