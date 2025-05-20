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
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import org.controlsfx.control.Notifications;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterStudentController implements Initializable {

    @FXML
    private MFXTextField txfBadge;

    @FXML
    private MFXTextField txfAverage;

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
    private MFXComboBox<Integer> cbGrade;

    @FXML
    private MFXComboBox<String> cbGroup;


    @FXML
    private MFXDatePicker dpBirthDate;


    private ServicioAlumnos servicioAlumnos;

    Mediador mediador;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInstances();
        loadStatusComboBox();
        loadLevelComboBox();
        loadGenderComboBox();
        loadSchoolarshipComboBox();
        setBatchField();
        setUpLevelComboBox();
        loadGroupComboBox();
    }

    private void loadInstances(){
        servicioAlumnos = ServicioAlumnos.getInstance();
        mediador = Mediador.getInstance();
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

    private void loadGroupComboBox(){
        List<String> groups = List.of("A", "B");
        cbGroup.setItems(FXCollections.observableArrayList(groups));
    }

    private void setUpLevelComboBox(){
        try {
            List<Integer> gradesPreescolar = List.of(2, 3);
            List<Integer> gradesPrimary = List.of(1, 2, 3, 4, 5, 6);

            cbLevel.selectedItemProperty().addListener((obs, oldLevel, newLevel) -> {
                if (newLevel == null) {
                    cbGrade.setItems(FXCollections.emptyObservableList());
                    cbGrade.setValue(null); // Limpia selección
                    return;
                }

                ObservableList<Integer> grupos;
                switch (newLevel) {
                    case Preescolar -> grupos = FXCollections.observableArrayList(gradesPreescolar);
                    case Primaria -> grupos = FXCollections.observableArrayList(gradesPrimary);
                    default -> grupos = FXCollections.emptyObservableList();
                }

                cbGrade.setItems(grupos);
                cbGrade.setValue(null);
            });
        } catch (IndexOutOfBoundsException e) {
            System.out.println("no");
        }
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



    //validacion y mas validacion jijoesu
    private void validateEmptyFields() throws IllegalArgumentException {
        StringBuilder missingFields = new StringBuilder();

        if (txfName.getText().isBlank()) missingFields.append("Nombre, ");
        if (txfAverage.getText().isBlank()) missingFields.append("Promedio, ");
        if (txfPhone.getText().isBlank()) missingFields.append("Teléfono, ");
        if (txfMail.getText().isBlank()) missingFields.append("Correo, ");
        if (txfTutor.getText().isBlank()) missingFields.append("Tutor, ");

        if (cbStatus.getValue() == null) missingFields.append("Estatus, ");
        if (cbLevel.getValue() == null) missingFields.append("Nivel, ");
        if (cbGender.getValue() == null) missingFields.append("Género, ");
        if (cbScholarship.getValue() == null) missingFields.append("Beca, ");
        if (cbGrade.getValue() == null) missingFields.append("Grado, ");
        if (cbGroup.getValue() == null || cbGroup.getValue().isBlank()) missingFields.append("Grupo, ");
        if (dpBirthDate.getValue() == null) missingFields.append("Fecha de nacimiento, ");

        if (missingFields.length() > 0) {
            String fields = missingFields.substring(0, missingFields.length() - 2);
            throw new IllegalArgumentException("Los siguientes campos son obligatorios: " + fields + ".\n");
        }
    }

    private void validateValidEntryFields() throws IllegalArgumentException {
        // Validar promedio como número válido
        try {
            Double.parseDouble(txfAverage.getText());
        } catch (NumberFormatException ne) {
            throw new IllegalArgumentException("Ingrese un número de promedio válido.");
        }

        // Validar que el número de teléfono solo contenga dígitos (mínimo 7-10 caracteres comúnmente)
        if (!txfPhone.getText().matches("\\d{7,10}")) {
            throw new IllegalArgumentException("Ingrese un número de teléfono válido (solo números, entre 7 y 15 dígitos).");
        }

        // Validar formato de correo electrónico
        String email = txfMail.getText();
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Ingrese un correo electrónico válido.");
        }
    }


    //to register the student
    @FXML
    private void registerStudent() {
        try {
            validateEmptyFields();
            validateValidEntryFields();

            // Construimos el DTO con los datos de la interfaz
            AlumnoRegistroDTO alumno = AlumnoRegistroDTO.builder()
                    .matricula(txfBadge.getText())
                    .nombre(txfName.getText())
                    .promedio(Double.parseDouble(txfAverage.getText()))
                    .grado(Integer.parseInt(cbGrade.getText()))
                    .grupo(cbGroup.getText())
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

            mediador.refreshManageStudentsScreen();

            notify("Exito","El Alumno a sido registrado con exito");

        } catch (ConexionServidorException e) {
            notify("Error","Error al registrar al alumno");
        }
        catch (IllegalArgumentException ex){
            notify("Error", ex.getMessage());
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
