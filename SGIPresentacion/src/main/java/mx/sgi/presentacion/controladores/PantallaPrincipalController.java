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
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.MetodosPagoDTO;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import mx.sgi.presentacion.interfaces.IServicioCicloEscolar;
import mx.sgi.presentacion.interfaces.IServicioCuotas;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import mx.sgi.presentacion.servicios.ServicioCicloEscolar;
import mx.sgi.presentacion.servicios.ServicioCuotas;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    Label lblAdeudoVencido;

    @FXML
    Label  lblAdeudoColegiatura;

    @FXML
    Label lblCuotaInscripcion;

    @FXML
    Label lblCuotaLibros;

    @FXML
    Label lblCuotaEventos;

    @FXML
    Label lblCuotaAcademias;

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
    ComboBox<AlumnoConsultaDTO> cmbxAlumnos;

    @FXML
    ComboBox<CicloEscolarDTO> cmbxCicloEscolar;

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

    private IServicioAlumnos servicioAlumnos;

    private IServicioCuotas  servicioCuotas;

    private IServicioCicloEscolar servicioCicloEscolar;

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

        this.servicioAlumnos = new ServicioAlumnos();

        this.servicioCuotas =  new ServicioCuotas();

        this.mediador = Mediador.getInstance();

        this.servicioCicloEscolar = new ServicioCicloEscolar();

        //establecemos los ciclos escolares en el comboBox
        establecerCiclos();

        //cargamos los metodos de pago:
        cargarMetodosDePago();

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
            verificarFormato(txfMontoVencido, lblAdeudoVencido);
        });
    }

    // Método para el campo "Monto Colegiatura"
    private void setMontoColegiaturaListener() {
        txfMontoColegiatura.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoColegiatura, lblAdeudoColegiatura);
        });
    }

    // Método para el campo "Monto Inscripción"
    private void setMontoInscripcionListener() {
        txfMontoInscripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoInscripcion, lblCuotaInscripcion);
        });
    }

    // Método para el campo "Monto Libros"
    private void setMontoLibrosListener() {
        txfMontoLibros.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoLibros, lblCuotaLibros);
        });
    }

    // Método para el campo "Monto Eventos"
    private void setMontoEventosListener() {
        txfMontoEventos.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoEventos, lblCuotaEventos);
        });
    }

    // Método para el campo "Monto Academias"
    private void setMontoAcademiasListener() {
        txfMontoAcademias.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoAcademias, lblCuotaAcademias);
        });
    }

    // Método para el campo "Monto Uniforme"
    private void setMontoUniformeListener() {
        txfMontoUniforme.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoUniforme, lblUniforme);
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


    private void cargarMetodosDePago() {
        // Definir la lista de métodos de pago
        List<String> listaMetodosPago = Arrays.asList("Efectivo", "Tarjeta", "Transferencia");  // Ajusta según los métodos de pago

        // Convertir la lista a un ObservableList
        ObservableList<String> observableList = FXCollections.observableArrayList(listaMetodosPago);

        // Establecer los items del ComboBox
        cmbxMetodoPago.setItems(observableList);
    }



    /**
     *
     */
    private void establecerCiclos(){
        try {

            List<CicloEscolarDTO> listaCiclos = servicioCicloEscolar.obtenerCiclosEscolares();

            ObservableList<CicloEscolarDTO> observableList = FXCollections.observableArrayList(listaCiclos);

            cmbxCicloEscolar.setItems(observableList);

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

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
        mediador.abrirPantallaTicket();
    }

    /**
     *
     */
    @FXML
    private void considerarColegiatura() {
        boolean considerar = cbxConsiderarColegiatura.isSelected();

        txfMontoVencido.setText(considerar ? lblAdeudoVencido.getText() : "");
        txfMontoColegiatura.setText(considerar ? lblAdeudoColegiatura.getText() : "");

        //actualizamos el total
        actualizarTotal();

    }

    /**
     * actualizamos el total
     */
    @FXML
    private void considerarTodos() {
        boolean considerar = cbxConsiderarCuotas.isSelected();

        Map<TextField, Label> campos = Map.of(
                txfMontoInscripcion, lblCuotaInscripcion,
                txfMontoLibros, lblCuotaLibros,
                txfMontoEventos, lblCuotaEventos,
                txfMontoAcademias, lblCuotaAcademias,
                txfMontoUniforme, lblUniforme
        );

        campos.forEach((campo, etiqueta) -> campo.setText(considerar ? etiqueta.getText() : ""));

        // Actualizamos el total
        actualizarTotal();

    }

    /**
     * Metodo que actualiza el campo para el total
     */
    private void actualizarTotal() {
        BigDecimal total = BigDecimal.ZERO;

        // Sumar los montos de cada campo
        total = total.add(ParseBigDecimal(txfMontoVencido));
        total = total.add(ParseBigDecimal(txfMontoColegiatura));
        total = total.add(ParseBigDecimal(txfMontoInscripcion));
        total = total.add(ParseBigDecimal(txfMontoLibros));
        total = total.add(ParseBigDecimal(txfMontoEventos));
        total = total.add(ParseBigDecimal(txfMontoAcademias));
        total = total.add(ParseBigDecimal(txfMontoUniforme));

        // Actualizar el total en la interfaz
        lblTotal.setText(total.toString());
    }

    /**
     * Método para parsear el texto a BigDecimal, devolviendo BigDecimal.ZERO si es vacío o no válido
     */
    private BigDecimal ParseBigDecimal(TextField textField) {
        try {
            return new BigDecimal(textField.getText().isEmpty() ? "0,00" : textField.getText());
        } catch (NumberFormatException e) {
            return new BigDecimal(0.00);
        }
    }

    private void verificarFormato(TextField textField, Label label) {
        try {

            // Verificamos si el texto no está vacío y es un número válido
            if (textField.getText().isEmpty()) {
                // Si el campo está vacío, puedes manejarlo de la forma que prefieras, por ejemplo:
                textField.setStyle("-fx-border-color: #000000;");
                textField.setDisable(false);
                return;  // Salir del método, ya que no queremos procesar el campo vacío
            }

            BigDecimal valorIngresado = new BigDecimal(textField.getText()); // valor del campo a evaluar
            BigDecimal valorReal = new BigDecimal(label.getText()); // valor del campo indicador del monto
            BigDecimal comparador = new BigDecimal(0); // valor a comparar

            // Comparar si el valor ingresado es menor que 0
            int comparacionMenor = valorIngresado.compareTo(comparador);
            int comparacionMayor = valorIngresado.compareTo(valorReal);

            // Si el número ingresado es negativo, aplicar estilo y deshabilitar el botón
            if (comparacionMenor < 0) {
                aplicarEstiloDeError(textField);
            }
            // Si el valor ingresado es mayor que el valor real, también aplica estilo de error
            else if (comparacionMayor > 0) {
                aplicarEstiloDeError(textField);
            }
            // Si el valor ingresado está dentro del rango esperado, aplicar estilo por defecto
            else {
                aplicarEstiloPorDefecto(textField);
                actualizarTotal();
            }
        } catch (NumberFormatException ex) {
            // Si el formato no es un número válido, aplicar el estilo de error
            aplicarEstiloDeError(textField);
        }
    }

    // Método auxiliar para aplicar estilo de error al botón
    private void aplicarEstiloDeError(TextField textField) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        btnRegistrar.setDisable(true);
    }

    // Método auxiliar para aplicar estilo por defecto al botón
    private void aplicarEstiloPorDefecto(TextField textField) {
        textField.setStyle("-fx-border-color: #000000;");
        btnRegistrar.setDisable(false);
    }


}