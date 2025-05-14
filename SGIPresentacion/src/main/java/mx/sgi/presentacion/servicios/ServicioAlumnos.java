package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.io.IOException;

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

    public List<AlumnoConsultaDTO> buscarAlumnos(String nombre){
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/SGI/api/students?matricula=" + nombre))
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                System.out.println(response.body());
                return new Gson().fromJson(response.body(), new TypeToken<List<AlumnoConsultaDTO>>(){}.getType());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;       
        
    }

    /**
     * consulta a la API a los alumnos por su nombre.
     *
     * @param nombre nombre del alumno a consultar
     */
    @Override
    public List<AlumnoConsultaDTO> consultarAlumnos(String nombre) throws ConexionServidorException {
    try {
        String url = "api/student?" + nombre;
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), new TypeToken<List<AlumnoConsultaDTO>>() {}.getType());
        } else {
                throw new ConexionServidorException("Error en la consulta: " + response.statusCode() + " - " + response.body());
        }
    } catch (IOException | InterruptedException e) {
        throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.", e);
    }
  }
}



