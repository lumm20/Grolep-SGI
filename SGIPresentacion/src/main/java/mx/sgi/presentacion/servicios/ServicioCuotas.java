package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.CuotasDTO;
import mx.itson.sgi.dto.DescuentoDTO;
import mx.sgi.presentacion.interfaces.IServicioCuotas;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ServicioCuotas implements IServicioCuotas {

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioCuotas() {
    }

    @Override
    public CuotasDTO obtenerCuotasAlumno(String matricula, CicloEscolarDTO cicloEscolar) throws Exception {
        // Simulación de obtención de datos
        System.out.println("Valores que llegaron: " + matricula + " " + cicloEscolar);

        // Creación de varias cuotas simuladas con diferentes valores
        CuotasDTO cuotas = new CuotasDTO("500.00", "1000.00", "500.00", "700.00", "1200.00", "400.00", "800.00", "CIVICA", new DescuentoDTO("Primer Periodo", new BigDecimal("300.00")));

        return cuotas;
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
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(LocalDate.of(2024, 1, 15), new BigDecimal("1000.00"), new BigDecimal("500.00")));
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(LocalDate.of(2024, 2, 15), new BigDecimal("700.00"), new BigDecimal("800")));
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(LocalDate.of(2024, 3, 15), new BigDecimal("300.00"), new BigDecimal("1200.00")));

        return colegiaturasAtrasadas;
    }

    /**
     * Obtiene el total de la colegiatura atrasada de un alumno.
     *
     * @param matricula matricula del alumno
     * @param cicloEscolar ciclo escolar en el que se encuentran las colegiaturas
     * @return total de colegiatura atrasadas del alumno
     * @throws Exception en caso de error.
     */
    public BigDecimal obtenerTotalColegiaturaAtrasada(String matricula, CicloEscolarDTO cicloEscolar) throws Exception{
        return new BigDecimal("5000.00");
    }

}
