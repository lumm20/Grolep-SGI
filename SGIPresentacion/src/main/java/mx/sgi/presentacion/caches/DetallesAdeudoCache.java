package mx.sgi.presentacion.caches;

import java.util.ArrayList;
import java.util.List;

import mx.itson.sgi.dto.DetalleAdeudoDTO;

public class DetallesAdeudoCache {

    private static volatile DetallesAdeudoCache instance = null;
    private static volatile List<DetalleAdeudoDTO> detalles;

    private DetallesAdeudoCache(){
        detalles = new ArrayList<>();
    }

    public static synchronized DetallesAdeudoCache getInstance(){
        if(instance == null){
            instance = new DetallesAdeudoCache();
        }
        return instance;
    }

    public List<DetalleAdeudoDTO> getDetalles(){
        return detalles;
    }

    public void setDetalles(List<DetalleAdeudoDTO> detallesNuevo){
        detalles = detallesNuevo;
    }


}
