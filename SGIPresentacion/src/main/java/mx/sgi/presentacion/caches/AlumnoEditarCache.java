package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.AlumnoRegistroDTO;

public class AlumnoEditarCache {

    private static AlumnoRegistroDTO alumnoRegistroDTO;

    public static AlumnoRegistroDTO getInstance(){
        return  alumnoRegistroDTO;
    }

    public static void setInstance(AlumnoRegistroDTO alumnoRegistroDTO){
        AlumnoEditarCache.alumnoRegistroDTO = alumnoRegistroDTO;
    }

}
