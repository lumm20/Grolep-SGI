package mx.sgi.presentacion.interfaces;

import mx.itson.sgi.dto.CicloEscolarDTO;

import java.util.List;

public interface IServicioCicloEscolar {

    public List<CicloEscolarDTO>obtenerCiclosEscolares() throws Exception;
    public CicloEscolarDTO obtenerCicloEscolarActual() throws Exception  ;

}
