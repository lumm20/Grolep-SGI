package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import mx.sgi.presentacion.interfaces.IServicioUsuarios;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

/**
 * Esta clase se encarga de iniciar conectar con servicios externos para
 * la obtencion de los datos relacionado con los usuarios, la llegada de
 * los datos puede provenir de diferentes fuentes y el controlador no
 * se da cuenta.
 *
 * @author skevi
 */
public class ServicioUsuarios implements IServicioUsuarios {



    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioUsuarios() {

    }


    /**
     * devuelve un usuario basado en su ID y su contrasena, en el servidor
     * se validara la sesion.
     */
    public UsuarioDTO obtenerUsuario(String id, String Contrasena)  throws Exception{

        String uuid = "3f22c5f2-5c73-41a4-9677-3d4f6c7d1018";
        return new UsuarioDTO(UUID.fromString(uuid), "Juan Pérez", "juan.perez@example.com");

    }


}
