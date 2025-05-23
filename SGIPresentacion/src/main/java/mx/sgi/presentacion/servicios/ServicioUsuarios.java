package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;

import mx.itson.sgi.dto.AuthenticationResponse;
import mx.itson.sgi.dto.RolDTO;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.interfaces.IServicioUsuarios;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    // Variable global de la clase;
    private static ServicioUsuarios instance;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioUsuarios() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }

    public synchronized static ServicioUsuarios getInstance(){
        if (instance == null){
            return new ServicioUsuarios();
        }
        else{
            return instance;
        }
    }


    /**
     * devuelve un usuario basado en su ID y su contrasena, en el servidor
     * se validara la sesion.
     */
    public UsuarioDTO obtenerUsuario(String id, String Contrasena)  throws Exception{

        return new UsuarioDTO( 1L,"Juan Pérez", "juan.perez@example.com",RolDTO.ADMIN);

    }


    /**
     *
     * @param correo
     * @param password
     * @return
     */
    public AuthenticationResponse loginSec(String correo, String password) {
       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create("http://localhost:8081/SGI/api/users/login-sec"))
               .header("Content-Type", "application/json")
               .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(new UsuarioDTO(correo, password))))
               .build();
       HttpResponse<String> response = null;
       try {
           response = client.send(request, HttpResponse.BodyHandlers.ofString());
           System.out.println(response.body());
           if(response.statusCode() == 200) {
               return new Gson().fromJson(response.body(), AuthenticationResponse.class);
           }else if(response.statusCode() == 401){
               return AuthenticationResponse.builder().error("Correo y/o contrasena incorrectos").build();
           }else if(response.statusCode() == 400){
               return AuthenticationResponse.builder().error("Debe llenar todos los campos").build();
           }
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
       return AuthenticationResponse.builder().error("¡¡Hubo un error inesperado al iniciar sesión!!").build();
    }

    /**
     * Brings the users filtrated by the name.
     *
     * @param name Name of the user.
     * @return List of UsuarioDTO matching the name.
     * @throws ConexionServidorException In case of error in the server.
     */
    public List<UsuarioDTO> getUserByName(String name) throws ConexionServidorException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/SGI/api/users"))
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<UsuarioDTO> allUsers = Arrays.asList(new Gson().fromJson(response.body(), UsuarioDTO[].class));
                return allUsers.stream()
                        .filter(u -> u.getNombre() != null && u.getNombre().toLowerCase().contains(name.toLowerCase()))
                        .collect(Collectors.toList());
            } else {
                throw new ConexionServidorException("Error al obtener usuarios: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new ConexionServidorException("Error al filtrar usuarios por nombre", e);
        }
    }

}
