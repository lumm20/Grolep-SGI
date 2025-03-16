package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;

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
public class ServicioUsuarios {

    /**
     * Instancia única (se inicializa de manera perezosa)
     */
    private static volatile ServicioUsuarios instancia;

    private HttpClient client;

    /**
     * Constructor privado para evitar instanciación externa
     */
    private ServicioUsuarios() {
    }

    /**
     * Método estático para obtener la única instancia
     * @return instancia unica de la clase
     */
    public static synchronized ServicioUsuarios getInstance() {
        if (instancia == null) {
            instancia = new ServicioUsuarios();
        }
        return instancia;
    }

    /**
     * metodo de ejemplo para la clase luego lo cambio
     */
    public void procesarInicioSesion(String id, String Contrasena) {

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
    }

    /**
     * Este metodo convierte de Json a DTO
     */
    //private static MyData parseJson(String json) {
    //    Gson gson = new Gson();
    //    return gson.fromJson(json, MyData.class);
    //}
}
