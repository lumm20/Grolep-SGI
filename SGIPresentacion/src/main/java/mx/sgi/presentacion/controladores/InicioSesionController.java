package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mx.itson.sgi.dto.AuthenticationResponse;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.interfaces.IServicioUsuarios;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioUsuarios;

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
    private MFXButton btnAccess;

    @FXML
    private MFXTextField txfId;

    @FXML
    private MFXPasswordField psfPassword;

    @FXML
    ImageView imgLogoEscuela;

    private IServicioUsuarios servicioUsuarios;

    private Mediador mediador;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //iniciamos el servicio de usuarios
        this.servicioUsuarios = new ServicioUsuarios();

        //iniciamos el mediador con su instacia global
        this.mediador = Mediador.getInstance();
    }

    /**
     * Procesa el inicio de sesion
     */
    @FXML
    private void handleLogin() {
        try{
            //validamos que los datos esten correctos
            validateEntryFieldsData();

            //recolectamos los datos para el inicio de sesion
            String id = txfId.getText();
            String contrena = psfPassword.getText();

            // mandamos los datos al servicio
            AuthenticationResponse usuario = servicioUsuarios.loginSec(id, contrena);

            System.out.println(usuario.toString());

            if (usuario.getToken() != null) {
                // guardamos al usuario en la instancia global
                // UsuarioCache.setInstance(usuario);
                UsuarioCache.setSession(usuario);

                // hacemos el cambio de pantalla
                loadInitialScreen();

                // cerramos la ventana de inicio de sesion
                Stage stage = (Stage) txfId.getScene().getWindow(); // Obtener el Stage (ventana) actual
                stage.close(); // Cerrar la ventana de inicio de sesiónS

            } else {
                notifyError(usuario.getError());
            }

        }
        catch(Exception ex){
            notifyError(ex.getMessage());
        }

    }

    /**
     * Validates the data entries of this specific screen
     */
    private void validateEntryFieldsData() throws Exception{
        if (txfId.getText().isEmpty() || psfPassword.getText().isEmpty()){
            throw new Exception("Complete todos los campos porfavor");
        }
    }

    /**
     * loads the initial screen based on the rol of the successfully logged user.
     */
    private void loadInitialScreen() {
        mediador.showMainFrame();
        mediador.openAdministratorSidebar();
        mediador.openStudentsScreen();
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
