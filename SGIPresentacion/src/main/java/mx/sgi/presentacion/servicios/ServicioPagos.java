package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.*;
import mx.itson.sgi.dto.PagoReporteDTO;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.interfaces.IServicioPagos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;

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
                .uri(URI.create("http://localhost:8080/SGI/api/payment"))
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
                .uri(URI.create("http://localhost:8080/SGI/api/payment/tuition/all?matricula="+matricula+"&ciclo="+ciclo))
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
                .uri(URI.create("http://localhost:8080/SGI/api/payment/tuition/all-monthly?matricula="+matricula+"&ciclo="+ciclo))
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

        System.out.println("Alumno a cosultar: " + filtros.getAlumno());
        System.out.println("Usuario a consultar " + filtros.getUsuario());

        List<PagoReporteDTO> pagos = new ArrayList<>();

        PagoReporteDTO pago = PagoReporteDTO.builder()
                .montoTotal(1500.00)
                .folio("FOL123456")
                .fecha(LocalDateTime.now()) // Aunque no se serializa, puedes asignarla
                .alumno(AlumnoConsultaDTO.builder()
                        .matricula("A12345")
                        .nombre("Juan Pérez")
                        .numeroCelular("5551234567")
                        .build())
                .metodoPago(MetodosPagoDTO.Transferencia)
                .tipoDescuento("Beca académica")
                .montoDescuento(500.00)
                .usuario(new UsuarioDTO(1L, "admin", "admin@ejemplo.com","admin123", RolDTO.ADMIN))
                .build();

        pagos.add(pago);
        return pagos;
    }

}
