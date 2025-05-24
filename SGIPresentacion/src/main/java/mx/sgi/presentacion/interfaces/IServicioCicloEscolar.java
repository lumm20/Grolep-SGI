package mx.sgi.presentacion.interfaces;

import mx.itson.sgi.dto.CicloConDetallesDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.sgi.presentacion.excepciones.ConexionServidorException;

import java.time.LocalDate;
import java.util.List;

public interface IServicioCicloEscolar {

    List<CicloEscolarDTO>obtenerCiclosEscolares() throws Exception;
    CicloEscolarDTO obtenerCicloEscolarActual() throws Exception  ;
    List<CicloConDetallesDTO>getCompleteCycle(LocalDate begin, LocalDate end, int limit, int offset) throws ConexionServidorException;
    void registerCycle(CicloConDetallesDTO cycle) throws ConexionServidorException;
    void editCycle(CicloConDetallesDTO cycle) throws ConexionServidorException;
}
