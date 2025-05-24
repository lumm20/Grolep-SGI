package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.*;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.interfaces.IServicioPagos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ServicioPagos implements IServicioPagos {

    private static ServicioPagos instance;
    private final HttpClient client;

    private ServicioPagos() {
        this.client = HttpClient.newHttpClient();
    }

    public static synchronized ServicioPagos getInstance(){
        if(instance == null){
            instance = new ServicioPagos();
        }
        return instance;
    }

    @Override
    public void registrarPago(PagoDTO pago)  throws ConexionServidorException{
        // System.out.println(pago);
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/payment"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().create().toJson(pago)))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("codigo: "+response.statusCode());
            if(response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("Pago registrado");
            }else {
                throw new ConexionServidorException("Error al registrar el pago: " + response.statusCode() + " - " + response.body());
            }
        }catch (IOException | InterruptedException e) {
          throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.", e);
        }
    }

    @Override
    public double obtenerTotalPagadoColegiatura(String matricula, String ciclo) throws ConexionServidorException{
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/payment/tuition/all?matricula="+matricula+"&ciclo="+ciclo))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                return Double.parseDouble(response.body());
            }else {
                throw new ConexionServidorException("Error al obtener el total pagado de colegiatura: " + response.statusCode() + " - " + response.body());
            }
        }catch (IOException | InterruptedException e) {
            throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.", e);
        }
    }

    @Override
    public long obtenerPagosDeColegiaturaDelMes(String matricula, String ciclo) {
        String token = UsuarioCache.getSession().getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/payment/tuition/all-monthly?matricula="+matricula+"&ciclo="+ciclo))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                return Long.parseLong(response.body());
            }else{
                System.out.println(response.statusCode());
                System.out.println(response.body());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0L;
    }


    /**
     *
     * @param filtros
     * @return
     * @throws ConexionServidorException
     */
    public List<PagoReporteDTO> getPaymentsForReport(FiltroPagoDTO filtros) throws ConexionServidorException {
        String token = UsuarioCache.getSession().getToken();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        System.out.println("filtros antes de json: " + filtros);
        String json = gson.toJson(filtros);
        System.out.println("filtros en json: " + json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/SGI/api/payment/filter"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("entrando a la respuesta");
                PagoReporteDTO[] pagosArray = gson.fromJson(response.body(), PagoReporteDTO[].class);
                List<PagoReporteDTO> pagos = new ArrayList<>();
                if (pagosArray != null) {
                    System.out.println("Pagos obtenidos: " + pagosArray.length);
                    for (PagoReporteDTO p : pagosArray) {
                        System.out.println("Alumno: " + p.getAlumno().getMatricula());  
                        pagos.add(p);
                    }
                }
                return pagos;
            } else {
                throw new ConexionServidorException("Error al obtener el reporte de pagos: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new ConexionServidorException("No se pudo conectar con el servidor. Por favor, intente más tarde.", e);
        }
    }

    // Adaptador para LocalDate
    static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value != null ? value.toString() : null);
        }
        @Override
        public LocalDate read(JsonReader in) throws IOException {
            String str = in.nextString();
            return (str != null && !str.isEmpty()) ? LocalDate.parse(str) : null;
        }
    }

    // Adaptador para LocalDateTime
    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            out.value(value != null ? value.toString() : null);
        }
        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            String str = in.nextString();
            return (str != null && !str.isEmpty()) ? LocalDateTime.parse(str) : null;
        }
    }
}
