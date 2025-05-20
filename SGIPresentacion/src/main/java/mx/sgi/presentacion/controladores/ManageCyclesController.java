package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Duration;
import mx.itson.sgi.dto.AlumnoRegistroDTO;
import mx.itson.sgi.dto.CicloConDetallesDTO;
import mx.itson.sgi.dto.FiltroPagoDTO;
import mx.itson.sgi.dto.PagoReporteDTO;
import mx.sgi.presentacion.caches.CycleWithDetailsCache;
import mx.sgi.presentacion.caches.FiltrosCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioCicloEscolar;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManageCyclesController implements Initializable {

    @FXML
    private MFXDatePicker dpBegin;

    @FXML
    private MFXDatePicker dpEnd;

    @FXML
    private MFXButton btnSearch;

    @FXML
    private MFXButton btnResetFilters;

    @FXML
    private MFXButton btnRegisterCycle;

    @FXML
    private MFXLegacyTableView<CicloConDetallesDTO> tblCycles;

    @FXML
    private MFXButton btnPreviousPage;

    @FXML
    private MFXButton btnNextPage;

    @FXML
    private Label lblPage;

    private ServicioCicloEscolar cyclesService;

    private Mediador mediador;

    private int listSize;

    private int limit = 8;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInstances();
        setBtnSearchStyles();
        setBtnResetFiltersStyles();
        setBtnRegisterCycleStyles();
        setTblStudentsStyles();
        cellsStyles();
        loadTable();
        setBtnPreviousPageStyles();
        setBtnNextPageStyles();
        validateInitialPagination();
    }

    private void loadInstances(){
        cyclesService = ServicioCicloEscolar.getInstance();
        mediador = Mediador.getInstance();
    }

    private void setBtnSearchStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_MAGNIFY);
        icon.setIconSize(25);
        btnSearch.setGraphic(icon);
        btnSearch.setText("");

        Tooltip tooltip = new Tooltip("Buscar ciclo");
        Tooltip.install(btnSearch, tooltip);
    }

    private void setBtnResetFiltersStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_FILTER_REMOVE_OUTLINE);
        icon.setIconSize(25);
        btnResetFilters.setGraphic(icon);
        btnResetFilters.setText("");

        Tooltip tooltip = new Tooltip("Quitar todos los parametros de busqueda");
        Tooltip.install(btnResetFilters, tooltip);
    }

    private void setBtnRegisterCycleStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_ACCOUNT_PLUS);
        icon.setIconSize(25);

        btnRegisterCycle.setGraphic(icon);
        btnRegisterCycle.setText("  Registrar ciclo");
        btnRegisterCycle.setContentDisplay(ContentDisplay.LEFT);

        Tooltip tooltip = new Tooltip("Registrar nuevo ciclo");
        Tooltip.install(btnRegisterCycle, tooltip);
    }

    private void validateDates() throws Exception {
        if (dpBegin.getValue() != null || dpEnd.getValue() != null) {
            if (dpEnd.getValue().isBefore(dpBegin.getValue())){
                throw new Exception("La fecha de fin no puede ser menor a la de inicio");
            }
        }
    }

    @FXML
    private void clearFilters(){
        dpBegin.clear();
        dpEnd.clear();
        loadTable();
    }

    @FXML
    private void searchCycles(){
        try{
            validateDates();
            loadTable();
        }
        catch (Exception e){
            notify("Error", e.getMessage());
        }
    }

    @FXML
    private void openRegisterCycleScreen(){
        mediador.openRegisterCycleScreen();
    }


    TableColumn<CicloConDetallesDTO, String> colBeginDate = new TableColumn<>("Inicio");

    TableColumn<CicloConDetallesDTO, String> colEndDate = new TableColumn<>("Fin");

    TableColumn<CicloConDetallesDTO, String> colColegiatura= new TableColumn<>("Colegiatura");

    TableColumn<CicloConDetallesDTO, String> colInscription= new TableColumn<>("Inscripcion");

    TableColumn<CicloConDetallesDTO, String> colBooks= new TableColumn<>("Libros");

    TableColumn<CicloConDetallesDTO, String> colEvents = new TableColumn<>("Eventos");

    TableColumn<CicloConDetallesDTO, String> colAcademies = new TableColumn<>("Academias");

    TableColumn<CicloConDetallesDTO, String> colUniforme = new TableColumn<>("Uniforme");

    TableColumn<CicloConDetallesDTO, Void> colEditar = new TableColumn<>("Editar");

    private void setTblStudentsStyles(){
        tblCycles.setEditable(false);
        tblCycles.setFixedCellSize(60);

        //Diseable the reorder cause is a pain in the ah
        colBeginDate.setReorderable(false);
        colEndDate.setReorderable(false);
        colColegiatura.setReorderable(false);
        colInscription.setReorderable(false);
        colBooks.setReorderable(false);
        colEvents.setReorderable(false);
        colAcademies.setReorderable(false);
        colUniforme.setReorderable(false);
        colEditar.setReorderable(false);

        //Styles for headers
        colBeginDate.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colEndDate.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colColegiatura.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colInscription.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colBooks.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colEvents.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colAcademies.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colUniforme.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colEditar.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");

        tblCycles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colBeginDate.prefWidthProperty().bind(tblCycles.widthProperty().multiply(0.1));
        colEndDate.prefWidthProperty().bind(tblCycles.widthProperty().multiply(0.3));
        colColegiatura.prefWidthProperty().bind(tblCycles.widthProperty().multiply(0.2));
        colInscription.prefWidthProperty().bind(tblCycles.widthProperty().multiply(0.2));
        colBooks.prefWidthProperty().bind(tblCycles.widthProperty().multiply(0.2));
        colEvents.prefWidthProperty().bind(tblCycles.widthProperty().multiply(0.2));
        colAcademies.prefWidthProperty().bind(tblCycles.widthProperty().multiply(0.2));
        colUniforme.prefWidthProperty().bind(tblCycles.widthProperty().multiply(0.2));
        colEditar.prefWidthProperty().bind(tblCycles.widthProperty().multiply(0.2));
    }

    private void cellsStyles(){
        colBeginDate.setCellFactory(column -> {
            TableCell<CicloConDetallesDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colEndDate.setCellFactory(column -> {
            TableCell<CicloConDetallesDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colColegiatura.setCellFactory(column -> {
            TableCell<CicloConDetallesDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colInscription.setCellFactory(column -> {
            TableCell<CicloConDetallesDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colBooks.setCellFactory(column -> {
            TableCell<CicloConDetallesDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colEvents.setCellFactory(column -> {
            TableCell<CicloConDetallesDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colAcademies.setCellFactory(column -> {
            TableCell<CicloConDetallesDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colUniforme.setCellFactory(column -> {
            TableCell<CicloConDetallesDTO, String> cell = new TableCell<>() {
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

    public void loadTable() {
        System.out.println("Recargando tabla de ciclos");

        // Limpiamos columnas previas
        tblCycles.getColumns().clear();


        colBeginDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCicloEscolar().getInicio()));

        colEndDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCicloEscolar().getFin()));

        colColegiatura.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDetalleCiclo().getCuotaColegiatura())));

        colInscription.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDetalleCiclo().getCuotaInscripcion())));

        colBooks.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDetalleCiclo().getCuotaLibros())));

        colEvents.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDetalleCiclo().getCuotaEventos())));

        colAcademies.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDetalleCiclo().getCuotaAcademias())));

        colUniforme.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDetalleCiclo().getCuotaUniforme())));

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
                    CicloConDetallesDTO cycle = getTableView().getItems().get(getIndex());
                    CycleWithDetailsCache.setInstance(cycle);
                    mediador.openEditCycleScreen();
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
        tblCycles.getColumns().addAll(colBeginDate, colEndDate, colColegiatura, colInscription, colBooks, colEvents, colAcademies, colUniforme, colEditar);

        // Llenar datos
        try {
            LocalDate begin = dpBegin.getValue() != null ? dpBegin.getValue() : null;
            LocalDate end = dpEnd.getValue() != null ? dpEnd.getValue() : null;
            int offset = Integer.parseInt(lblPage.getText());

            List<CicloConDetallesDTO> cycles = cyclesService.getCompleteCycle(begin, end, limit, offset);
            listSize = cycles.size();
            tblCycles.setItems(FXCollections.observableArrayList(cycles));
        } catch (ConexionServidorException e) {
            notify("Ups!!", e.getMessage());
        }
    }


    @FXML
    private void previusPage(Event event){
        int actualPageNumber = Integer.parseInt(lblPage.getText());

        int newPageNumber = actualPageNumber - 1;
        lblPage.setText(String.valueOf(newPageNumber));

        loadTable();

        if (newPageNumber == 1) {
            btnPreviousPage.setDisable(true);
            btnNextPage.setDisable(false);
        }
    }

    @FXML
    private void nextPage(Event event){
        int actualPageNumber = Integer.parseInt(lblPage.getText());

        int newPageNumber = actualPageNumber + 1;
        lblPage.setText(String.valueOf(newPageNumber));

        loadTable();

        if (listSize < limit){
            btnNextPage.setDisable(true);
            btnPreviousPage.setDisable(false);
        }
    }

    private void validateInitialPagination(){
        if (listSize < limit){
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
