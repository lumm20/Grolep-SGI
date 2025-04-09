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
                    .header("Authorization", "Bearer EAAOSGJzVMCwBO53Ya60cvqvzZC285bwqyc4qbsaISesCq48nsb9zO3NW4Hr6o5zSAXOet6LKcjUB7NSQyuJw8mmS9liZCjjfr2xSURHQbJZAtaZBwX2HW5KUoHmSO1nt0lYZBa5wJeU74dDViEXzVxtEgMuMxQcYYGk0UGc6MdxZBOCac5V1MwnTXzUvPOIgVKns1C2P7KXNZBrjDJDJ2AOcXanXZBv900CUx91joxqi")
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
        Map<String, Object> contenido = new HashMap<>();

        String texto = "Pago Recibido \n----------------------\nFolio del pago: " + folio + "\nAlumno: " + nombreAlumno  + "\nConceptos pagados: "
                + conceptos  + "\nMétodo de pago: " + metodoPago + "\n----------------------\nSubtotal: " + subtotal + "\nMonto total: " + monto + "\nDescuento aplicado: " + descuento;

        contenido.put("preview_url", "false");
        contenido.put("body", texto);

        mensaje.put("messaging_product", "whatsapp");
        mensaje.put("recipient_type", "individual");
        mensaje.put("to", "+52" + numero);
        mensaje.put("type", "text");
        mensaje.put("text", contenido);
        Gson gson = new Gson();

        return gson.toJson(mensaje);
    }
    
    public void enviarNotificacion(String folio, String costo, String conceptos, String nombreAlumno, String subtotal, String descuento, String metodoPago, String numero) {
        enviarPeticion(crearJSON(folio, costo, conceptos, nombreAlumno, descuento, subtotal, metodoPago, numero));
    }
}
