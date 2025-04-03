package mx.sgi.presentacion.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.util.Duration;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.DetalleAdeudoDTO;
import mx.sgi.presentacion.caches.AlumnoCache;
import mx.sgi.presentacion.caches.CicloEscolarCache;
import mx.sgi.presentacion.caches.DetallesAdeudoCache;
import mx.sgi.presentacion.servicios.ServicioCuotas;
import org.controlsfx.control.Notifications;

import java.math.BigDecimal;
import java.net.URL;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ColegiaturasAtrasadasController implements Initializable {

    @FXML
    Label lblMes1;

    @FXML
    Label lblMes2;

    @FXML
    Label lblMes3;

    @FXML
    Label lblAdeudoAcumulado1;

    @FXML
    Label lblAdeudoAcumulado2;

    @FXML
    Label lblAdeudoAcumulado3;

    @FXML
    Label lbllMontoPagado1;

    @FXML
    Label lbllMontoPagado2;

    @FXML
    Label lbllMontoPagado3;

    @FXML
    Label lblTotal;

    ServicioCuotas servicioCuotas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarInstancias();
        iniciarComponentesEnInvisible();
        cargarAdeudosAtrasados();
    }

    private void iniciarInstancias(){
        this.servicioCuotas = ServicioCuotas.getInstance();
    }

    private void iniciarComponentesEnInvisible(){
        lblMes1.setVisible(false);
        lblAdeudoAcumulado1.setVisible(false);
        lbllMontoPagado1.setVisible(false);

        lblMes2.setVisible(false);
        lblAdeudoAcumulado2.setVisible(false);
        lbllMontoPagado2.setVisible(false);

        lblMes3.setVisible(false);
        lblAdeudoAcumulado3.setVisible(false);
        lbllMontoPagado3.setVisible(false);
    }

    private void cargarAdeudosAtrasados() {

        List<DetalleAdeudoDTO> detalles = DetallesAdeudoCache.getDetalles();

        DetalleAdeudoDTO detalle1 = detalles.get(0);
        DetalleAdeudoDTO detalle2 = detalles.get(1);
        DetalleAdeudoDTO detalle3 = detalles.get(2);
        lblMes1.setVisible(true);
        lblAdeudoAcumulado1.setVisible(true);
        lbllMontoPagado1.setVisible(true);
        lblMes1.setText(detalle1.getMesAdeudo().getDisplayName(TextStyle.FULL, Locale.US));
        lblAdeudoAcumulado1.setText(detalle1.getMontoAdeudo().toString());
        lbllMontoPagado1.setText(detalle1.getMontoPagado().toString());

        lblMes2.setVisible(true);
        lblAdeudoAcumulado2.setVisible(true);
        lbllMontoPagado2.setVisible(true);
        lblMes2.setText(detalle2.getMesAdeudo().getDisplayName(TextStyle.FULL, Locale.US));
        lblAdeudoAcumulado2.setText(detalle2.getMontoAdeudo().toString());
        lbllMontoPagado2.setText(detalle2.getMontoPagado().toString());

        lblMes3.setVisible(true);
        lblAdeudoAcumulado3.setVisible(true);
        lbllMontoPagado3.setVisible(true);
        lblMes3.setText(detalle3.getMesAdeudo().getDisplayName(TextStyle.FULL, Locale.US));
        lblAdeudoAcumulado3.setText(detalle3.getMontoAdeudo().toString());
        lbllMontoPagado3.setText(detalle3.getMontoPagado().toString());

        Double total = detalle1.getMontoAdeudo() + detalle2.getMontoAdeudo() + detalle3.getMontoAdeudo();
        lblTotal.setText(total.toString());

    }
}
