package mx.sgi.presentacion.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFrameController implements Initializable {

    @FXML
    BorderPane mainBorderPane;

    /**
     * Public global instance.
     */
    private static MainFrameController mainScreen;

    private Initializable center;

    private Initializable left;

    public static MainFrameController getInstance(){
        return mainScreen;
    }

    public static void setInstance(MainFrameController mainScreen){
        MainFrameController.mainScreen = mainScreen;
    }

    public Initializable getCenter(){
        return center;
    }

    public Initializable getLeft(){
        return left;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRoute));
            AnchorPane central = loader.load();
            center = loader.getController();
            mainBorderPane.setCenter(central);
        } catch (IOException e) {
            notifyError("Error al cargar la pantalla");
            System.out.println(fxmlRoute);
            e.printStackTrace();
        }
    }

    /**
     *
     * @param fxmlRoute
     */
    public void setLeftPane(String fxmlRoute){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRoute));
            AnchorPane dashboard = loader.load();
            left = loader.getController();
            mainBorderPane.setLeft(dashboard); // Carga dinámica al centro
        } catch (IOException e) {
            notifyError("Error al cargar la barra lateral");
            System.out.println(fxmlRoute);
            e.printStackTrace();
        }
    }

    /**
     * Shows a floating notification for error cases.
     */
    private void notifyError(String message) {
        Notifications.create()
                .title("Error de carga")
                .text(message)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

}
