package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.BecaDTO;
import mx.itson.sgi.dto.DescuentoDTO;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;

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


    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioAlumnos() {

    }

    /**
     * consulta a la API a los alumnos por su nombre.
     *
     * @param nombre nombre del alumno a consultar
     */
    @Override
    public List<AlumnoConsultaDTO> consultarAlumnos(String nombre) throws Exception {
        return null;
    }

}


