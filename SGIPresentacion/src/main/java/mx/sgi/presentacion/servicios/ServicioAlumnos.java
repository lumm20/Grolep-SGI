package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.BecaDTO;
import mx.itson.sgi.dto.DescuentoDTO;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ServicioAlumnos implements IServicioAlumnos {

    // varibale para las peticiones
    private HttpClient client;
    private Gson gson;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioAlumnos() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * consulta a la API a los alumnos por su nombre.
     *
     * @param nombre nombre del alumno a consultar
     */
    @Override
    public List<AlumnoConsultaDTO> consultarAlumnos(String nombre) throws Exception {
        String url = "API_URL" + nombre;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), new TypeToken<List<AlumnoConsultaDTO>>() {}.getType());
        } else {
            throw new Exception("Error en la consulta: " + response.statusCode() + " - " + response.body());
        }
    }

}


