package mx.sgi.presentacion.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PantallaPrinciController implements Initializable {

    @FXML
    BorderPane mainBorderPane;

    /**
     * Public global instance.
     */
    public static PantallaPrinciController mainScreen;

    /**
     * Singletone method to get the final instance.
     * @return
     */
    public static PantallaPrinciController getInstance(){
        if (mainScreen == null){
            return new PantallaPrinciController();
        }
        else{
            return mainScreen;
        }
    }

    /**
     *
     * @param mainScreen
     */
    public static void setInstance(PantallaPrinciController mainScreen){
        PantallaPrinciController.mainScreen = mainScreen;
    }

    /**
     * Initialize method to set the start requirements
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     *
     * @param fxmlRoute
     */
    public void setCenterPane(String fxmlRoute) {
        try {
            System.out.println("si estoy entrando al metodo de cargar pantalla");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRoute));
            AnchorPane central = loader.load();
            mainBorderPane.setCenter(central); // Carga dinámica al centro
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param fxmlRoute
     */
    public void setLeftPane(String fxmlRoute){
        try {
            System.out.println("si estoy entrando al metodo de cargar dashboard");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRoute));
            AnchorPane dashboard = loader.load();
            mainBorderPane.setLeft(dashboard); // Carga dinámica al centro
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows a floating notification for error cases.
     */
    private void notifyError(String message){
        Notifications.create()
                .title("Error de inicio de sesion")
                .text(message)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

}
