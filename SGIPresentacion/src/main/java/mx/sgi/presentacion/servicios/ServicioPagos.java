package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.PagoDTO;
import mx.sgi.presentacion.interfaces.IServicioPagos;

import java.net.http.HttpClient;

public class ServicioPagos implements IServicioPagos {

    // varibale para las peticiones
    private HttpClient client;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioPagos() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public void registrarPago(PagoDTO pago)  throws Exception{
        System.out.println(pago.toString());
    }


}
