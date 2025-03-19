package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ServicioAlumnos implements IServicioAlumnos {

    // varibale para las peticiones
    private HttpClient client;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioAlumnos() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }

    /**
     * consulta a los alumnos por su nombre, unicamente trae los primeros 10
     * alumnos que coinciden con el nombre.
     *
     * @param nombre nombre del alumno a consultar
     */
    @Override
    public List<AlumnoConsultaDTO> consultarAlumnos(String nombre) throws Exception{

        // Creamos algunos alumnos simulados
        List<AlumnoConsultaDTO>alumnosSimulados = new ArrayList<>();
        alumnosSimulados.add(new AlumnoConsultaDTO("A001", "Juan", "Pérez", "López", "123456789"));
        alumnosSimulados.add(new AlumnoConsultaDTO("A002", "Kevin", "Martínez", "Gómez", "987654321"));
        alumnosSimulados.add(new AlumnoConsultaDTO("A003", "Ana", "González", "Ruiz", "112233445"));
        alumnosSimulados.add(new AlumnoConsultaDTO("A004", "Carlos", "Hernández", "Vargas", "998877665"));
        alumnosSimulados.add(new AlumnoConsultaDTO("A005", "Pedro", "Rodríguez", "Díaz", "556677889"));
        alumnosSimulados.add(new AlumnoConsultaDTO("A006", "María", "Sánchez", "Pérez", "667788990"));
        alumnosSimulados.add(new AlumnoConsultaDTO("A007", "Itzel", "Torres", "Ramírez", "223344556"));
        alumnosSimulados.add(new AlumnoConsultaDTO("A008", "Alondra", "Lopez", "González", "123123123"));
        alumnosSimulados.add(new AlumnoConsultaDTO("A009", "Juan", "Martínez", "Sánchez", "321321321"));
        alumnosSimulados.add(new AlumnoConsultaDTO("A010", "Laura", "Gómez", "Fernández", "445566778"));
        // Añadir más si se necesita

        if (nombre == null || nombre.isEmpty()) {

        }

        // Filtrar los alumnos que contienen el nombre en su campo "nombres"
        return alumnosSimulados.stream()
                .filter(alumno -> alumno.getNombres().toLowerCase().contains(nombre.toLowerCase()))
                .limit(10) // Limitar a 10 alumnos
                .collect(Collectors.toList());
    }


}


