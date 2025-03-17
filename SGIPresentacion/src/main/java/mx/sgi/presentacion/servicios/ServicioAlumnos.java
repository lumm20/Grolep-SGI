package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ServicioAlumnos implements IServicioAlumnos {

    // varibale para las peticiones
    private HttpClient client;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioAlumnos() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }

    /**
     * consulta a los alumnos por su nombre, unicamente trae los primeros 10
     * alumnos que coinciden con el nombre.
     *
     * @param nombre nombre del alumno a consultar
     */
    @Override
    public void consultarAlumnos(String nombre) throws Exception{
        try {
            // Definir la URL para la solicitud HTTPS
            URI uri = URI.create("https://api.ejemplo.com/alumnos?nombre=" + nombre);

            // Crear la solicitud HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            // Realizar la solicitud y manejar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Aquí puedes procesar la respuesta si la solicitud fue exitosa
                System.out.println("Respuesta: " + response.body());
            } else {
                // Manejo de errores si la solicitud no fue exitosa
                System.err.println("Error en la solicitud: " + response.statusCode());
            }

        } catch (Exception e) {
            // Manejo de excepciones
            e.printStackTrace();
        }
    }


}
