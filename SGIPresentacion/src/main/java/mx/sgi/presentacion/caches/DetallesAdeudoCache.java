package mx.sgi.presentacion.caches;

import java.util.ArrayList;
import java.util.List;

import mx.itson.sgi.dto.DetalleAdeudoDTO;

public class DetallesAdeudoCache {

    private static volatile List<DetalleAdeudoDTO> detalles= new ArrayList<>();

    private DetallesAdeudoCache(){
    }

    public static synchronized List<DetalleAdeudoDTO> getDetalles(){
        if(detalles == null){
            detalles = new ArrayList<>();
        }
        return detalles;
    }

    public static void setDetalles(List<DetalleAdeudoDTO> detallesNuevo){
        detalles.clear();
        detalles.addAll(detallesNuevo);
    }


}
