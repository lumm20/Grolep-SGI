package mx.sgi.presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mx.sgi.presentacion.mediador.Mediador;

import java.net.URL;
import java.util.ResourceBundle;

public class PantallaPrincipalController implements Initializable {

    @FXML
    JFXButton btnPagos;

    @FXML
    JFXButton btnRegistros;

    @FXML
    JFXButton btnUniformes;

    @FXML
    JFXButton btnCuenta;

    @FXML
    VBox boxCajero;

    //de aqui en adelante se declaran los componentes que representan datos

    @FXML
    Label lblMontoVencidos;

    @FXML
    Label  lblAdeudoColegiatura;

    @FXML
    Label lblCuotaInscripcion;

    @FXML
    Label lblCuotaILibros;

    @FXML
    Label lblCuotaIEventos;

    @FXML
    Label lblCuotaIAcademias;

    @FXML
    Label lblUniforme;

    @FXML
    Label lblTotal;


    //declaracion de los checkBoxes

    @FXML
    CheckBox cbxConsiderarColegiatura;

    @FXML
    CheckBox cbxConsiderarCuotas;


    //declaracion de los botones

    @FXML
    JFXButton btnDetalles;

    @FXML
    JFXButton btnRegistrar;


    //Declaracion de los ComboBox

    @FXML
    ComboBox<String> cmbxAlumnos;

    @FXML
    ComboBox<String> cmbxCicloEscolar;

    @FXML
    ComboBox<String> cmbxMetodoPago;


    //de aqui en adelante se declaran los componentes de entradas

    @FXML
    TextField txfMontoVencido;

    @FXML
    TextField txfMontoColegiatura;

    @FXML
    TextField txfMontoInscripcion;

    @FXML
    TextField txfMontoLibros;

    @FXML
    TextField txfMontoEventos;

    @FXML
    TextField txfMontoAcademias;

    @FXML
    TextField txfMontoUniforme;


    @FXML
    private BorderPane borderPane;

    private Mediador mediador;

    /**
     * Instancia estatica del controlador
     */
    private static PantallaPrincipalController instancia;

    /**
     * Metodo para obtener la instancia unica de la clase
     * @return instancia unica de la clase
     */
    public static synchronized PantallaPrincipalController getInstance() {
        return instancia;
    }

    /**
     * Metodo para establecer la instancia unica de la clase
     * @param instancia instancia a establecer
     */
    public static synchronized void setInstancia(PantallaPrincipalController  instancia){
        PantallaPrincipalController.instancia = instancia;
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.mediador = Mediador.getInstance();

        ObservableList<String> opciones = FXCollections.observableArrayList(
                "Opción 1", "Opción 2", "Opción 3", "Opción 4"
        );

        cmbxCicloEscolar.setItems(opciones);

        //hacemos set de todos los listeners para cada campo
        setMontoVencidoListener();
        setMontoColegiaturaListener();
        setMontoInscripcionListener();
        setMontoLibrosListener();
        setMontoEventosListener();
        setMontoAcademiasListener();
        setMontoUniformeListener();

        //hacemos los set para los combo box
        setAlumnosComboBoxListener();

    }

    /**
     *
     *
    public void setDashboard(String direccion) {
        try {
            // Cargar el archivo FXML del Dashboard
            FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource(direccion));

            // Obtener el controlador del dashboard
            DashboardCajeroController dashboardControler = dashboardLoader.getController();

            System.out.println("si llegue hasta aca");

            // Obtener el VBox (o cualquier contenedor) del dashboard
            VBox dashboard = dashboardControler.getDashboard();

            if (dashboard != null) {
                System.out.println("si tenemos dashboard");
            }

            // Asignar el VBox del dashboard a la región izquierda del BorderPane

        } catch (Exception e) {
            System.err.println("Error al cargar el dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
    */

    //de aqui en adelante comienzan los listeners para validaciones

    // Método para el campo "Monto Vencido"
    private void setMontoVencidoListener() {
        txfMontoVencido.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Monto Vencido: " + newValue);
            // Agregar la lógica para manejar el cambio en "Monto Vencido"
        });
    }

    // Método para el campo "Monto Colegiatura"
    private void setMontoColegiaturaListener() {
        txfMontoColegiatura.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Monto Colegiatura: " + newValue);
            // Agregar la lógica para manejar el cambio en "Monto Colegiatura"
        });
    }

    // Método para el campo "Monto Inscripción"
    private void setMontoInscripcionListener() {
        txfMontoInscripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Monto Inscripción: " + newValue);
            // Agregar la lógica para manejar el cambio en "Monto Inscripción"
        });
    }

    // Método para el campo "Monto Libros"
    private void setMontoLibrosListener() {
        txfMontoLibros.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Monto Libros: " + newValue);
            // Agregar la lógica para manejar el cambio en "Monto Libros"
        });
    }

    // Método para el campo "Monto Eventos"
    private void setMontoEventosListener() {
        txfMontoEventos.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Monto Eventos: " + newValue);
            // Agregar la lógica para manejar el cambio en "Monto Eventos"
        });
    }

    // Método para el campo "Monto Academias"
    private void setMontoAcademiasListener() {
        txfMontoAcademias.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Monto Academias: " + newValue);
            // Agregar la lógica para manejar el cambio en "Monto Academias"
        });
    }

    // Método para el campo "Monto Uniforme"
    private void setMontoUniformeListener() {
        txfMontoUniforme.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Monto Uniforme: " + newValue);
            // Agregar la lógica para manejar el cambio en "Monto Uniforme"
        });
    }


    //de aqui en adelante comienzan los listeners para los combo box

    // Método para el ComboBox "Alumnos"
    private void setAlumnosComboBoxListener() {
        cmbxAlumnos.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Alumno seleccionado: " + newValue);
            // Agregar la lógica para manejar el cambio en "Alumnos"
        });
    }

    //ahora de aquí en adelante van los metodos para el procesamiento del pago

    /**
     * aqui se consultaran las cuotas correspondientes al alumno que haya sido seleccionado
     */
    public void consultarCuotas() {
        //pendiente
    }

    /**
     * Recolecta la informacion para el pago y la pasa al ticket al igual que abre
     * la pantalla del ticket
     */
    @FXML
    public void registrarPago(){

    }

    //
    @FXML
    private void considerarColegiatura(){

    }

   @FXML
    private void considerarTodos(){
        //por implementar
    }

    //muestra la pantalla de detalles de adeudo
    @FXML
    private void mostrarPantallaDetalles(){
        mediador.abrirPantallaTicket();
    }

}