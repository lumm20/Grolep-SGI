package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.TicketRegistrarDTO;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.interfaces.IServicioPagos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        // System.out.println(pago);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/SGI/api/payment"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().create().toJson(pago)))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                System.out.println("Pago registrado");
                System.out.println(new GsonBuilder().
                                    excludeFieldsWithoutExposeAnnotation().
                                    create().
                                    fromJson(response.body(), TicketRegistrarDTO.class));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
