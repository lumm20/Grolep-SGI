package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import mx.itson.sgi.dto.DetallePagoDTO;
import mx.itson.sgi.dto.MetodosPagoDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.vistas.TicketRegistrarDTO;
import mx.sgi.presentacion.caches.TicketRegistrarCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioPagos;

import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class TicketController implements Initializable {

    @FXML
    Label lblTotal;

    @FXML
    Label lblFolio;

    @FXML
    Label lblFecha;

    @FXML
    Label lblCliente;

    @FXML
    Label lblMetodo;

    @FXML
    Label lblDescuento;

    @FXML
    Label lblMontoDescuento;

    @FXML
    Label lblSubTotal;

    @FXML
    MFXButton btnCancelar;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox conceptosContainer;
    @FXML
    private AnchorPane ticketContentPane;
    @FXML
    private Separator separadorDetalleTicket;
    @FXML
    private Separator separadorDescuento;
    @FXML
    private MFXButton btnConfirmar;
    
    @FXML
    private AnchorPane footerPane;
    private NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.of("es", "MX"));

    ServicioPagos servicioPagos;

    Mediador mediador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.servicioPagos = ServicioPagos.getInstance();
        this.mediador = Mediador.getInstance();
        setBtnCancelarStyles();

        if (conceptosContainer != null) {
            conceptosContainer.getChildren().clear();
        }
        cargarInfoPago();
    }


    public void generarConceptos(Map<String, Double> conceptos) {
        conceptosContainer.getChildren().clear();
        
        for (Map.Entry<String, Double> concepto : conceptos.entrySet()) {
            double valor = concepto.getValue();
            if (valor > 0) {
                agregarConcepto(concepto.getKey(), valor);
            }
        }
        ajustarCampoDescuento();
    }
    
    
    private void agregarConcepto(String nombreConcepto, double valor) {
        HBox conceptoHBox = new HBox();
        conceptoHBox.setSpacing(10);
        conceptoHBox.setPrefHeight(32.0);
        
        Label lblNombreConcepto = new Label("Monto para " + nombreConcepto.toLowerCase() + ":");
        lblNombreConcepto.setFont(Font.font("System", FontWeight.BOLD, 14.0));
        
        HBox.setHgrow(lblNombreConcepto, Priority.ALWAYS);
        
        Label lblValorConcepto = new Label(formatoMoneda.format(valor));
        lblValorConcepto.setFont(Font.font("System", 14.0));
        
        conceptoHBox.getChildren().addAll(lblNombreConcepto, lblValorConcepto);        
        conceptosContainer.getChildren().add(conceptoHBox);
    }

    private void ajustarCampoDescuento() {

        boolean hayConceptos = !conceptosContainer.getChildren().isEmpty();
        
        separadorDescuento.setVisible(hayConceptos);
        lblDescuento.getParent().getParent().setVisible(hayConceptos);
        
        ajustarTamanioTicket();
    }

    private void ajustarTamanioTicket() {
        double alturaConceptos = conceptosContainer.getChildren().size() * 32.0 + 400.0; 
        double alturaMinima = 440.0;
        double nuevaAlturaTicket = Math.max(alturaMinima, alturaConceptos);

        ticketContentPane.setPrefHeight(nuevaAlturaTicket);
        double posBtn = nuevaAlturaTicket + 30.0;
        AnchorPane.setTopAnchor(btnConfirmar, posBtn);
        
        rootPane.setPrefHeight(posBtn + 70.0);
    }

    /**
     * aqui se carga cada campo del pago en el ticket
     */
    private void cargarInfoPago(){
        //obtenemos el pago registrado en el cache
        TicketRegistrarDTO ticketCache = TicketRegistrarCache.getInstance();

        //recogemos todos los datos del ticket
        String total = ticketCache.getMontoTotal().toString();

        String cliente = ticketCache.getAlumno().getNombre();

        List<DetallePagoDTO> detalles = ticketCache.getDetalles();
        Map<String, Double> mapaDetalles = new HashMap<>();

        for (DetallePagoDTO detalle : detalles) {
            mapaDetalles.put(detalle.getConceptoCuota(), detalle.getMontoPagado());
        }
        
        MetodosPagoDTO metodoPago = ticketCache.getMetodoPago();
        lblTotal.setText(total);
        lblFolio.setText(ticketCache.getFolio());
        lblFecha.setText(ticketCache.getFecha().toString());
        lblCliente.setText(cliente);
        lblMetodo.setText(metodoPago.toString().toLowerCase());
        lblDescuento.setText(ticketCache.getTipoDescuento());
        lblSubTotal.setText(ticketCache.getSubTotal());
        lblMontoDescuento.setText(ticketCache.getMontoDescuento().toString());
        
        generarConceptos(mapaDetalles);
    }

    /**
     *
     */
    @FXML
    public void confirmarPago() throws ConexionServidorException {
        try {
            //intancia del ticket
            TicketRegistrarDTO ticket = TicketRegistrarCache.getInstance();

            //nueva instancia para pago
            PagoDTO pago = new PagoDTO();

            pago.setMontoTotal(ticket.getMontoTotal());
            pago.setFolio(ticket.getFolio());
           // pago.setFecha(LocalDateTime.of(ticket.getFecha(), ticket.getHora()));
            pago.setAlumno(ticket.getAlumno());
            pago.setMetodoPago(ticket.getMetodoPago());
            pago.setCuotasPagadas(ticket.getDetalles());
            pago.setIdCicloEscolar(ticket.getCiclo().getId());
            pago.setIdUsuario(ticket.getIdUsuario());
            pago.setTipoDescuento(ticket.getTipoDescuento());
            pago.setMontoDescuento(ticket.getMontoDescuento());

            //registramos el pago
            System.out.println("si entra y registra el pago");
            servicioPagos.registrarPago(pago);

            //refrescamos los pagos del alumno que pago
            mediador.refreshPaymentScreen();

            //cerramos la pantalla
            cancelar();

        } catch (ConexionServidorException ex) {
            cancelar();
            notificarError(ex.getMessage());
        }catch (Exception e) {
            System.out.println("problemas: "+e.toString());
            cancelar();
            e.printStackTrace();
        }
    }


    /**
     *
     */
    @FXML
    private void cancelar(){
        //limpiamos el cache del ticket guardado
        TicketRegistrarCache.limpiarCache();

        //cerramos la ventana
        Stage stage = (Stage) lblTotal.getScene().getWindow(); // Obtener el Stage (ventana) actual
        stage.close(); // Cerrar la ventana de inicio de sesión
    }

    private void notificarError(String mensaje) {
        Notifications.create()
                .title("Ups!!")
                .text(mensaje)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

    private void setBtnCancelarStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_CLOSE);
        icon.setIconSize(15);

        btnCancelar.setGraphic(icon);
        btnCancelar.setText("");

        Tooltip tooltip = new Tooltip("Cancelar");
        Tooltip.install(btnCancelar, tooltip);
    }

}
