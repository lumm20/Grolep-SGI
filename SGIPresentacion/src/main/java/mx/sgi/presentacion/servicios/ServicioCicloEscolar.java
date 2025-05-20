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

    @Override
    public List<CicloConDetallesDTO> getCompleteCycle(LocalDate begin, LocalDate end, int limit, int offset) throws ConexionServidorException {
        // Primer ciclo escolar
        CicloEscolarDTO ciclo1 = CicloEscolarDTO.builder()
                .id("24-25")
                .inicio("2024-08-01")
                .fin("2025-07-01")
                .build();

        DetalleCicloDTO detalle1 = DetalleCicloDTO.builder()
                .id(1L)
                .idCicloEscolar("24-25")
                .cuotaInscripcion(1500.0)
                .cuotaColegiatura(2500.0)
                .cuotaLibros(1200.0)
                .cuotaEventos(500.0)
                .cuotaAcademias(800.0)
                .cuotaUniforme(1000.0)
                .build();

        CicloConDetallesDTO cicloConDetalles1 = new CicloConDetallesDTO(ciclo1, detalle1);

        // Segundo ciclo escolar
        CicloEscolarDTO ciclo2 = CicloEscolarDTO.builder()
                .id("25-26")
                .inicio("2025-08-01")
                .fin("2026-07-01")
                .build();

        DetalleCicloDTO detalle2 = DetalleCicloDTO.builder()
                .id(2L)
                .idCicloEscolar("25-26")
                .cuotaInscripcion(1600.0)
                .cuotaColegiatura(2600.0)
                .cuotaLibros(1300.0)
                .cuotaEventos(550.0)
                .cuotaAcademias(850.0)
                .cuotaUniforme(1100.0)
                .build();

        CicloConDetallesDTO cicloConDetalles2 = new CicloConDetallesDTO(ciclo2, detalle2);

        // Agregamos ambos a una lista
        List<CicloConDetallesDTO> listaCiclos = new ArrayList<>();
        listaCiclos.add(cicloConDetalles1);
        listaCiclos.add(cicloConDetalles2);

        return listaCiclos;
    }

    @Override
    public void registerCycle(CicloConDetallesDTO cycle) throws ConexionServidorException {
        System.out.println("registrado");
    }


    @Override
    public void editCycle(CicloConDetallesDTO cycle) throws ConexionServidorException {
        System.out.println("editado");
    }

}
