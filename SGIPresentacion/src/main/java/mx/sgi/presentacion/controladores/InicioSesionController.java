package mx.sgi.presentacion.controladores;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.interfaces.IServicioUsuarios;
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

        //iniciamos el servicio de usuarios con su instacia global
        this.mediador = Mediador.getInstance();

        //cargarIconos();

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
            // UsuarioDTO usuario = servicioUsuarios.obtenerUsuario(id, contrena);
            UsuarioDTO usuario = servicioUsuarios.login(id, contrena);

            System.out.println(usuario.toString());

            if(usuario != null){
                //guardamos al usuario en la instancia global
                UsuarioCache.setInstance(usuario);
    
                //hacemos el cambio de pantalla
                mediador.MostrarPantallaPrincipal();
                
    
                //cerramos la ventana de inicio de sesion
                Stage stage = (Stage) txfID.getScene().getWindow(); // Obtener el Stage (ventana) actual
                stage.close(); // Cerrar la ventana de inicio de sesiónS
                
            }

        }
        catch(Exception ex){
            error(ex.getMessage());
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
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

    private void cargarIconos(){
        try {
            Image image = new Image(getClass().getResource("/mx/sgi/presentacion/iconos/logo.png").toExternalForm());
            imgLogoEscuela = new ImageView(image);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
