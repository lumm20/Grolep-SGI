package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import mx.sgi.presentacion.mediador.Mediador;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 */
public class AdministratorSidebarController implements Initializable {

    //Declaration for the buttons
    @FXML
    private MFXButton menuButton;

    @FXML
    private MFXButton paymentButton;

    @FXML
    private MFXButton usersButton;

    @FXML
    private MFXButton shoppingButton;

    @FXML
    private MFXButton reportsButton;

    @FXML
    private MFXButton cyclesButton;

    @FXML
    private MFXButton logoutButton;


    //Booleans declaration for selected buttons.
    private boolean isPaymentButtonSelected;

    private boolean isUsersButtonSelected;

    private boolean isShoppingButtonSelected;

    private boolean isReportsButtonSelected;

    private boolean isCyclesButtonSelected;


    //List for the buttons of the dashboard
    List<MFXButton> buttons = new ArrayList<>();

    //instance for the mediator
    Mediador mediador;


    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMenuButtonStyles();
        setPaymentsButtonStyles();
        setUsersButtonStyles();
        setShoppingButtonStyles();
        setReportsButtonStyles();
        setCyclesButtonStyles();
        setLogoutButtonStyles();

        collectAllButtons();
        setOpeningSelectedButton();

        mediador = Mediador.getInstance();
    }


    private void setMenuButtonStyles(){
        FontIcon icon  = new FontIcon("mdi-menu");
        icon.setIconSize(30);
        icon.setIconColor(Color.web("#282828"));

        //assign the icon
        menuButton.setGraphic(icon);
        //take off the text
        menuButton.setText("");
        //set the tool tip

        // Eventos de hover
        menuButton.setOnMouseEntered(e -> {
            menuButton.setStyle("-fx-background-color: #7198b9;");
        });

        menuButton.setOnMouseExited(e -> {
            menuButton.setStyle("-fx-background-color: #a1c1dd;");
        });
    }

    private void setPaymentsButtonStyles(){
        FontIcon icon = new FontIcon("mdi-cash-multiple");
        icon.setIconSize(30);
        icon.setIconColor(Color.web("#6f6e6e"));

        //assign the icon
        paymentButton.setGraphic(icon);
        //take of the text
        paymentButton.setText("");
        //assign the tooltip
        Tooltip tooltip = new Tooltip("Registrar pagos");
        Tooltip.install(paymentButton, tooltip);

        // Create transitions
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), icon);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), icon);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        // Hover events
        paymentButton.setOnMouseEntered(e -> {
            icon.setIconColor(Color.web("#282828"));
            paymentButton.setStyle("-fx-background-color: #7198b9;");
            scaleIn.playFromStart();
        });

        paymentButton.setOnMouseExited(e -> {
            if(!isPaymentButtonSelected){
                icon.setIconColor(Color.web("#6f6e6e"));
                paymentButton.setStyle("-fx-background-color: #a1c1dd;");
                scaleOut.playFromStart();
            }
        });
    }

    private void setUsersButtonStyles(){
        FontIcon icon = new FontIcon("mdi-account");
        icon.setIconSize(35);
        icon.setIconColor(Color.web("#6f6e6e"));

        //asignamos el icono
        usersButton.setGraphic(icon);
        //quitamos el texto
        usersButton.setText("");

        Tooltip tooltip = new Tooltip("Administracion de estudiantes");
        Tooltip.install(usersButton, tooltip);

        // Crear transiciones
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), icon);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), icon);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        // Eventos de hover
        usersButton.setOnMouseEntered(e -> {
            icon.setIconColor(Color.web("#282828"));
            usersButton.setStyle("-fx-background-color: #7198b9;");
            scaleIn.playFromStart();
        });

        usersButton.setOnMouseExited(e -> {
            if(!isUsersButtonSelected) {
                icon.setIconColor(Color.web("#6f6e6e"));
                usersButton.setStyle("-fx-background-color: #a1c1dd;");
                scaleOut.playFromStart();
            }
        });
    }

    private void setShoppingButtonStyles(){
        FontIcon icon = new FontIcon("mdi-shopping");
        icon.setIconSize(30);
        icon.setIconColor(Color.web("#6f6e6e"));

        //asignamos el icono
        shoppingButton.setGraphic(icon);
        //quitamos el texto
        shoppingButton.setText("");

        Tooltip tooltip = new Tooltip("venta de productos");
        Tooltip.install(shoppingButton, tooltip);

        // Crear transiciones
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), icon);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), icon);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        // Eventos de hover
        shoppingButton.setOnMouseEntered(e -> {
            icon.setIconColor(Color.web("#282828"));
            shoppingButton.setStyle("-fx-background-color: #7198b9;");
            scaleIn.playFromStart();
        });

        shoppingButton.setOnMouseExited(e -> {
            if(!isShoppingButtonSelected){
                icon.setIconColor(Color.web("#6f6e6e"));
                shoppingButton.setStyle("-fx-background-color: #a1c1dd;");
                scaleOut.playFromStart();
            }
        });
    }

    private void setReportsButtonStyles(){
        FontIcon icon = new FontIcon("mdi-file-document");
        icon.setIconSize(30);
        icon.setIconColor(Color.web("#6f6e6e"));

        //asignamos el icono
        reportsButton.setGraphic(icon);
        //quitamos el texto
        reportsButton.setText("");

        Tooltip tooltip = new Tooltip("Generacion de reportes");
        Tooltip.install(reportsButton, tooltip);

        // Crear transiciones
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), icon);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), icon);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        // Eventos de hover
        reportsButton.setOnMouseEntered(e -> {
            icon.setIconColor(Color.web("#282828"));
            reportsButton.setStyle("-fx-background-color: #7198b9;");
            scaleIn.playFromStart();
        });

        reportsButton.setOnMouseExited(e -> {
            if (!isReportsButtonSelected){
                icon.setIconColor(Color.web("#6f6e6e"));
                reportsButton.setStyle("-fx-background-color: #a1c1dd;");
                scaleOut.playFromStart();
            }
        });
    }

    private void setCyclesButtonStyles(){
        FontIcon icon = new FontIcon("mdi-timetable");
        icon.setIconSize(30);
        icon.setIconColor(Color.web("#6f6e6e"));

        //asignamos el icono
        cyclesButton.setGraphic(icon);
        //quitamos el texto
        cyclesButton.setText("");

        Tooltip tooltip = new Tooltip("Administracion de ciclos escolares");
        Tooltip.install(cyclesButton, tooltip);

        // Crear transiciones
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), icon);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), icon);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        // Eventos de hover
        cyclesButton.setOnMouseEntered(e -> {
            icon.setIconColor(Color.web("#282828"));
            cyclesButton.setStyle("-fx-background-color: #7198b9;");
            scaleIn.playFromStart();
        });

        cyclesButton.setOnMouseExited(e -> {
            if(!isCyclesButtonSelected){
                icon.setIconColor(Color.web("#6f6e6e"));
                cyclesButton.setStyle("-fx-background-color: #a1c1dd;");
                scaleOut.playFromStart();
            }
        });
    }

    private void setLogoutButtonStyles(){
        FontIcon icon = new FontIcon("mdi-logout");
        icon.setIconSize(30);
        icon.setIconColor(Color.web("#282828"));

        //asignamos el icono
        logoutButton.setGraphic(icon);
        //quitamos el texto
        logoutButton.setText("");

        Tooltip tooltip = new Tooltip("Cerrar sesion");
        Tooltip.install(logoutButton, tooltip);

        // Eventos de hover
        logoutButton.setOnMouseEntered(e -> {
            logoutButton.setStyle("-fx-background-color: #7198b9;");
        });

        logoutButton.setOnMouseExited(e -> {
            logoutButton.setStyle("-fx-background-color: #a1c1dd;");
        });
    }


    /**
     * Sets the button that belongs to the screen that
     * appears at the initialization of the main frame
     */
    private void setOpeningSelectedButton(){
        this.isUsersButtonSelected = true;
        setButtonSelected(usersButton);

        //Unselect all other buttons
        isPaymentButtonSelected = false;
        isShoppingButtonSelected = false;
        isReportsButtonSelected = false;
        isCyclesButtonSelected = false;
    }

    /**
     * collects and adds all the buttons of the dashboard
     */
    private void collectAllButtons(){
        buttons.add(paymentButton);
        buttons.add(usersButton);
        buttons.add(shoppingButton);
        buttons.add(reportsButton);
        buttons.add(cyclesButton);
    }

    /**
     * Method to keep a button selected and unselect the others
     * @param button button to set selected
     */
    private void setButtonSelected(MFXButton button){
        //set the styles for the selected buttons
        button.setStyle("-fx-background-color: #7198b9;");
        //change the icon styles
        FontIcon icon = (FontIcon) button.getGraphic();
        icon.setScaleX(1.2);
        icon.setScaleY(1.2);
        icon.setIconColor(Color.web("282828"));

        for(MFXButton mfxButton: buttons){
            if(!mfxButton.equals(button)){
                //set the styles for the not selected buttons
                mfxButton.setStyle("-fx-background-color: #a1c1dd;");
                //change the icon styles for the not selected buttons
                FontIcon mfxIcon = (FontIcon) mfxButton.getGraphic();
                mfxIcon.setScaleX(1.0);
                mfxIcon.setScaleY(1.0);
                mfxIcon.setIconColor(Color.web("#6f6e6e"));
            }
        }
    }


    @FXML
    public void handlePaymentsButtonClick(Event event){
        this.isPaymentButtonSelected = true;
        setButtonSelected(paymentButton);

        //Unselect all other buttons
        isUsersButtonSelected = false;
        isShoppingButtonSelected = false;
        isReportsButtonSelected = false;
        isCyclesButtonSelected = false;

        //open the screen for payments
    }

    @FXML
    public void handleUsersButtonClick(Event event){
        this.isUsersButtonSelected = true;
        setButtonSelected(usersButton);

        //Unselect all other buttons
        isPaymentButtonSelected = false;
        isShoppingButtonSelected = false;
        isReportsButtonSelected = false;
        isCyclesButtonSelected = false;

        //open the screen for students administration
        mediador.openStudentsScreen();
    }

    @FXML
    public void handleShoppingButtonClick(Event event){
        this.isShoppingButtonSelected = true;
        setButtonSelected(shoppingButton);

        isPaymentButtonSelected = false;
        isUsersButtonSelected = false;
        isReportsButtonSelected = false;
        isCyclesButtonSelected = false;
    }

    @FXML
    public void handleReportsButtonClick(Event event){
        this.isReportsButtonSelected = true;
        setButtonSelected(reportsButton);

        //Unselect all other buttons
        isPaymentButtonSelected = false;
        isUsersButtonSelected = false;
        isShoppingButtonSelected = false;
        isCyclesButtonSelected = false;

        //open the screen for reports making
        mediador.openReportsScreen();
    }

    @FXML
    public void handleCyclesButtonClick(Event event){
        this.isCyclesButtonSelected = true;
        setButtonSelected(cyclesButton);

        //Unselect all other buttons
        isPaymentButtonSelected = false;
        isUsersButtonSelected = false;
        isShoppingButtonSelected = false;
        isReportsButtonSelected = false;

        //open the screen for cycles management
        mediador.openCyclesScreen();
    }

    @FXML
    public void handleLogoutButtonClick(Event event){
        System.out.println("cerre sesion");
    }

}
