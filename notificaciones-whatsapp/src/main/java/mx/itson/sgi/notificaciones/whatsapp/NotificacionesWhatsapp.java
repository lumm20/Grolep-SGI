package mx.itson.sgi.notificaciones.whatsapp;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juventino
 */
public class NotificacionesWhatsapp {

    private static Logger logger = Logger.getLogger(NotificacionesWhatsapp.class.toString());

    public void enviarPeticion(String json) {
        try (HttpClient http = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://graph.facebook.com/v22.0/561402587064064/messages"))
                    .header("Authorization", "Bearer EAAOSGJzVMCwBO0gGMZBzCrLQjvyC4eS3tQKBMsg7hTzZB3Kla98YDoNhhRINlZAUolghWy5ZCtYCNCZCVUggYaSHj6IX5wuC9QULsZBVNcodth9z4TdPaYbKFuPiiG25vbKgU4CJ8SZAIZChFk4jqyk2m12jJwmGPjLbBap6lvR60BECZAq5pTroXvXCZBlztEuV8xCHs9WJTX0JyUHs4Ynm08ahj1VHZBVZAWiFtlgZD")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = http.send(request, BodyHandlers.ofString());

            logger.log(Level.INFO, json);
            logger.log(Level.INFO, response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public String crearJSON(String folio, String monto, String conceptos, String nombreAlumno, String descuento, String subtotal, String metodoPago, String numero) {
        Map<String, Object> mensaje = new HashMap<>();

        Map<String, Object> template = new HashMap<>();
        Map<String, Object> language = new HashMap<>();

        ArrayList<Map<String, Object>> components = new ArrayList<>();

        // Llena información para mensajes
        ArrayList<Map<String, String>> parameters = new ArrayList<>();
        Map<String, String> folioMap = new HashMap<>();
        folioMap.put("type", "text");
        folioMap.put("text", folio);
        Map<String, String> nombreAlumnoMap = new HashMap<>();
        nombreAlumnoMap.put("type", "text");
        nombreAlumnoMap.put("text", nombreAlumno);
        Map<String, String> conceptoMap = new HashMap<>();
        conceptoMap.put("type", "text");
        conceptoMap.put("text", conceptos);
        Map<String, String> metodoMap = new HashMap<>();
        metodoMap.put("type", "text");
        metodoMap.put("text", metodoPago);
        Map<String, String> subtotalMap = new HashMap<>();
        subtotalMap.put("type", "text");
        subtotalMap.put("text", subtotal);
        Map<String, String> montoMap = new HashMap<>();
        montoMap.put("type", "text");
        montoMap.put("text", monto);
        Map<String, String> descuentoMap = new HashMap<>();
        descuentoMap.put("type", "text");
        descuentoMap.put("text", descuento);
        parameters.add(folioMap);
        parameters.add(nombreAlumnoMap);
        parameters.add(conceptoMap);
        parameters.add(metodoMap);
        parameters.add(subtotalMap);
        parameters.add(montoMap);
        parameters.add(descuentoMap);

        // Configuración de template
        Map<String, Object> body = new HashMap<>();
        body.put("type", "body");
        body.put("parameters", parameters);
        components.add(body);
        language.put("code", "es_MX");
        template.put("name", "confirmacion_de_pago");
        template.put("language", language);
        template.put("components", components);

        mensaje.put("messaging_product", "whatsapp");
        mensaje.put("recipient_type", "individual");
        mensaje.put("to", "+52" + numero);
        mensaje.put("type", "template");
        mensaje.put("template", template);
        Gson gson = new Gson();

        return gson.toJson(mensaje);
    }

    public void enviarNotificacion(String folio, String costo, String conceptos, String nombreAlumno, String subtotal, String descuento, String metodoPago, String numero) {
        enviarPeticion(crearJSON(folio, costo, conceptos, nombreAlumno, descuento, subtotal, metodoPago, numero));
    }
}