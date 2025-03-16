package mx.sgi.presentacion.controladores;

import javafx.geometry.Pos;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioUsuarios;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 */
public class InicioSesionController implements Initializable{

    @FXML
    private JFXButton btnAcceder;

    @FXML
    private JFXTextField txfID;

    @FXML
    private JFXPasswordField psfContrasena;


    private ServicioUsuarios servicioUsuarios;
    private Mediador mediador;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //iniciamos el servicio de usuarios con su instacia global
        this.servicioUsuarios = ServicioUsuarios.getInstance();
        this.mediador = Mediador.getInstance();
    }

    /**
     * Procesa el inicio de sesion
     */
    @FXML
    private void handleLogin() {
        try{
            //validamos que los datos esten correctos
            validarDatos();

            //recolectamos los datos para el inicio de sesion
            String id = txfID.getText();
            String contrena = psfContrasena.getText();

            //mandamos los datos al servicio
            servicioUsuarios.procesarInicioSesion(id, contrena);

            //hacemos el cambio de pantalla
            mediador.MostrarPantallaPrincipal();

        }
        catch(Exception ex){
            error(ex.getMessage());
            ex.printStackTrace();
        }

    }

    /**
     * Valida que las entradas de datos sean validas para enviar al serivicio de
     * usuarios
     */
    private void validarDatos() throws Exception{
        if (txfID.getText().isEmpty() || psfContrasena.getText().isEmpty()){
            throw new Exception("Complete todos los campos porfavor");
        }
    }

    /**
     * Muestra una notificacion en caso de error
     */
    private void error(String mensaje){
        Notifications.create()
                .title("Error de inicio de sesion")
                .text(mensaje)
                .graphic(null)
                .position(Pos.BASELINE_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

}
