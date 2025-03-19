package mx.sgi.presentacion.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.itson.sgi.dto.CuotasDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.TicketRegistrarDTO;
import mx.sgi.presentacion.caches.TicketRegistrarCache;
import mx.sgi.presentacion.servicios.ServicioPagos;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    Label lblMontoVencidos;

    @FXML
    Label  lblAdeudoColegiatura;

    @FXML
    Label lblMontoInscripcion;

    @FXML
    Label lblMontoILibros;

    @FXML
    Label lblMontoIEventos;

    @FXML
    Label lblMontoIAcademias;

    @FXML
    Label lblMontoUniforme;

    @FXML
    Label lblDescuento;

    ServicioPagos servicioPagos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.servicioPagos = new ServicioPagos();

        cargarInfoPago();
    }


    /**
     * aqui se carga cada campo del pago en el ticket
     */
    private void cargarInfoPago(){
        //obtenemos el pago registrado en el cache
        TicketRegistrarDTO ticketCache = TicketRegistrarCache.getInstance();

        //recogemos todos los datos del ticket
        String total = ticketCache.getMontoTotal().toString();
        String folio = ticketCache.getFolio().toString();
        String fecha = ticketCache.getFecha().toString();

        String cliente = ticketCache.getAlumno().getNombres().concat(" ").
                concat(ticketCache.getAlumno().getApellidoPaterno()).concat(" ").
                concat(ticketCache.getAlumno().getApellidoMaterno());

        String metodoPago = ticketCache.getMetodoPago();
        String montoVencidos = ticketCache.getMontoVencidos().toString();
        String montoColegiatura = ticketCache.getMontoColegiatura().toString();
        String montoInscripcion = ticketCache.getMontoInscripcion().toString();
        String montoLibros = ticketCache.getMontoLibros().toString();
        String montoEventos = ticketCache.getMontoEventos().toString();
        String montoAcademias = ticketCache.getMontoAcademias().toString();
        String montoUniforme = ticketCache.getMontoUniforme().toString();
        String descuento = ticketCache.getDescuento().toString();

        lblTotal.setText(total);
        lblFolio.setText(ticketCache.getFolio().toString());
        lblFecha.setText(ticketCache.getFecha().toString());
        lblCliente.setText(cliente);
        lblMetodo.setText(metodoPago);
        lblMontoVencidos.setText(montoVencidos);
        lblAdeudoColegiatura.setText(montoColegiatura);
        lblMontoInscripcion.setText(montoInscripcion);
        lblMontoILibros.setText(montoLibros);
        lblMontoIEventos.setText(montoEventos);
        lblMontoIAcademias.setText(montoAcademias);
        lblMontoUniforme.setText(montoUniforme);
        lblDescuento.setText(descuento);

    }

    /**
     *
     */
    @FXML
    public void confirmarPago(){

        //intancia del ticket
        TicketRegistrarDTO ticket =  TicketRegistrarCache.getInstance();

        //nueva instancia para pago
        PagoDTO pago =  new PagoDTO();

        pago.setMontoTotal(ticket.getMontoTotal());
        pago.setFolio(ticket.getFolio());
        pago.setFecha(ticket.getFecha());
        pago.setHora(ticket.getHora());
        pago.setAlumno(ticket.getAlumno());
        pago.setMetodoPago(ticket.getMetodoPago());
        pago.setCuotasPagadas(crearCuotas());

    }

    private List<CuotasDTO> crearCuotas(){
        TicketRegistrarDTO ticket =  TicketRegistrarCache.getInstance();

        List<CuotasDTO> cuotas;

        if (ticket.getMontoVencidos()  != null) {

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
        stage.close(); // Cerrar la ventana de inicio de sesiónS
    }

}
