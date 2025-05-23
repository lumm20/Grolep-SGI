package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloConDetallesDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.DetalleCicloDTO;
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

    private static ServicioCicloEscolar instance;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioCicloEscolar() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }


    public static ServicioCicloEscolar getInstance(){
        if (instance == null){
            instance = new ServicioCicloEscolar();
        }
        return instance;
    }


    @Override
    public List<CicloEscolarDTO> obtenerCiclosEscolares()  throws ConexionServidorException {
        try {
            String token = UsuarioCache.getSession().getToken();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/SGI/api/fees/all-cycles"))
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
                .uri(URI.create("http://localhost:8081/SGI/api/fees/actual-cycle"))
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

  @Override
    public List<CicloConDetallesDTO> getCompleteCycle(LocalDate begin, LocalDate end, int limit, int offset)
            throws ConexionServidorException {
        String token = null;
        if (UsuarioCache.getSession() != null) {
            token = UsuarioCache.getSession().getToken();
        }
        if (token == null || token.isEmpty()) {
            throw new ConexionServidorException("No hay sesión activa o el token es nulo. Por favor, inicie sesión.");
        }
        String url = "http://localhost:8081/SGI/api/cycles";
        if (begin != null && end != null) {
            url += "?begin=" + begin.toString() + "&end=" + end.toString();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("el cuerpo: "+response.body()+" el status: "+response.statusCode());
            if (response.statusCode() == 200) {
                System.out.println(response.body());
                List<CicloConDetallesDTO> result = new Gson().fromJson(response.body(),
                        new com.google.gson.reflect.TypeToken<List<CicloConDetallesDTO>>() {
                        }.getType());
                return result != null ? result : new ArrayList<>();
            } else {
                throw new ConexionServidorException(
                        "Error al obtener los ciclos completos: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.",
                    e);
        }
    }

    @Override
    public void registerCycle(CicloConDetallesDTO cycle) throws ConexionServidorException {
        String token = UsuarioCache.getSession().getToken();
        String json = new Gson().toJson(cycle);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/cycles"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201 && response.statusCode() != 200) {
                throw new ConexionServidorException(
                        "Error al registrar el ciclo: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.",
                    e);
        }
    }

    @Override
    public void editCycle(CicloConDetallesDTO cycle) throws ConexionServidorException {
        String token = UsuarioCache.getSession().getToken();
        String json = new Gson().toJson(cycle);
        String id = cycle.getCicloEscolar() != null ? cycle.getCicloEscolar().getIDString() : null;
        System.out.println("el id es: " + cycle.getCicloEscolar().getIDString());
        if (id == null) {
            throw new ConexionServidorException("No se puede editar: el id es nulo.");
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/cycles/" + id))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ConexionServidorException(
                        "Error al editar el ciclo: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.",
                    e);
        }
    }

}
