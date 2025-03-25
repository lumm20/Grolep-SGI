package mx.sgi.presentacion.mediador;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.vistas.TicketRegistrarDTO;
import mx.sgi.presentacion.caches.TicketRegistrarCache;
import mx.sgi.presentacion.controladores.PantallaPrincipalController;

/**
 *
 * @author skevi
 */
public class Mediador {

    /**
     * Variable que contiene la instancia unica
     */
    private static volatile Mediador instancia;

    private static Stage stageActual;

    /**
     * Constructor privado para evitar instanciación externa
     */
    private Mediador() {
    }

    /**
     * Método estático para obtener la única instancia
     * @return instancia unica de la clase
     */
    public static synchronized Mediador getInstance() {
        if (instancia == null) {
            instancia = new Mediador();
        }
        return instancia;
    }

    /**
     * Metodo encargado de mostrar la pantalla principal
     */
    public void MostrarPantallaPrincipal() {
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/PantallaPrincipal.fxml"));
            Parent root = loader.load();

            // Llamamos al controlador de la pantalla principal
            PantallaPrincipalController pantallaPrincipal = loader.getController();

            //asignamos el controlador a la instancia global
            PantallaPrincipalController.setInstancia(pantallaPrincipal);

            //eto queda pendiente poque etamo menso

            //asignamos el dashboard
            //pantallaPrincipal.setDashboard("/mx/sgi/presentacion/main/DashboardCajero.fxml");

            //asignamos la pantalla principal

            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Crear un nuevo Stage (ventana)
            Stage nuevaVentana = new Stage();
            nuevaVentana.setMaximized(true);
            nuevaVentana.setResizable(true);
            // Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

            // double maxHeight = bounds.getHeight() * 0.9;
            // double maxWidth = bounds.getWidth() * 0.9;

            // nuevaVentana.setHeight(Math.min(760, maxHeight));
            // nuevaVentana.setWidth(Math.min(1150, maxWidth));

            nuevaVentana.setTitle("GROLEP SGI v1.0");
            nuevaVentana.setScene(scene);
            stageActual = nuevaVentana;
            nuevaVentana.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla principal o el dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void abrirPantallaTicket() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/Ticket.fxml"));
            Parent root = loader.load();
            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Crear un nuevo Stage (ventana)
            Stage nuevaVentana = new Stage();
            nuevaVentana.initModality(Modality.APPLICATION_MODAL);
            nuevaVentana.initOwner(stageActual);
            nuevaVentana.setTitle("GROLEP SGI v1.0");
            nuevaVentana.setScene(scene);
            
            // (Opcional) Si deseas evitar que el usuario redimensione la ventana
            nuevaVentana.setResizable(false);
            
            // Mostrar la nueva ventana
            nuevaVentana.showAndWait();
            // nuevaVentana.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla principal de confirmacion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void refrescarPantallaPagos(){
        TicketRegistrarDTO ticket = TicketRegistrarCache.getInstance();
        PantallaPrincipalController pantallaPrincipal = PantallaPrincipalController.getInstance();

        String matricula = ticket.getAlumno().getMatricula();
        CicloEscolarDTO cicloEscolar = ticket.getCiclo();

        pantallaPrincipal.establecerCuotas(matricula, cicloEscolar);

    }



}