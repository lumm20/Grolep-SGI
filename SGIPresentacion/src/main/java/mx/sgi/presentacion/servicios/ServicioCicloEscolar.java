package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.interfaces.IServicioCicloEscolar;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServicioCicloEscolar implements IServicioCicloEscolar {

    // varibale para las peticiones
    private HttpClient client;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioCicloEscolar() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }


    @Override
    public List<CicloEscolarDTO> obtenerCiclosEscolares()  throws ConexionServidorException {
        try {
            String token = UsuarioCache.getSession().getToken();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/SGI/api/fees/all-cycles"))
                    .GET()
                    .header("Authorization", "Bearer " + token)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println(response.body());
                return new Gson().fromJson(response.body(), new TypeToken<List<CicloEscolarDTO>>() {}.getType());
            } else {
                throw new ConexionServidorException("Error al obtener los ciclos escolares: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.", e);
        }
    }


    @Override
    public CicloEscolarDTO obtenerCicloEscolarActual() throws ConexionServidorException {
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/SGI/api/fees/actual-cycle"))
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                System.out.println(response.body());
                return new Gson().fromJson(response.body(), CicloEscolarDTO.class);
            }else {
                throw new ConexionServidorException("Error al obtener el ciclo escolar actual: " + response.statusCode() + " - " + response.body());
            }
        }catch (IOException | InterruptedException e) {
            throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.", e);
        }  
    }

}
