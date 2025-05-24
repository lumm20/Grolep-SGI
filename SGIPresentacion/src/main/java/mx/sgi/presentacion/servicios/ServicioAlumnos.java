package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
     * 
     * @return Global instance.
     */
    public synchronized static ServicioAlumnos getInstance() {
        return Objects.requireNonNullElseGet(servicioAlumnos, ServicioAlumnos::new);
    }

    public List<AlumnoConsultaDTO> buscarAlumnos(String nombre) throws ConexionServidorException {
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/students?matricula=" + nombre))
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println(response.body());
                return new Gson().fromJson(response.body(), new TypeToken<List<AlumnoConsultaDTO>>() {
                }.getType());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Gets from the server all the showable information of a student.
     *
     * @param name Search parameter for the students with a coincident name.
     * @return List of the coincident students.
     * @throws ConexionServidorException In case of error in the server.
     */
    public List<AlumnoRegistroDTO> searchCompleteStudent(String name, int limit, int offset)
            throws ConexionServidorException {
        String token = UsuarioCache.getSession().getToken();
        try {
            String encodedName = java.net.URLEncoder.encode(name, java.nio.charset.StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/SGI/api/students/byName/" + encodedName + "?offset=" + offset + "&limit=" + limit))
                    .GET()
                    .header("Authorization", "Bearer " + token)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), new TypeToken<List<AlumnoRegistroDTO>>() {
                }.getType());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

        // List<AlumnoRegistroDTO> alumnos = new ArrayList<>();

        // AlumnoRegistroDTO alumno1 = AlumnoRegistroDTO.builder()
        //         .matricula("A001")
        //         .nombre("Juan Pérez")
        //         .promedio(8.5)
        //         .grado(3)
        //         .grupo("A")
        //         .estatus(Estatus.Activo)
        //         .nivel(Nivel.Primaria)
        //         .fechaNacimiento("2008-05-12")
        //         .telefono("555-1234")
        //         .genero(Genero.Masculino)
        //         .correo("juan.perez@example.com")
        //         .beca(new BecaDTO("NINGUNA", new BigDecimal("0.0")))
        //         .tutor("María Pérez")
        //         .build();

        // AlumnoRegistroDTO alumno2 = AlumnoRegistroDTO.builder()
        //         .matricula("A002")
        //         .nombre("Ana Gómez")
        //         .promedio(9.2)
        //         .grado(2)
        //         .grupo("B")
        //         .estatus(Estatus.Egresado)
        //         .nivel(Nivel.Primaria)
        //         .fechaNacimiento("2010-09-23")
        //         .telefono("555-5678")
        //         .genero(Genero.Femenino)
        //         .correo("ana.gomez@example.com")
        //         .beca(new BecaDTO("CIVICA", new BigDecimal("30.0")))
        //         .tutor("Luis Gómez")
        //         .build();

        // AlumnoRegistroDTO alumno3 = AlumnoRegistroDTO.builder()
        //         .matricula("A003")
        //         .nombre("Carlos López")
        //         .promedio(7.8)
        //         .grado(1)
        //         .grupo("C")
        //         .estatus(Estatus.Baja)
        //         .nivel(Nivel.Preescolar)
        //         .fechaNacimiento("2009-11-02")
        //         .telefono("555-9999")
        //         .genero(Genero.Femenino)
        //         .correo("carlos.lopez@example.com")
        //         .beca(new BecaDTO("DEPORTIVA", new BigDecimal("20.0")))
        //         .tutor("Laura López")
        //         .build();

        // alumnos.add(alumno1);
        // alumnos.add(alumno2);
        // alumnos.add(alumno3);

        // return alumnos;
    }


     public List<AlumnoRegistroDTO> searchAllCompleteStudents(int limit, int offset)
            throws ConexionServidorException {
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/students/allStudents/page?offset="+offset+"&limit="+limit))
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), new TypeToken<List<AlumnoRegistroDTO>>() {
                }.getType());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void  registerStudent(AlumnoRegistroDTO alumno) throws ConexionServidorException {
        String token = UsuarioCache.getSession().getToken();
        String json = new Gson().toJson(alumno);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/students"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                AlumnoRegistroDTO alumnoNuevo = new Gson().fromJson(response.body(),AlumnoRegistroDTO.class);
                System.out.println(alumnoNuevo);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editStudent(AlumnoRegistroDTO alumno) throws ConexionServidorException {
        System.out.println("el alumno a editar es: "+alumno);
        String token = UsuarioCache.getSession().getToken();
        String json = new Gson().toJson(alumno);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/students"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body: " + response.body());
            if (response.statusCode() == 200) {
                AlumnoRegistroDTO alumnoNuevo = new Gson().fromJson(response.body(),AlumnoRegistroDTO.class);
                System.out.println(alumnoNuevo);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
