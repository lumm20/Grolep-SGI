package mx.sgi.presentacion.servicios;

import com.google.gson.Gson;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.interfaces.IServicioPagos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    public void registrarPago(PagoDTO pago)  throws Exception{
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
            if(response.statusCode() == 200) {
                System.out.println("Pago registrado");
            }else{
                System.out.println(response.statusCode());
                System.out.println(response.body());

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public double obtenerTotalPagadoColegiatura(String matricula, String ciclo) {
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
            }else{
                System.out.println(response.statusCode());
                System.out.println(response.body());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0.0;
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


}
