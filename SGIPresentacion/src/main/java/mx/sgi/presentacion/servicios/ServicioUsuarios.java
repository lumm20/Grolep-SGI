package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;

import mx.itson.sgi.dto.RolDTO;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.interfaces.IServicioUsuarios;

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
     * devuelve un usuario basado en su ID y su contrasena, en el servidor
     * se validara la sesion.
     */
    public UsuarioDTO obtenerUsuario(String id, String Contrasena)  throws Exception{

        return new UsuarioDTO( 1L,"Juan Pérez", "juan.perez@example.com",RolDTO.ADMIN);

    }

    public UsuarioDTO login(String nombre, String password) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/SGI/api/users/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(new UsuarioDTO(nombre, password))))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), UsuarioDTO.class);
            }
            System.out.println(response.body());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
