package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.CuotasDTO;
import mx.itson.sgi.dto.DetalleAdeudoDTO;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.interfaces.IServicioCuotas;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class ServicioCuotas implements IServicioCuotas {

    private static ServicioCuotas instance;
    private final HttpClient client;

    private ServicioCuotas() {
        this.client = HttpClient.newHttpClient();
    }

    public static synchronized ServicioCuotas getInstance(){
        if(instance == null){
            instance = new ServicioCuotas();
        }
        return instance;
    }

    @Override
    public CuotasDTO obtenerCuotasAlumno(String matricula, String cicloEscolar) throws ConexionServidorException {
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder().
                            uri(URI.create("http://localhost:8080/SGI/api/fees?matricula="+matricula+"&ciclo="
                            + cicloEscolar)).
                            GET().
                            header("Authorization", "Bearer " + token).
                            build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                System.out.println(response.body());
                return new Gson().fromJson(response.body(), CuotasDTO.class);
            } else {
                throw new ConexionServidorException("Error al obtener las cuotas del alumno: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
        throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.", e
        );   
     }
        // Simulación de valores de adeudos
        // Double adeudoVencido = 500.00; // Total de adeudos vencidos
        // Double adeudoColegiatura = 1000.00;
        // Double adeudoInscripcion = 500.00;
        // Double adeudoLibros = 700.00;
        // Double adeudoEventos = 1200.00;
        // Double adeudoAcademias = 400.00;
        // Double adeudoUniformes = 800.00;

        // // Se crea un solo objeto CuotaDTO con todos los adeudos en sus respectivos campos
        // CuotasDTO cuota = new CuotasDTO(adeudoVencido, adeudoColegiatura, adeudoInscripcion,
        //         adeudoLibros, adeudoEventos, adeudoAcademias, adeudoUniformes);

        // // Se devuelve una lista con un solo elemento, ya que ahora CuotaDTO almacena todos los valores en un solo objeto
        // return cuota;
    }


    /**
     * Metodo que obtiene las colegiaturas atrasadas de un alumno, solo se pueden
     * obtener tres colegiaturas atrasadas.
     *
     * @param matricula matricula del alumno
     * @param cicloEscolar ciclo escolar en el que se encuentran las colegiaturas
     */
    @Override
    public List<ColegiaturaAtrasadaDTO> obtenerColegiaturasAtrasadas(String matricula, CicloEscolarDTO cicloEscolar) throws Exception {
        // Simulación de la lógica para obtener las colegiaturas atrasadas (por ejemplo, con una API o base de datos)
        List<ColegiaturaAtrasadaDTO> colegiaturasAtrasadas = new ArrayList<>();

        // Aquí agregarías la lógica real para consultar las colegiaturas atrasadas
        // Usamos un ejemplo con datos simulados
        // Agregamos tres colegiaturas atrasadas simuladas
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(LocalDate.of(2024, 1, 15), new BigDecimal("1000.00"), new BigDecimal("500.00")));
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(LocalDate.of(2024, 2, 15), new BigDecimal("700.00"), new BigDecimal("800")));
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(LocalDate.of(2024, 3, 15), new BigDecimal("300.00"), new BigDecimal("1200.00")));

        return colegiaturasAtrasadas;
    }

    @Override
    public List<DetalleAdeudoDTO> obtenerDetallesAdeudosColegiatura(String matricula, String cicloEscolar){
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder().
                            uri(URI.create("http://localhost:8080/SGI/api/fees/debit-details?matricula="+matricula+"&ciclo="+cicloEscolar)).
                            GET().
                            header("Authorization", "Bearer " + token).
                            build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                System.out.println(response.body());
                return new Gson().fromJson(response.body(), new TypeToken<List<DetalleAdeudoDTO>>() {}.getType());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
