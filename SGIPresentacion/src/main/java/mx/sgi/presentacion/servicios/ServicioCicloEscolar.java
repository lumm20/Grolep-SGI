package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.interfaces.IServicioCicloEscolar;

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
    public List<CicloEscolarDTO> obtenerCiclosEscolares() throws Exception {
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
            }
        } catch (Exception ex) {
            throw new Exception("Error al obtener los ciclos escolares", ex);
        }
        return null;
    }


    @Override
    public CicloEscolarDTO obtenerCicloEscolarActual() {
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
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;   
    }

}
