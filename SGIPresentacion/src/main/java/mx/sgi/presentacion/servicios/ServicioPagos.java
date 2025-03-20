package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import mx.itson.sgi.dto.PagoDTO;
import mx.sgi.presentacion.interfaces.IServicioPagos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServicioPagos implements IServicioPagos {

    // varibale para las peticiones
    private HttpClient client;
    private Gson gson;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioPagos() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    @Override
    public void registrarPago(PagoDTO pago) throws Exception {
        String json = gson.toJson(pago);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("API_URL"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("Error en el registro de pago: " + response.statusCode() + " - " + response.body());
        }
    }


}
