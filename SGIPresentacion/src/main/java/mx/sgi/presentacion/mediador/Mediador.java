package mx.sgi.presentacion.mediador;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author skevi
 */
public class Mediador {

    /**
     * Variable que contiene la instancia unica
     */
    private static volatile Mediador instancia;

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
    public void MostrarPantallaPrincipal(){
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/hello-view.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Crear un nuevo Stage (ventana)
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(scene);
            nuevaVentana.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}