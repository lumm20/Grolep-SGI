package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.BecaDTO;
import mx.itson.sgi.dto.DescuentoDTO;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        DescuentoDTO descuento = new DescuentoDTO("Primer Periodo", new BigDecimal("300.00"));
        DescuentoDTO descuento2 = new DescuentoDTO("Segundo Periodo", new BigDecimal("200.00"));


        BecaDTO beca = new BecaDTO("SEC", new BigDecimal("1500.00"));
        BecaDTO beca2 = new BecaDTO("DEPORTIVA", new BigDecimal("2000.00"));
        BecaDTO beca3 = new BecaDTO("CIVICA", new BigDecimal("1000.00"));

        // Creamos algunos alumnos simulados
        List<AlumnoConsultaDTO>alumnosSimulados = new ArrayList<>();
        alumnosSimulados.add(new AlumnoConsultaDTO("A001", "Juan", "Pérez", "López", "123456789", beca, descuento));
        alumnosSimulados.add(new AlumnoConsultaDTO("A002", "Kevin", "Martínez", "Gómez", "987654321", beca2, descuento2));
        alumnosSimulados.add(new AlumnoConsultaDTO("A003", "Ana", "González", "Ruiz", "112233445",  beca3, descuento));
        alumnosSimulados.add(new AlumnoConsultaDTO("A004", "Carlos", "Hernández", "Vargas", "998877665", null,  descuento));
        alumnosSimulados.add(new AlumnoConsultaDTO("A005", "Pedro", "Rodríguez", "Díaz", "556677889", null, null));
        alumnosSimulados.add(new AlumnoConsultaDTO("A006", "María", "Sánchez", "Pérez", "667788990", beca3, null));
        alumnosSimulados.add(new AlumnoConsultaDTO("A007", "Itzel", "Torres", "Ramírez", "223344556", beca2, descuento2));
        alumnosSimulados.add(new AlumnoConsultaDTO("A008", "Alondra", "Lopez", "González", "123123123", null, null));
        alumnosSimulados.add(new AlumnoConsultaDTO("A009", "Juan", "Martínez", "Sánchez", "321321321", beca3, null));
        alumnosSimulados.add(new AlumnoConsultaDTO("A010", "Laura", "Gómez", "Fernández", "445566778",  beca, descuento));

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


