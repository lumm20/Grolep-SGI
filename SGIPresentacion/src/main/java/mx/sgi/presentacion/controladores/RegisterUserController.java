package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.util.StringConverter;
import mx.itson.sgi.dto.AlumnoRegistroDTO;
import mx.itson.sgi.dto.BecaDTO;
import mx.itson.sgi.dto.enums.Estatus;
import mx.itson.sgi.dto.enums.Genero;
import mx.itson.sgi.dto.enums.Nivel;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import org.controlsfx.control.Notifications;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterUserController implements Initializable {

    @FXML
    private MFXTextField txfBadge;

    @FXML
    private MFXTextField txfAverage;

    @FXML
    private MFXTextField txfGrade;

    @FXML
    private MFXTextField txfGroup;

    @FXML
    private MFXTextField txfName;

    @FXML
    private MFXTextField txfPhone;

    @FXML
    private MFXTextField txfMail;

    @FXML
    private MFXTextField txfTutor;


    @FXML
    private MFXComboBox<Estatus> cbStatus;

    @FXML
    private MFXComboBox<Nivel> cbLevel;

    @FXML
    private MFXComboBox<Genero> cbGender;

    @FXML
    private MFXComboBox<BecaDTO> cbScholarship;


    @FXML
    private MFXDatePicker dpBirthDate;


    private ServicioAlumnos servicioAlumnos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInstances();
        loadStatusComboBox();
        loadLevelComboBox();
        loadGenderComboBox();
        loadSchoolarshipComboBox();
        setBatchField();
    }

    private void loadInstances(){
        servicioAlumnos = ServicioAlumnos.getInstance();
    }

    private void loadStatusComboBox(){
        cbStatus.setItems(FXCollections.observableArrayList(Estatus.values()));
    }

    private void loadLevelComboBox(){
        cbLevel.setItems(FXCollections.observableArrayList(Nivel.values()));
    }

    private void loadGenderComboBox(){
        cbGender.setItems(FXCollections.observableArrayList(Genero.values()));
    }

    private void loadSchoolarshipComboBox() {
        BecaDTO beca1 = new BecaDTO("DEPORTIVA", new BigDecimal("20.0"));
        BecaDTO beca2 = new BecaDTO("CIVICA", new BigDecimal("30.0"));
        BecaDTO beca3 = new BecaDTO("SEC", new BigDecimal("20.0"));
        BecaDTO beca4 = new BecaDTO("NINGUNA", new BigDecimal("0.0"));

        ObservableList<BecaDTO> scholarships = FXCollections.observableArrayList(beca1, beca2, beca3, beca4);
        cbScholarship.setItems(scholarships);

        // (Opcional) Mostrar solo el nombre en el combo box
        cbScholarship.setConverter(new StringConverter<>() {
            @Override
            public String toString(BecaDTO beca) {
                return beca != null ? beca.getTipo() : "";
            }

            @Override
            public BecaDTO fromString(String string) {
                return scholarships.stream()
                        .filter(b -> b.getTipo().equalsIgnoreCase(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        cbScholarship.setOnMouseClicked(e ->{
            cbScholarship.show();
        });
    }


    public static String generateBatchNumber() {
        Random random = new Random();
        LocalDate fecha = LocalDate.now();

        int random1 = random.nextInt(90) + 10;
        int random2 = random.nextInt(90) + 10;
        int random3 = random.nextInt(90) + 10;

        String anio = String.valueOf(fecha.getYear());
        String mes = String.format("%02d", fecha.getMonthValue());
        String dia = String.format("%02d", fecha.getDayOfMonth());

        return "A" + random1 + anio + random2 + mes + random3 + dia;
    }


    private void setBatchField(){
        txfBadge.setText(generateBatchNumber());
        txfBadge.setDisable(true);
    }


    @FXML
    private void registerStudent() {
        try {
            // Construimos el DTO con los datos de la interfaz
            AlumnoRegistroDTO alumno = AlumnoRegistroDTO.builder()
                    .matricula(txfBadge.getText())
                    .nombre(txfName.getText())
                    .promedio(Double.parseDouble(txfAverage.getText()))
                    .grado(Integer.parseInt(txfGrade.getText()))
                    .grupo(txfGroup.getText())
                    .estatus(cbStatus.getValue())
                    .nivel(cbLevel.getValue())
                    .fechaNacimiento(dpBirthDate.getValue().toString()) // Asegúrate del formato
                    .telefono(txfPhone.getText())
                    .genero(cbGender.getValue())
                    .correo(txfMail.getText())
                    .beca(cbScholarship.getValue())
                    .tutor(txfTutor.getText())
                    .build();

            // Aquí puedes llamar a tu servicio para guardar el alumno
            servicioAlumnos.registerStudent(alumno);

            notify("Exito","El Alumno a sido registrado con exito");

        } catch (ConexionServidorException e) {
            notify("Error","Error al registrar al alumno");
        }
    }

    private void notify(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

}
