package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import mx.itson.sgi.dto.AlumnoRegistroDTO;
import mx.sgi.presentacion.caches.AlumnoEditarCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageStudentController implements Initializable {

    @FXML
    private MFXTextField txfStudentSearch;

    @FXML
    private MFXButton btnSearch;

    @FXML
    private MFXButton btnRegisterStudent;

    @FXML
    private MFXLegacyTableView<AlumnoRegistroDTO> tblStudents;

    @FXML
    private MFXButton btnPreviousPage;

    @FXML
    private MFXButton btnNextPage;

    @FXML
    private Label lblPage;

    private int listSize;

    //instance of the service.
    private ServicioAlumnos servicioAlumnos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        starClassInstances();
        searchUserByNameByPressingEnterSetUp();
        setBtnSearchStyles();
        setRegisterStudentButtonStyles();
        setTblStudentsStyles();
        cellsStyles();
        loadTable();
        setBtnPreviousPageStyles();
        setBtnNextPageStyles();
        validateInitialPagination();
    }

    /**
     * Declares all the class required instances.
     */
    private void starClassInstances(){
        servicioAlumnos = ServicioAlumnos.getInstance();
    }

    /**
     * Set the styles for the search button.
     */
    private void setBtnSearchStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_MAGNIFY);
        icon.setIconSize(25);

        btnSearch.setGraphic(icon);
        btnSearch.setText("");

        Tooltip tooltip = new Tooltip("Buscar");
        Tooltip.install(btnSearch, tooltip);
    }

    /**
     * Set the styles for the register button.
     */
    private void setRegisterStudentButtonStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_ACCOUNT_PLUS);
        icon.setIconSize(25);

        btnRegisterStudent.setGraphic(icon);
        btnRegisterStudent.setText("  Registrar Alumno");
        btnRegisterStudent.setContentDisplay(ContentDisplay.LEFT);

        Tooltip tooltip = new Tooltip("Registrar nuevo alumno");
        Tooltip.install(btnRegisterStudent, tooltip);
    }

    @FXML
    private void searchUserByName() {
        if(!txfStudentSearch.getText().isEmpty()){
            loadTable();
        }
        else{
            notify("Erro al buscar", "Porfavor escriba un nombre antes de buscar");
        }
    }

    @FXML
    private void searchUserByNameByPressingEnterSetUp(){
        txfStudentSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if(!txfStudentSearch.getText().isEmpty()){
                    loadTable();
                }
                else{
                    notify("Erro al buscar", "Porfavor escriba un nombre antes de buscar");
                }
            }
        });
    }

    @FXML
    private void openRegisterUserScreen(){
        Mediador mediador = Mediador.getInstance();
        mediador.openRegisterUserScreen();
    }

    private void openEditUserScreen(AlumnoRegistroDTO alumnoRegistroDTO){
        AlumnoEditarCache.setInstance(alumnoRegistroDTO);

        Mediador mediador = Mediador.getInstance();
        mediador.openEditUserScreen();
    }


    //Table methods and declarations

    //Column for badge number
    TableColumn<AlumnoRegistroDTO, String> colMatricula = new TableColumn<>("Matrícula");

    //Column for name
    TableColumn<AlumnoRegistroDTO, String> colNombre = new TableColumn<>("Nombre");

    //Column for cellphone
    TableColumn<AlumnoRegistroDTO, String> colTelefono = new TableColumn<>("Telefono");

    //Column for mail
    TableColumn<AlumnoRegistroDTO, String> colCorreo = new TableColumn<>("Correo");

    //Column for status
    TableColumn<AlumnoRegistroDTO, String> colEstatus = new TableColumn<>("Estatus");

    //Column for nothing
    TableColumn<AlumnoRegistroDTO, String> colPromedio = new TableColumn<>("Promedio");

    //Column for the editing button
    TableColumn<AlumnoRegistroDTO, Void> colEditar = new TableColumn<>("Editar");


    /**
     * Set the styles for the register button.
     */
    private void setTblStudentsStyles(){
        tblStudents.setEditable(false);
        tblStudents.setFixedCellSize(60);

        //desabilitamos el reordenamiento
        colMatricula.setReorderable(false);
        colNombre.setReorderable(false);
        colTelefono.setReorderable(false);
        colCorreo.setReorderable(false);
        colEstatus.setReorderable(false);
        colPromedio.setReorderable(false);
        colEditar.setReorderable(false);

        //le damos estilos a los encabezados
        colMatricula.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colNombre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colTelefono.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colCorreo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colEstatus.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colPromedio.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colEditar.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");

        tblStudents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colMatricula.prefWidthProperty().bind(tblStudents.widthProperty().multiply(0.1));
        colNombre.prefWidthProperty().bind(tblStudents.widthProperty().multiply(0.3));
        colTelefono.prefWidthProperty().bind(tblStudents.widthProperty().multiply(0.2));
        colCorreo.prefWidthProperty().bind(tblStudents.widthProperty().multiply(0.2));
        colEstatus.prefWidthProperty().bind(tblStudents.widthProperty().multiply(0.2));
        colPromedio.prefWidthProperty().bind(tblStudents.widthProperty().multiply(0.2));
        colEditar.prefWidthProperty().bind(tblStudents.widthProperty().multiply(0.2));
    }

    /**
     * Set the styles for each cell.
     */
    private void cellsStyles(){
        colMatricula.setCellFactory(column -> {
            TableCell<AlumnoRegistroDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colNombre.setCellFactory(column -> {
            TableCell<AlumnoRegistroDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colTelefono.setCellFactory(column -> {
            TableCell<AlumnoRegistroDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colCorreo.setCellFactory(column -> {
            TableCell<AlumnoRegistroDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colEstatus.setCellFactory(column -> {
            TableCell<AlumnoRegistroDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colPromedio.setCellFactory(column -> {
            TableCell<AlumnoRegistroDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

    }

    /**
     * Sets the gotten data from the service in the table and ads the edit
     * button for each row.
     */
    public void loadTable() {

        // Limpiamos columnas previas
        tblStudents.getColumns().clear();


        colMatricula.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMatricula()));

        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

        colTelefono.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefono()));

        colCorreo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCorreo()));

        colEstatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstatus().toString()));

        colPromedio.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPromedio())));

        colEditar.setCellFactory(param -> new TableCell<>() {
            private final MFXButton btn = new MFXButton("");

            {
                FontIcon icon = new FontIcon(MaterialDesign.MDI_PENCIL);
                icon.setIconSize(18);
                btn.setGraphic(icon);
                btn.setStyle("-fx-background-color:  #f4fa67;" +
                             "-fx-background-radius: 20px;" +
                             "-fx-cursor: hand;" +
                             "-fx-scale-x: 1.2;" +
                             "-fx-scale-y: 1.1;");
                btn.setOnAction(event -> {
                    AlumnoRegistroDTO alumno = getTableView().getItems().get(getIndex());
                    openEditUserScreen(alumno);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        // Agregar columnas a la tabla
        tblStudents.getColumns().addAll(colMatricula, colNombre, colTelefono, colCorreo, colEstatus, colPromedio, colEditar);

        // Llenar datos
        try {
            String name = txfStudentSearch.getText();
            int offset = Integer.parseInt(lblPage.getText());
            List<AlumnoRegistroDTO> alumnos = servicioAlumnos.searchCompleteStudent(name, 9, offset);
            listSize = alumnos.size();
            tblStudents.setItems(FXCollections.observableArrayList(alumnos));
        } catch (ConexionServidorException e) {
            notify("Ups!!", e.getMessage());
        }

    }



    //pagination methods

    @FXML
    private void previusPage(Event event){
        int actualPageNumber = Integer.parseInt(lblPage.getText());

        String newPageNumber = String.valueOf(actualPageNumber - 1);
        lblPage.setText(newPageNumber);

        loadTable();

        if (newPageNumber.equalsIgnoreCase("1")){
            btnPreviousPage.setDisable(false);
            btnNextPage.setDisable(true);
        }
    }

    @FXML
    private void nextPage(Event event){
        int actualPageNumber = Integer.parseInt(lblPage.getText());

        String newPageNumber = String.valueOf(actualPageNumber + 1);
        lblPage.setText(newPageNumber);

        loadTable();

        if (listSize < 9){
            btnNextPage.setDisable(true);
            btnPreviousPage.setDisable(false);
        }
    }

    private void validateInitialPagination(){
        if (listSize < 9){
            btnNextPage.setDisable(true);
        }
        btnPreviousPage.setDisable(true);
    }

    private void setBtnPreviousPageStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_CHEVRON_LEFT);
        icon.setIconSize(20);
        btnPreviousPage.setGraphic(icon);

        btnPreviousPage.setText("");
        Tooltip tooltip = new Tooltip("Pagina anterior");
        Tooltip.install(btnPreviousPage, tooltip);
    }

    private void setBtnNextPageStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_CHEVRON_RIGHT);
        icon.setIconSize(20);
        btnNextPage.setGraphic(icon);

        btnNextPage.setText("");
        Tooltip tooltip = new Tooltip("Pagina siguiente");
        Tooltip.install(btnNextPage, tooltip);
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
