package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.util.Duration;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.FiltroPagoDTO;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.caches.FiltrosCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import mx.sgi.presentacion.servicios.ServicioUsuarios;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReportFiltersController implements Initializable {

    @FXML
    MFXComboBox<AlumnoConsultaDTO> cbStudent;

    @FXML
    MFXTextField txfStudent;

    @FXML
    MFXTextField txfMinimum;

    @FXML
    MFXTextField txfMaximum;

    @FXML
    MFXComboBox<UsuarioDTO> cbCashier;

    @FXML
    MFXTextField txfCashier;



    ServicioUsuarios usersService;

    ServicioAlumnos studentsService;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInstances();
        setUpStudentsComboBox();
        setUpUsersComboBox();
        setPreChosenOptions();
    }



    private void loadInstances(){
        usersService = ServicioUsuarios.getInstance();
        studentsService = ServicioAlumnos.getInstance();
    }

    private  void setUpStudentsComboBox() {
        PauseTransition pause = new PauseTransition(Duration.millis(500));

        pause.setOnFinished(event -> {
            // Acción cuando el usuario ha dejado de escribir
            updateStudentOptions();
        });

        // Detectar cuando el usuario escribe
        txfStudent.textProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(txfCashier.getText());
            // Desactivar el ComboBox mientras se escribe
            // Cancelar cualquier temporizador anterior (si el usuario sigue escribiendo)
            pause.stop();
            // Reiniciar el temporizador
            pause.playFromStart();
        });
    }

    private void updateStudentOptions() {
        String name = cbStudent.getText(); // Obtener texto del editor del ComboBox
        List<AlumnoConsultaDTO> students = studentsService.buscarAlumnos(name);

        ObservableList<AlumnoConsultaDTO> newStudents = FXCollections.observableArrayList(students);
        cbStudent.setItems(newStudents);
    }

    private  void setUpUsersComboBox() {
        PauseTransition pause = new PauseTransition(Duration.millis(500));

        pause.setOnFinished(event -> {
            // Acción cuando el usuario ha dejado de escribir
            updateUserOptions();
            cbCashier.show();
        });

        txfCashier.textProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(txfCashier.getText());
            // Desactivar el ComboBox mientras se escribe
            // Cancelar cualquier temporizador anterior (si el usuario sigue escribiendo)
            pause.stop();
            // Reiniciar el temporizador
            pause.playFromStart();
        });

        cbCashier.setOnMouseClicked(e->{
            String name = cbCashier.getSelectedItem().getNombre();
            txfCashier.setText(name);
        });
    }

    private void updateUserOptions() {
        try {
            System.out.println("si estoy actualizando");
            String name = txfCashier.getText();
            List<UsuarioDTO> users = usersService.getUserByName(name);

            ObservableList<UsuarioDTO> newUsers = FXCollections.observableArrayList(users);
            cbCashier.setItems(newUsers);
        }
        catch (ConexionServidorException e){
            System.out.println(e);
        }
    }

    private void setPreChosenOptions(){
        FiltroPagoDTO filters = FiltrosCache.getInstance();

        if (filters.getMontoMinimo() != null){ txfMinimum.setText(String.valueOf(filters.getMontoMaximo()));}
        if (filters.getMontoMaximo() != null){ txfMaximum.setText(String.valueOf(filters.getMontoMinimo()));}

        Platform.runLater(() -> {
            if (filters.getAlumno() != null){cbStudent.setValue(filters.getAlumno());}
            if (filters.getUsuario() != null){cbCashier.setValue(filters.getUsuario());}
        });
    }


    @FXML
    private void closeScreen(){
        FiltroPagoDTO filters = FiltrosCache.getInstance();

        filters.setAlumno(cbStudent.getValue());
        filters.setMontoMinimo(Double.parseDouble(txfMinimum.getText()));
        filters.setMontoMaximo(Double.parseDouble(txfMaximum.getText()));
        filters.setUsuario(cbCashier.getValue());

        System.out.println(filters);

        Stage stage = (Stage) txfMinimum.getScene().getWindow();
        stage.close();
    }


}
