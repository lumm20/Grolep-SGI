package mx.sgi.presentacion.mediador;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.vistas.TicketRegistrarDTO;
import mx.sgi.presentacion.caches.TicketRegistrarCache;
import mx.sgi.presentacion.controladores.*;
import mx.sgi.presentacion.excepciones.ConexionServidorException;

/**
 *
 * @author skevi
 */
public class Mediador {

    /**
     * Variable que contiene la instancia unica
     */
    private static volatile Mediador instancia;

    /**
     * Método estático para obtener la única instancia
     * @return instancia unica de la clase
     */
    public static synchronized Mediador getInstance() {
        if (instancia == null) {
            instancia = new Mediador();
        }
        return instancia;
    }

    /**
     * Constructor privado para evitar instanciación externa
     */
    private Mediador() {

    }


    /**
     *
     */
    public void mostrarPantallaColegiaturasAtrasadas(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/ColegiaturasAtrasadas.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Crear un nuevo Stage (ventana)
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Detalles colegiaturas atrasadas");
            nuevaVentana.setScene(scene);

            // (Opcional) Si deseas evitar que el usuario redimensione la ventana
            nuevaVentana.setResizable(false);

            // Mostrar la nueva ventana
            nuevaVentana.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla principal de ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws ConexionServidorException
     */
    public void abrirPantallaTicket() throws ConexionServidorException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/Ticket.fxml"));
            Parent root = loader.load();
            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Crear un nuevo Stage (ventana)
            Stage nuevaVentana = new Stage();
            nuevaVentana.initModality(Modality.APPLICATION_MODAL);
            nuevaVentana.setTitle("GROLEP SGI v1.0");
            nuevaVentana.setScene(scene);

            // (Opcional) Si deseas evitar que el usuario redimensione la ventana
            nuevaVentana.setResizable(false);

            // Mostrar la nueva ventana
            //cuando hay desconexion aqui manda una excepcion
            nuevaVentana.showAndWait();

            TicketController controller = loader.getController();
            System.out.println("Aqui deberia tomar la excepcion y lanzarla");
            //controller.confirmarPago(); //esta linea daba error nulo
        } catch (IOException e) {
            throw new ConexionServidorException("Error al abrir la ventana de confirmación del pago.", e);
        }catch (RuntimeException e) {
            System.err.println("Error durante la ejecución de la ventana: " + e.getMessage());
            e.printStackTrace();
            throw new ConexionServidorException("Ocurrió un error inesperado al mostrar la ventana del ticket.", e);
        }
    }


    /**
     *  Method that sets the initial center frame and the initial left frame
     *  this method has to be used only for the first invocation of the main frame,
     *  normally done after a login.
     */
    public void showMainFrame(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/MainFrame.fxml"));
            Parent root = loader.load();

            // Call the controller of the loaded Screen
            MainFrameController mainScreen = loader.getController();

            // Set the global instance for the controller
            MainFrameController.setInstance(mainScreen);
            // Create a new Scene
            Scene scene = new Scene(root);

            // Create a new Stage(Window)
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("GROLEP SGI v1.0");

            // Mostrar la nueva ventana
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openReportsScreen(){
        if (MainFrameController.getInstance() != null) {
            MainFrameController controller = MainFrameController.getInstance();
            controller.setCenterPane(
                    "/mx/sgi/presentacion/main/GenerateReport.fxml"
            );
        }
    }

    public void openPaymentsScreen(){
        if (MainFrameController.getInstance() != null) {
            MainFrameController controller = MainFrameController.getInstance();
            controller.setCenterPane(
                    "/mx/sgi/presentacion/main/Payments.fxml"
            );
        }
    }

    public void openCyclesScreen(){
        if (MainFrameController.getInstance() != null) {
            MainFrameController controller = MainFrameController.getInstance();
            controller.setCenterPane(
                    "/mx/sgi/presentacion/main/ManageCycles.fxml"
            );
        }
    }

    public void openStudentsScreen(){
        if (MainFrameController.getInstance() != null) {
            MainFrameController controller = MainFrameController.getInstance();
            controller.setCenterPane(
                    "/mx/sgi/presentacion/main/ManageStudent.fxml"
            );
        }
    }

    public void openAdministratorSidebar(){
        if (MainFrameController.getInstance() != null) {
            MainFrameController controller = MainFrameController.getInstance();
            controller.setLeftPane(
                    "/mx/sgi/presentacion/main/AdministratorSidebar.fxml"
            );
        }
    }

    public void openRegisterUserScreen(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/RegisterStudent.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);
            // Crear un nuevo Stage (ventana)
            Stage newScreen = new Stage();

            newScreen.setTitle("Registrar Estudiante");
            newScreen.setScene(scene);
            newScreen.setResizable(false);
            newScreen.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de estudiantes");
            e.printStackTrace();
        }
    }

    public void openEditUserScreen(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/EditStudent.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);
            // Crear un nuevo Stage (ventana)
            Stage newScreen = new Stage();

            newScreen.setTitle("Editar Estudiante");
            newScreen.setScene(scene);
            newScreen.setResizable(false);
            newScreen.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de estudiantes");
            e.printStackTrace();
        }
    }

    public void openAddFiltersScreen(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/ReportFilters.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);
            // Crear un nuevo Stage (ventana)
            Stage newScreen = new Stage();

            newScreen.setTitle("Filtros de busqueda");
            newScreen.setScene(scene);
            newScreen.setResizable(false);
            newScreen.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de estudiantes");
            e.printStackTrace();
        }
    }

    public void openRegisterCycleScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/RegisterCycle.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);
            // Crear un nuevo Stage (ventana)
            Stage newScreen = new Stage();

            newScreen.setTitle("Registro de ciclo escolar");
            newScreen.setScene(scene);
            newScreen.setResizable(false);
            newScreen.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de estudiantes");
            e.printStackTrace();
        }
    }

    public void openEditCycleScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/sgi/presentacion/main/EditCycle.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);
            // Crear un nuevo Stage (ventana)
            Stage newScreen = new Stage();

            newScreen.setTitle("Edicion de ciclo escolar");
            newScreen.setScene(scene);
            newScreen.setResizable(false);
            newScreen.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de estudiantes");
            e.printStackTrace();
        }
    }


    public void refreshManageStudentsScreen(){
        MainFrameController mainFrame = MainFrameController.getInstance();
        ManageStudentController controller = (ManageStudentController) mainFrame.getCenter();
        controller.loadTable();
    }

    public void refreshManageCyclesScreen(){
        MainFrameController mainFrame = MainFrameController.getInstance();
        ManageCyclesController controller = (ManageCyclesController) mainFrame.getCenter();
        controller.loadTable();
    }

    public void refreshPaymentScreen(){
        MainFrameController mainFrame = MainFrameController.getInstance();
        PayamentsController controller = (PayamentsController) mainFrame.getCenter();

        TicketRegistrarDTO ticket = TicketRegistrarCache.getInstance();

        String matricula = ticket.getAlumno().getMatricula();
        CicloEscolarDTO cicloEscolar = ticket.getCiclo();

        controller.cleanupTxtFields();
        controller.establecerCuotas(matricula, cicloEscolar);
    }

}