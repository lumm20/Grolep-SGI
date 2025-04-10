package mx.sgi.presentacion.controladores;

import java.net.URL;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mx.itson.sgi.dto.DetalleAdeudoDTO;
import mx.sgi.presentacion.caches.DetallesAdeudoCache;
import mx.sgi.presentacion.servicios.ServicioCuotas;

public class ColegiaturasAtrasadasController implements Initializable {

    @FXML
    private AnchorPane root; // El AnchorPane principal
    
    @FXML
    private AnchorPane contenedorFilas;
    @FXML
    private Label lblTotal;
    ServicioCuotas servicioCuotas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.servicioCuotas = ServicioCuotas.getInstance();
        cargarInfo();
    }

    
private void cargarInfo() {
    List<DetalleAdeudoDTO> detalles = DetallesAdeudoCache.getInstance().getDetalles();
    
    // Ya no necesitamos buscar el contenedor por coordenadas
    // porque ahora tenemos una referencia directa con @FXML
    double montoAdeudo = 0.0;
    if (contenedorFilas != null) {
        // Posición Y inicial para la primera fila
        double posY = 0;
        String fecha;
        for (DetalleAdeudoDTO detalle : detalles) {
            // Crear una fila para este mes
            fecha = detalle.getFechaPago().toString();
            AnchorPane filaMes = crearFila(fecha, detalle.getMontoAdeudo(), 
                                          detalle.getMontoPagado(), detalle.getMontoBase());
            
            // Posicionar la fila
            filaMes.setLayoutY(posY);
            
            // Agregar la fila al contenedor
            contenedorFilas.getChildren().add(filaMes);
            
            // Incrementar la posición Y para la siguiente fila
            posY += 70; // Ajusta este valor según el espacio que quieras entre filas
            montoAdeudo += detalle.getMontoAdeudo();
        }
        
        // Asegurarse de que el contenedor tenga suficiente altura para todas las filas
        contenedorFilas.setPrefHeight(Math.max(230.0, posY));
    }
    
    Double totalAdeudo = montoAdeudo;
    lblTotal.setText(String.format("%.2f", totalAdeudo));
}

private AnchorPane crearFila(String nombreMes, Double adeudoAcumulado, Double montoPagado, Double montoBase) {
    // Crear un contenedor para la fila
    AnchorPane filaMes = new AnchorPane();
    filaMes.setPrefHeight(50.0);
    filaMes.setPrefWidth(560.0); // Ajustado al ancho del contenedor
    
    // Crear etiqueta para el mes (fecha)
    Label lblMes = new Label(nombreMes);
    lblMes.setAlignment(javafx.geometry.Pos.CENTER);
    lblMes.setPrefHeight(30.0);
    lblMes.setPrefWidth(120.0);
    lblMes.setFont(new Font("System Bold", 17.0));
    lblMes.setLayoutX(25.0);
    lblMes.setLayoutY(10.0);
    
    // Crear etiqueta para el monto pagado
    Label lblPagado = new Label(String.format("%.2f", montoPagado));
    lblPagado.setAlignment(javafx.geometry.Pos.CENTER);
    lblPagado.setPrefHeight(26.0);
    lblPagado.setPrefWidth(100.0);
    lblPagado.setFont(new Font("System", 17.0));
    lblPagado.setLayoutX(190.0); // Ajustado
    lblPagado.setLayoutY(12.0);
    
    // Crear etiqueta para el adeudo
    Label lblAdeudo = new Label(String.format("%.2f", adeudoAcumulado));
    lblAdeudo.setAlignment(javafx.geometry.Pos.CENTER);
    lblAdeudo.setPrefHeight(26.0);
    lblAdeudo.setPrefWidth(100.0);
    lblAdeudo.setFont(new Font("System", 17.0));
    lblAdeudo.setLayoutX(340.0); // Ajustado
    lblAdeudo.setLayoutY(12.0);
    
    // Nueva etiqueta para Cuota del mes (montoBase)
    Label lblCuota = new Label(String.format("%.2f", montoBase));
    lblCuota.setAlignment(javafx.geometry.Pos.CENTER);
    lblCuota.setPrefHeight(26.0);
    lblCuota.setPrefWidth(100.0);
    lblCuota.setFont(new Font("System", 17.0));
    lblCuota.setLayoutX(460.0); // Posicionado a la derecha
    lblCuota.setLayoutY(12.0);
    
    // Agregar las etiquetas al contenedor de la fila
    filaMes.getChildren().addAll(lblMes, lblPagado, lblAdeudo, lblCuota);
    
    return filaMes;
}
    
    // private void iniciarComponentesEnInvisible(){
    //     lblMes1.setVisible(false);
    //     lblAdeudoAcumulado1.setVisible(false);
    //     lbllMontoPagado1.setVisible(false);

    //     lblMes2.setVisible(false);
    //     lblAdeudoAcumulado2.setVisible(false);
    //     lbllMontoPagado2.setVisible(false);

    //     lblMes3.setVisible(false);
    //     lblAdeudoAcumulado3.setVisible(false);
    //     lbllMontoPagado3.setVisible(false);
    // }

    // private void cargarAdeudosAtrasados() {

    //     List<DetalleAdeudoDTO> detalles = DetallesAdeudoCache.getDetalles();

    //     DetalleAdeudoDTO detalle1 = detalles.get(0);
    //     DetalleAdeudoDTO detalle2 = detalles.get(1);
    //     DetalleAdeudoDTO detalle3 = detalles.get(2);
    //     lblMes1.setVisible(true);
    //     lblAdeudoAcumulado1.setVisible(true);
    //     lbllMontoPagado1.setVisible(true);
    //     lblMes1.setText(detalle1.getMesAdeudo().getDisplayName(TextStyle.FULL, Locale.US));
    //     lblAdeudoAcumulado1.setText(detalle1.getMontoAdeudo().toString());
    //     lbllMontoPagado1.setText(detalle1.getMontoPagado().toString());

    //     lblMes2.setVisible(true);
    //     lblAdeudoAcumulado2.setVisible(true);
    //     lbllMontoPagado2.setVisible(true);
    //     lblMes2.setText(detalle2.getMesAdeudo().getDisplayName(TextStyle.FULL, Locale.US));
    //     lblAdeudoAcumulado2.setText(detalle2.getMontoAdeudo().toString());
    //     lbllMontoPagado2.setText(detalle2.getMontoPagado().toString());

    //     lblMes3.setVisible(true);
    //     lblAdeudoAcumulado3.setVisible(true);
    //     lbllMontoPagado3.setVisible(true);
    //     lblMes3.setText(detalle3.getMesAdeudo().getDisplayName(TextStyle.FULL, Locale.US));
    //     lblAdeudoAcumulado3.setText(detalle3.getMontoAdeudo().toString());
    //     lbllMontoPagado3.setText(detalle3.getMontoPagado().toString());

    //     Double total = detalle1.getMontoAdeudo() + detalle2.getMontoAdeudo() + detalle3.getMontoAdeudo();
    //     lblTotal.setText(total.toString());

    // }
}
