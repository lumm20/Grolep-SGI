package mx.sgi.presentacion.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PantallaPrinciController implements Initializable {

    @FXML
    BorderPane mainBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarPantallaAlumnos();
    }

    public void cargarPantallaAlumnos() {
        try {
            System.out.println("si estoy entrando al metodo de cargar pantalla");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/PantallaPrueba.fxml"));
            AnchorPane alumnosPane = loader.load();
            mainBorderPane.setCenter(alumnosPane); // Carga dinámica al centro
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
