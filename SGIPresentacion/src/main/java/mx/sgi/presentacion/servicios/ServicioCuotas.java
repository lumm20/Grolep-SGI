package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.CuotaDTO;
import mx.sgi.presentacion.interfaces.IServicioCuotas;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ServicioCuotas implements IServicioCuotas {

    // varibale para las peticiones
    private HttpClient client;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioCuotas() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public CuotaDTO obtenerCuotasAlumno(String matricula, CicloEscolarDTO cicloEscolar) throws Exception {
        // Simulación de obtención de datos (en un caso real, aquí iría la lógica de la consulta)
        System.out.println("Valores que llegaron: " + matricula + " " + cicloEscolar);

        // Simulación de valores de adeudos
        String adeudoVencido = "500.00"; // Total de adeudos vencidos
        String adeudoColegiatura = "1000.00";
        String adeudoInscripcion = "500.00";
        String adeudoLibros = "700.00";
        String adeudoEventos = "1200.00";
        String adeudoAcademias = "400.00";
        String adeudoUniformes = "800.00";

        // Se crea un solo objeto CuotaDTO con todos los adeudos en sus respectivos campos
        CuotaDTO cuota = new CuotaDTO(adeudoVencido, adeudoColegiatura, adeudoInscripcion,
                adeudoLibros, adeudoEventos, adeudoAcademias, adeudoUniformes);

        // Se devuelve una lista con un solo elemento, ya que ahora CuotaDTO almacena todos los valores en un solo objeto
        return cuota;
    }

    /**
     * Metodo que obtiene las colegiaturas atrasadas de un alumno, solo se pueden
     * obtener tres colegiaturas atrasadas.
     *
     * @param matricula matricula del alumno
     * @param cicloEscolar ciclo escolar en el que se encuentran las colegiaturas
     */
    @Override
    public List<ColegiaturaAtrasadaDTO> obtenerColegiaturasAtrasadas(String matricula, CicloEscolarDTO cicloEscolar) throws Exception {
        // Simulación de la lógica para obtener las colegiaturas atrasadas (por ejemplo, con una API o base de datos)
        List<ColegiaturaAtrasadaDTO> colegiaturasAtrasadas = new ArrayList<>();

        // Aquí agregarías la lógica real para consultar las colegiaturas atrasadas
        // Usamos un ejemplo con datos simulados
        // Agregamos tres colegiaturas atrasadas simuladas
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(new BigDecimal("1500.00"), LocalDate.of(2024, 1, 15)));
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(new BigDecimal("1800.00"), LocalDate.of(2024, 2, 15)));
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(new BigDecimal("1200.00"), LocalDate.of(2024, 3, 15)));

        return colegiaturasAtrasadas;
    }


}
