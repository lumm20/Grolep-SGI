package mx.itson.sgi.reportes;

import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.DetallePagoDTO;
import mx.itson.sgi.dto.MetodosPagoDTO;
import mx.itson.sgi.dto.PagoDTO;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportesPrueba {
    public static void main(String[] args) throws JRException, FileNotFoundException {
        List<PagoDTO> pagoList = new ArrayList<>();

        PagoDTO pago1 = new PagoDTO();
        pago1.setMontoTotal(500.0);
        pago1.setFolio("PAY123");
        LocalDateTime fecha1 = LocalDateTime.now();
        pago1.setFecha(fecha1);

        AlumnoConsultaDTO alumno1 = new AlumnoConsultaDTO();
        alumno1.setMatricula("A12345");
        alumno1.setNombre("Maria Gutierrez");
        alumno1.setNumeroCelular("555-1234");
        pago1.setAlumno(alumno1);

        pago1.setMetodoPago(MetodosPagoDTO.Transferencia);

        List<DetallePagoDTO> cuotasPagadas1 = new ArrayList<>();
        DetallePagoDTO det1 = new DetallePagoDTO();
        det1.setConceptoCuota("Matrícula");
        det1.setMontoPagado(300.0);
        cuotasPagadas1.add(det1);
        DetallePagoDTO det2 = new DetallePagoDTO();
        det2.setConceptoCuota("Libros");
        det2.setMontoPagado(200.0);
        cuotasPagadas1.add(det2);
        pago1.setCuotasPagadas(cuotasPagadas1);

        pago1.setTipoDescuento("General");
        pago1.setMontoDescuento(50.0);
        pago1.setIdCicloEscolar("C2023-1");
        pago1.setIdUsuario(1L);

        pagoList.add(pago1);

        PagoDTO pago2 = new PagoDTO();
        pago2.setMontoTotal(700.0);
        pago2.setFolio("PAY456");
        LocalDateTime fecha2 = LocalDateTime.now();
        pago2.setFecha(fecha2);

        AlumnoConsultaDTO alumno2 = new AlumnoConsultaDTO();
        alumno2.setMatricula("A67890");
        alumno2.setNombre("Pedro Martinez");
        alumno2.setNumeroCelular("555-5678");
        pago2.setAlumno(alumno2);

        pago2.setMetodoPago(MetodosPagoDTO.Tarjeta);

        List<DetallePagoDTO> cuotasPagadas2 = new ArrayList<>();
        DetallePagoDTO det3 = new DetallePagoDTO();
        det3.setConceptoCuota("Mensualidad");
        det3.setMontoPagado(500.0);
        cuotasPagadas2.add(det3);
        DetallePagoDTO det4 = new DetallePagoDTO();
        det4.setConceptoCuota("Actividades");
        det4.setMontoPagado(200.0);
        cuotasPagadas2.add(det4);
        pago2.setCuotasPagadas(cuotasPagadas2);

        pago2.setTipoDescuento(null);
        pago2.setMontoDescuento(null);
        pago2.setIdCicloEscolar("C2023-2");
        pago2.setIdUsuario(2L);

        pagoList.add(pago2);

        PagoDTO pago3 = new PagoDTO();
        pago3.setMontoTotal(300.0);
        pago3.setFolio("PAY789");
        LocalDateTime fecha3 = LocalDateTime.now();
        pago3.setFecha(fecha3);

        AlumnoConsultaDTO alumno3 = new AlumnoConsultaDTO();
        alumno3.setMatricula("A23456");
        alumno3.setNombre("Ana Lopez");
        alumno3.setNumeroCelular("555-9876");
        pago3.setAlumno(alumno3);

        pago3.setMetodoPago(MetodosPagoDTO.Efectivo);

        List<DetallePagoDTO> cuotasPagadas3 = new ArrayList<>();
        DetallePagoDTO det5 = new DetallePagoDTO();
        det5.setConceptoCuota("Inscripción");
        det5.setMontoPagado(300.0);
        cuotasPagadas3.add(det5);
        pago3.setCuotasPagadas(cuotasPagadas3);

        pago3.setTipoDescuento(null);
        pago3.setMontoDescuento(null);
        pago3.setIdCicloEscolar("C2023-1");
        pago3.setIdUsuario(3L);

        pagoList.add(pago3);

        PagoDTO pago4 = new PagoDTO();
        pago4.setMontoTotal(800.0);
        pago4.setFolio("PAY101");
        LocalDateTime fecha4 = LocalDateTime.now();
        pago4.setFecha(fecha4);

        AlumnoConsultaDTO alumno4 = new AlumnoConsultaDTO();
        alumno4.setMatricula("A90123");
        alumno4.setNombre("Luis Hernández");
        alumno4.setNumeroCelular("555-4321");
        pago4.setAlumno(alumno4);

        pago4.setMetodoPago(MetodosPagoDTO.Transferencia);

        List<DetallePagoDTO> cuotasPagadas4 = new ArrayList<>();
        DetallePagoDTO det6 = new DetallePagoDTO();
        det6.setConceptoCuota("Mensualidad");
        det6.setMontoPagado(500.0);
        cuotasPagadas4.add(det6);
        DetallePagoDTO det7 = new DetallePagoDTO();
        det7.setConceptoCuota("Material");
        det7.setMontoPagado(200.0);
        cuotasPagadas4.add(det7);
        DetallePagoDTO det8 = new DetallePagoDTO();
        det8.setConceptoCuota("Otros");
        det8.setMontoPagado(100.0);
        cuotasPagadas4.add(det8);
        pago4.setCuotasPagadas(cuotasPagadas4);

        pago4.setTipoDescuento("Primavera");
        pago4.setMontoDescuento(80.0);
        pago4.setIdCicloEscolar("C2023-2");
        pago4.setIdUsuario(4L);

        pagoList.add(pago4);

        String destino = "C:";
    }
}