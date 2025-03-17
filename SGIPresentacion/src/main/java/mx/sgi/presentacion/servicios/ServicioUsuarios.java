package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import mx.sgi.presentacion.interfaces.IServicioUsuarios;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Esta clase se encarga de iniciar conectar con servicios externos para
 * la obtencion de los datos relacionado con los usuarios, la llegada de
 * los datos puede provenir de diferentes fuentes y el controlador no
 * se da cuenta.
 *
 * @author skevi
 */
public class ServicioUsuarios implements IServicioUsuarios {

    // varibale para las peticiones
    private HttpClient client;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioUsuarios() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }


    /**
     * metodo de ejemplo para la clase luego lo cambio
     */
    public UsuarioDTO obtenerUsuario(String id, String Contrasena)  throws Exception{

        // Crear una solicitud GET
        //HttpRequest request = HttpRequest.newBuilder()
        //        .uri(URI.create("ejemplo"))
        //        .build();

        // Enviar la solicitud y obtener la respuesta
        //client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        //        .thenApply(HttpResponse::body)
        //        .thenApply(ServicioUsuarios::parseJson)  // Aquí puedes convertir el JSON en objetos Java
        //        .thenAccept(System.out::println)  // Imprimir los resultados
        //        .join();

        System.out.println("este es el id" + id + "este contraseña" + Contrasena);
        return null;
    }

    /**
     * Este metodo convierte de Json a DTO
     */
    //private static MyData parseJson(String json) {
    //    Gson gson = new Gson();
    //    return gson.fromJson(json, MyData.class);
    //}
}
