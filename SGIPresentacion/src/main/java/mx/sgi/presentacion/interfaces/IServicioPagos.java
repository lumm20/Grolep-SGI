package mx.sgi.presentacion.interfaces;

import mx.itson.sgi.dto.PagoDTO;

public interface IServicioPagos {

    public void registrarPago(PagoDTO pago)  throws Exception;
    public double obtenerTotalPagadoColegiatura(String matricula,String ciclo);

}
