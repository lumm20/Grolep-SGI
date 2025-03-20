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
                    .uri(new URI("https://graph.facebook.com/v13.0/<PHONE_ID>/messages"))
                    .header("Authorization", "Bearer EAAOSGJzVMCwBO5lLlsiJ7pwebh6F5GqMwDxujBqGESJ3XqZCWxYj3s7D0gcP5OtjgnedlS8kyfu6X2BhEn7W9Goe8yA0cuW1e2p7K1XcCZBtUsRR3bkmrbLPPNINMpjvrXZA7JLggOdCC2KFB2PUnDCLrl21QbTuZAzZB6ZBVSul0K4J2CqclfMLOVeW4o76qYXuYuPXx3BB9DsNQ68UNTAXA17pOQu72vvBuSHHRR")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = http.send(request, BodyHandlers.ofString());
            logger.log(Level.INFO, response.body());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    public String crearJSON(String nombre, String costo, String concepto, String nombreAlumno, String numero) {
        Map<String, Object> mensaje = new HashMap<>();
        
        Map<String, Object> template = new HashMap<>();
        Map<String, Object> language = new HashMap<>();
        
        ArrayList<Map<String, String>> parameters = new ArrayList<>();
        Map<String, String> nombreMap = new HashMap<>();
        nombreMap.put("type", "text");
        nombreMap.put("text", nombre);
        Map<String, String> costoMap = new HashMap<>();
        costoMap.put("type", "text");
        costoMap.put("text", costo);
        Map<String, String> conceptoMap = new HashMap<>();
        conceptoMap.put("type", "text");
        conceptoMap.put("text", concepto);
        Map<String, String> nombreAlumnoMap = new HashMap<>();
        nombreAlumnoMap.put("type", "text");
        nombreAlumnoMap.put("text", nombreAlumno);
        parameters.add(nombreMap);
        parameters.add(costoMap);
        parameters.add(conceptoMap);
        parameters.add(nombreAlumnoMap);

        language.put("code", "es_MX");
        template.put("name", "confirmacion_de_pago");
        template.put("language", language);
        template.put("parameeters", parameters);

        mensaje.put("messaging_product", "whatsapp");
        mensaje.put("recipient_type", "individual");
        mensaje.put("to", "+52" + numero);
        mensaje.put("type", "whatsapp");
        mensaje.put("template", template);
        Gson gson = new Gson();

        return gson.toJson(mensaje);
    }
    
    public void enviarNotificacion(String nombre, String costo, String concepto, String nombreAlumno, String numero) {
        enviarPeticion(crearJSON(nombre, costo, concepto, nombreAlumno, numero));
    }
}
