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
        notificaciones.enviarNotificacion("F9095", "$900", "Colegiatura, Libro", "Pedro", "$1800",
                "$450", "Tarjeta", "6442114892");

    }
    
}
