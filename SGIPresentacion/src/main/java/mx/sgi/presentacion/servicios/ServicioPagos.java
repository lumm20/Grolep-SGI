package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import mx.itson.sgi.dto.PagoDTO;
import mx.sgi.presentacion.interfaces.IServicioPagos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServicioPagos implements IServicioPagos {


    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioPagos() {

    }

    @Override
    public void registrarPago(PagoDTO pago) throws Exception {

    }


}
