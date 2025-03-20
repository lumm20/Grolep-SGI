package main;

import mx.itson.sgi.notificaciones.whatsapp.NotificacionesWhatsapp;

/**
 *
 * @author juventino
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NotificacionesWhatsapp notificaciones = new NotificacionesWhatsapp();
        notificaciones.enviarNotificacion("Fernando", "$900", "la colegiatura", "Andrea", "6442114892");

    }
    
}
