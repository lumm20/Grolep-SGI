package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.AlumnoRegistroDTO;
import mx.itson.sgi.dto.BecaDTO;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Objects;

import mx.itson.sgi.dto.enums.Estatus;
import mx.itson.sgi.dto.enums.Nivel;
import mx.itson.sgi.dto.enums.Genero;

public class ServicioAlumnos implements IServicioAlumnos {

    // varibale para las peticiones
    private HttpClient client;
    private Gson gson;
    private static ServicioAlumnos servicioAlumnos;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioAlumnos() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Gets the global instance of the service.
     * @return Global instance.
     */
    public synchronized static ServicioAlumnos getInstance(){
        return Objects.requireNonNullElseGet(servicioAlumnos, ServicioAlumnos::new);
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

    /**
     * Gets from the server all the showable information of a student.
     *
     * @param name Search parameter for the students with a coincident name.
     * @return List of the coincident students.
     * @throws ConexionServidorException In case of error in the server.
     */
  public List<AlumnoRegistroDTO> searchCompleteStudent(String name) throws ConexionServidorException{
      List<AlumnoRegistroDTO> alumnos = new ArrayList<>();

      AlumnoRegistroDTO alumno1 = AlumnoRegistroDTO.builder()
              .matricula("A001")
              .nombre("Juan Pérez")
              .promedio(8.5)
              .grado(3)
              .grupo("A")
              .estatus(Estatus.Activo)
              .nivel(Nivel.Primaria)
              .fechaNacimiento("2008-05-12")
              .telefono("555-1234")
              .genero(Genero.Masculino)
              .correo("juan.perez@example.com")
              .beca(null)
              .tutor("María Pérez")
              .build();

      AlumnoRegistroDTO alumno2 = AlumnoRegistroDTO.builder()
              .matricula("A002")
              .nombre("Ana Gómez")
              .promedio(9.2)
              .grado(2)
              .grupo("B")
              .estatus(Estatus.Egresado)
              .nivel(Nivel.Primaria)
              .fechaNacimiento("2010-09-23")
              .telefono("555-5678")
              .genero(Genero.Femenino)
              .correo("ana.gomez@example.com")
              .beca(new BecaDTO("Ninguno",new BigDecimal(0)))
              .tutor("Luis Gómez")
              .build();

      AlumnoRegistroDTO alumno3 = AlumnoRegistroDTO.builder()
              .matricula("A003")
              .nombre("Carlos López")
              .promedio(7.8)
              .grado(1)
              .grupo("C")
              .estatus(Estatus.Baja)
              .nivel(Nivel.Preescolar)
              .fechaNacimiento("2009-11-02")
              .telefono("555-9999")
              .genero(Genero.Femenino)
              .correo("carlos.lopez@example.com")
              .beca(new BecaDTO("Ninguno",new BigDecimal(0)))
              .tutor("Laura López")
              .build();

      alumnos.add(alumno1);
      alumnos.add(alumno2);
      alumnos.add(alumno3);

      return alumnos;
  }

}



