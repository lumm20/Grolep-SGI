package mx.itson.sgi.data_access;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.CuotaMensual;
import mx.itson.sgi.data_access.entities.DetallePago;
import mx.itson.sgi.data_access.entities.MetodoPago;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.PagoRepository;

@Service
public class PaymentDataInitializer {

    @Autowired
	private PagoRepository pagoRepository;
    @Autowired
    private static FeeDataInitializer feeData;
    private static CuotaMensual cuotaM ;
    private static Double adeudoAcumulado = 0.0;
    private static Double adeudoDelMes = 0.0; 
    /**
     * Guarda todos los registros de los pagos por cada alumno en el ciclo 23-24
     * @throws Exception en caso de que no se encuentre el cajero, o haya
     * un error al guardar los pagos
     */
    @Transactional
    protected void cargarPagos2324(Usuario cajero,Alumno alumno1, Alumno alumno2, Alumno alumno3)throws Exception{
        try {
            for (Pago pago : crearPagos2324Alumno1(cajero,alumno1)) {
                pagoRepository.save(pago);
            }
            for (Pago pago : crearPagos2324Alumno2(cajero,alumno2)) {
                pagoRepository.save(pago);
            }
            for (Pago pago : crearPagos2324Alumno3(cajero,alumno3)) {
                pagoRepository.save(pago);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception ("Error al guardar los pagos");
        }
        
    }

     /**
     * Guarda todos los registros de los pagos por cada alumno en el ciclo 23-24
     * @throws Exception en caso de que no se encuentre el cajero, o haya
     * un error al guardar los pagos
     */
    @Transactional
    protected void cargarPagos2425(Usuario cajero, Alumno alumno1, Alumno alumno2, Alumno alumno3)throws Exception{

        try {
            for (Pago pago : crearPagos2425Alumno1(cajero, alumno1)) {
                pagoRepository.save(pago);
            }
            for (Pago pago : crearPagos2425Alumno2(cajero, alumno2)) {
                pagoRepository.save(pago);
            }
            for (Pago pago : crearPagos2425Alumno3(cajero, alumno3)) {
                pagoRepository.save(pago);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception ("Error al guardar los pagos");
        }
        
    }

    /**
     * Crea los objetos de tipo Pago en el ciclo 23-24 referente al alumno 1
     * @param cajero Usuario el cual se registrara en el pago
     * @return la lista de pagos generados
     * @throws Exception en caso de que no se encuentre el alumno o las cuotas mensuales
     */
    private List<Pago> crearPagos2324Alumno1(Usuario cajero, Alumno alumno1)throws Exception {
        List<CuotaMensual> cuotasMensuales = feeData.buscarCuotasMensuales(alumno1, new CicloEscolar("23-24"));
        if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
            System.out.println("No se encontraron cuotas mensuales para el alumno");
            throw new Exception("No se encontraron cuotas mensuales para el alumno");
        }
        List<Cuota> cuotas = feeData.buscarCuotas(alumno1, new CicloEscolar("23-24"));
        if (cuotas == null || cuotas.isEmpty()) {
            System.out.println("No se encontraron cuotas para el alumno");
            throw new Exception("No se encontraron cuotas para el alumno");
        }
        List<Pago> pagos = new ArrayList<>();
        //agregar pago de inscripcion
        pagos.add(new Pago("P202300004", LocalDateTime.of(2023, 8, 15, 9, 15, 30),
        2300.0, 0.0, "No aplica", cajero, alumno1,
        List.of(new DetallePago(feeData.buscarCuota(Concepto.INSCRIPCION, cuotas), 2300.0, new Pago("P202300004"))), MetodoPago.EFECTIVO));
        
        // //agregar pago de colegiatura de agosto
        // cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.AUGUST);
        // adeudoAcumulado = cuotaM.getAdeudoAcumulado(); 
        // adeudoDelMes= 0.0; 
        actualizarCuotaMensual(cuotasMensuales, Month.AUGUST, pagos);
        pagos.add(new Pago("P202300006", LocalDateTime.of(2023, 8, 20, 10, 0, 0),
        1600.0, 0.0, "No aplica", cajero, alumno1,
        List.of(new DetallePago(cuotaM, 1600.0,new Pago("P202300006"))), MetodoPago.EFECTIVO));
        // cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        // adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        // if(adeudoDelMes == 0.0){
        //     adeudoAcumulado = 0.0;
        // }
        //agregar pago de uniformes y libros
        pagos.add(new Pago("P202300003", LocalDateTime.of(2023, 8, 13, 10, 15, 0),
        2350.0, 0.0, "No aplica", cajero, alumno1,
        List.of(new DetallePago(feeData.buscarCuota(Concepto.UNIFORMES, cuotas), 2000.0,new Pago("P202300003")),new DetallePago(feeData.buscarCuota(Concepto.LIBROS, cuotas), 350.0,new Pago("P202300003"))), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de septiembre
        // cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.SEPTEMBER);
        pagos.add(new Pago("P202300008", LocalDateTime.of(2023, 9, 10, 9, 50, 0), 1600.00,
        0.0, "No aplica", cajero, alumno1,
        List.of(new DetallePago(cuotaM, 1600.0,new Pago("P202300008"))), MetodoPago.TRANSFERENCIA));
        actualizarCuotaMensual(cuotasMensuales, Month.SEPTEMBER, pagos);
        // cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        // adeudoAcumulado = cuotaM.getAdeudoAcumulado();
        // adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        // if(adeudoDelMes == 0.0){
        //     adeudoAcumulado = 0.0;
        // }
        //agregar pago de colegiatura de octubre y noviembre
        pagos.add(new Pago("P202300011", LocalDateTime.of(2023, 11, 15, 8, 30, 0), 3200.00, 
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1600.0,new Pago("P202300011")) ,
        new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1600.0,new Pago("P202300011"))), MetodoPago.EFECTIVO));
        actualizarCuotaMensual(cuotasMensuales, Month.OCTOBER, pagos);
        // cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.OCTOBER);
        // cuotaM = feeData.actualizarCuotaMensual(0.0, adeudoDelMes, adeudoAcumulado, cuotaM);
        // adeudoAcumulado= cuotaM.getAdeudoAcumulado();
        // adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        // if(adeudoDelMes == 0.0){
        //     adeudoAcumulado = 0.0;
        // }
        actualizarCuotaMensual(cuotasMensuales, Month.NOVEMBER, pagos);
        // cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.NOVEMBER);
        // cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        // adeudoAcumulado = cuotaM.getAdeudoAcumulado();
        // adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        // if(adeudoDelMes == 0.0){
        //     adeudoAcumulado = 0.0;
        // }
        //agregar pago de colegiatura de diciembre
        pagos.add(new Pago("P202300013", LocalDateTime.of(2023, 12, 5, 12, 0, 0), 1600.00, 
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.DECEMBER), 1600.0,new Pago("P202300013"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.DECEMBER);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoAcumulado = cuotaM.getAdeudoAcumulado();
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }
        //agregar pago de colegiatura de enero y febrero
        pagos.add(new Pago("P202400002", LocalDateTime.of(2024, 2, 10, 10, 10, 0), 3200.00, 
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.JANUARY), 1600.0,new Pago("P202400002")),
        new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.FEBRUARY), 1600.0,new Pago("P202400002"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.JANUARY);
        cuotaM = feeData.actualizarCuotaMensual(0.0, adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoAcumulado = cuotaM.getAdeudoAcumulado();
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.FEBRUARY);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoAcumulado = cuotaM.getAdeudoAcumulado();
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }
        //agregar pago de colegiatura de marzo
        pagos.add(new Pago("P202400003", LocalDateTime.of(2024, 3, 8, 11, 20, 0), 1600.00, 
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.MARCH), 1600.0,new Pago("P202400003"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.MARCH);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoAcumulado = cuotaM.getAdeudoAcumulado();
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        //agregar pago de colegiatura de abril y mayo
        pagos.add(new Pago("P202400007", LocalDateTime.of(2024, 5, 13, 8, 45, 0), 3200.00,
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.APRIL), 1600.0,new Pago("P202400007")),
        new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.MAY), 1600.0,new Pago("P202400007"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.APRIL);
        cuotaM = feeData.actualizarCuotaMensual(0.0, adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoAcumulado = cuotaM.getAdeudoAcumulado();
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.MAY);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoAcumulado = cuotaM.getAdeudoAcumulado();
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        //agregar pago de colegiatura de junio
        pagos.add(new Pago("P202400010", LocalDateTime.of(2024, 6, 5, 10, 30, 0), 1600.00,
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.JUNE), 1600.0,new Pago("P202400010"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.JUNE);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoAcumulado = cuotaM.getAdeudoAcumulado();
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();

        return pagos;
    }

    /**
     * Crea los objetos de tipo Pago en el ciclo 23-24 referente al alumno 2
     * @param cajero Usuario el cual se registrara en el pago
     * @return la lista de pagos generados
     * @throws Exception en caso de que no se encuentre el alumno o las cuotas mensuales
     */
    private List<Pago> crearPagos2324Alumno2(Usuario cajero, Alumno alumno)throws Exception {
        List<CuotaMensual> cuotasMensuales = feeData.buscarCuotasMensuales(alumno, new CicloEscolar("23-24"));
        if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
            System.out.println("No se encontraron cuotas mensuales para el alumno");
            throw new Exception("No se encontraron cuotas mensuales para el alumno");
        }
        List<Cuota> cuotas = feeData.buscarCuotas(alumno, new CicloEscolar("23-24"));
        if (cuotas == null || cuotas.isEmpty()) {
            System.out.println("No se encontraron cuotas  para el alumno");
            throw new Exception("No se encontraron cuotas para el alumno");
        }
        List<Pago> pagos = new ArrayList<>();
        //agregar pago de libros y uniformes
        pagos.add(new Pago("P202300002", LocalDateTime.of(2023, 8, 10, 11, 0, 0), 2350.00, 0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuota(Concepto.UNIFORMES, cuotas), 2000.0,new Pago("P202300002")),
        new DetallePago(feeData.buscarCuota(Concepto.LIBROS, cuotas), 350.0,new Pago("P202300002"))), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de agosto
        CuotaMensual cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.AUGUST);
        Double adeudoAcumulado = cuotaM.getAdeudoAcumulado(); 
        Double adeudoDelMes= 0.0; 
        pagos.add(new Pago("P202300007", LocalDateTime.of(2023, 8, 20, 11, 0, 0), 1200.00,
         0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1200.0,new Pago("P202300007"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        //agregar pago de colegiatura de septiembre, octubre y noviembre
        pagos.add(new Pago("P202300010", LocalDateTime.of(2023, 11, 5, 9, 20, 0), 3600.00,
        0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1200.0,new Pago("P202300010")),
        new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1200.0,new Pago("P202300010")),
        new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1200.0,new Pago("P202300010"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.OCTOBER);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        //agregar pago de colegiatura de diciembre y enero
        pagos.add(new Pago("P202400001", LocalDateTime.of(2024, 1, 15, 10, 40, 0), 2400.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1200.0,new Pago("P202400001")),
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1200.0,new Pago("P202400001"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.DECEMBER);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.JANUARY);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        //agregar pago de colegiatura de febrero y marzo
        pagos.add(new Pago("P202400004", LocalDateTime.of(2024, 3, 15, 9, 0, 0), 2400.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.FEBRUARY), 1200.0,new Pago("P202400004")),
        new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.MARCH), 1200.0,new Pago("P202400004"))), MetodoPago.TRANSFERENCIA));
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.MARCH);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }
        
        //agregar pago de colegiatura de abril
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.APRIL);
        pagos.add(new Pago("P202400006", LocalDateTime.of(2024, 4, 15, 11, 0, 0), 1200.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(cuotaM, 1200.0,new Pago("P202400006"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }
        
        //agregar pago de colegiatura de mayo
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.MAY);
        pagos.add(new Pago("P202400008", LocalDateTime.of(2024, 5, 20, 10, 30, 0), 1200.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(cuotaM, 1200.0,new Pago("P202400008"))), MetodoPago.TRANSFERENCIA));
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        return pagos;
    }

    private static void actualizarCuotaMensual(List<CuotaMensual> cuotas, Month mes, List<Pago> pagos){
        cuotaM = feeData.buscarCuotaMensual(cuotas, mes);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }
    }

    /**
     * Crea los objetos de tipo Pago en el ciclo 23-24 referente al alumno 2
     * @param cajero Usuario el cual se registrara en el pago
     * @return la lista de pagos generados
     * @throws Exception en caso de que no se encuentre el alumno o las cuotas mensuales
     */
    private List<Pago> crearPagos2324Alumno3(Usuario cajero, Alumno alumno) throws Exception {
        List<CuotaMensual> cuotasMensuales = feeData.buscarCuotasMensuales(alumno, new CicloEscolar("23-24"));
        if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
            System.out.println("No se encontraron cuotas mensuales para el alumno");
            throw new Exception("No se encontraron cuotas mensuales para el alumno");
        }
        List<Cuota> cuotas = feeData.buscarCuotas(alumno, new CicloEscolar("23-24"));
        if (cuotas == null || cuotas.isEmpty()) {
            System.out.println("No se encontraron cuotas para el alumno");
            throw new Exception("No se encontraron cuotas para el alumno");
        }
        List<Pago> pagos = new ArrayList<>();
        //agregar pago de inscripcion, eventos y libros
        pagos.add(new Pago("P202300001", LocalDateTime.of(2023, 8, 5, 10, 0, 0), 3450.00,
        0.0, "No aplica", cajero, alumno,
        List.of(
        new DetallePago(feeData.buscarCuota(Concepto.INSCRIPCION,cuotas), 2300.0,new Pago("P202300001")),
        new DetallePago(feeData.buscarCuota(Concepto.EVENTOS,cuotas), 800.0,new Pago("P202300001")),
        new DetallePago(feeData.buscarCuota(Concepto.LIBROS,cuotas), 350.0,new Pago("P202300001"))), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de agosto
        CuotaMensual cuotaM = feeData.buscarCuotaMensual(cuotasMensuales, Month.AUGUST);
        Double adeudoAcumulado = cuotaM.getAdeudoAcumulado(); 
        Double adeudoDelMes= 0.0; 
        pagos.add(new Pago("P202300005", LocalDateTime.of(2023, 8, 18, 9, 30, 0), 1300.00,
         0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1300.0,new Pago("P202300005"))), MetodoPago.EFECTIVO));
        cuotaM = feeData.buscarCuotaMensual(cuotasMensuales,Month.MARCH);
        cuotaM = feeData.actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
        adeudoDelMes = cuotaM.getAdeudoGeneradoDelMes();
        if(adeudoDelMes == 0.0){
            adeudoAcumulado = 0.0;
        }

        //agregar pago de colegiatura de septiembre
        pagos.add(new Pago("P202300009", LocalDateTime.of(2023, 9, 18, 9, 30, 0), 1300.00,
         0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1300.0,new Pago("P202300009"))), MetodoPago.TRANSFERENCIA));
        
        //agregar pago de colegiatura de octubre, noviembre y diciembre
        pagos.add(new Pago("P202300012", LocalDateTime.of(2023, 12, 1, 11, 0, 0), 3900.00,
        0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1300.0,new Pago("P202300012")),
        new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1300.0,new Pago("P202300012")),
        new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1300.0,new Pago("P202300012"))), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de enero, febrero, marzo y abril
        pagos.add(new Pago("P202400005", LocalDateTime.of(2024, 4, 10, 9, 15, 0), 5200.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1300.0,new Pago("P202400005")),
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY), 1300.0,new Pago("P202400005")),
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.MARCH), 1300.0,new Pago("P202400005")),
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.APRIL), 1300.0,new Pago("P202400005"))), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de mayo y junio
        pagos.add(new Pago("P202400009", LocalDateTime.of(2024, 6, 2, 10, 20, 0), 2600.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.MAY), 1300.0,new Pago("P202400009")),
        new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales, Month.JUNE), 1300.0,new Pago("P202400009"))), MetodoPago.TRANSFERENCIA));
        
        return pagos;
    }

    private List<Pago> crearPagos2425Alumno1(Usuario cajero, Alumno alumno) throws Exception {
        List<CuotaMensual> cuotasMensuales = feeData.buscarCuotasMensuales(alumno, new CicloEscolar("24-25"));
        if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
            System.out.println("No se encontraron cuotas mensuales para el alumno");
            throw new Exception("No se encontraron cuotas mensuales para el alumno");
        }
        
        List<Cuota> cuotas = feeData.buscarCuotas(alumno, new CicloEscolar("24-25"));
        if (cuotas == null || cuotas.isEmpty()) {
            System.out.println("No se encontraron cuotas para el alumno");
            throw new Exception("No se encontraron cuotas para el alumno");
        }
        
        List<Pago> pagos = new ArrayList<>();
        
        //pago por INSCRIPCION, UNIFORMES, LIBROS
        pagos.add(new Pago("P202400011", LocalDateTime.of(2024, 8, 1, 10, 0, 0), 4900.00, 
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuota(Concepto.INSCRIPCION,cuotas), 2500.0,new Pago("P202400011")), // INSCRIPCION
                new DetallePago(feeData.buscarCuota(Concepto.UNIFORMES,cuotas), 2000.0,new Pago("P202400011")), // UNIFORMES
                new DetallePago(feeData.buscarCuota(Concepto.LIBROS,cuotas), 400.0,new Pago("P202400011"))   // LIBROS
            ), MetodoPago.EFECTIVO));
        
        // AGOSTO COLEGIATURA
        pagos.add(new Pago("P202400015", LocalDateTime.of(2024, 8, 15, 10, 0, 0), 1800.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1800.0,new Pago("P202400015"))
            ), MetodoPago.EFECTIVO));
        
        // SEPTIEMBRE COLEGIATURA
        pagos.add(new Pago("P202400017", LocalDateTime.of(2024, 9, 15, 10, 0, 0), 1800.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1800.0,new Pago("P202400017"))
            ), MetodoPago.EFECTIVO));
        
        // OCTUBRE COLEGIATURA
        pagos.add(new Pago("P202400019", LocalDateTime.of(2024, 10, 15, 9, 0, 0), 1800.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1800.0,new Pago("P202400019"))
            ), MetodoPago.EFECTIVO));
        
        // NOVIEMBRE COLEGIATURA
        pagos.add(new Pago("P202400021", LocalDateTime.of(2024, 11, 15, 10, 0, 0), 1800.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1800.0,new Pago("P202400021"))
            ), MetodoPago.EFECTIVO));
        
        // DICIEMBRE COLEGIATURA
        pagos.add(new Pago("P202400026", LocalDateTime.of(2024, 12, 16, 10, 0, 0), 1800.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1800.0,new Pago("P202400026"))
            ), MetodoPago.EFECTIVO));
        
        // ENERO COLEGIATURA
        pagos.add(new Pago("P202500002", LocalDateTime.of(2025, 1, 15, 10, 0, 0), 1800.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1800.0,new Pago("P202500002"))
            ), MetodoPago.EFECTIVO));
        
        // ABRIL: 3 colegiaturas (FEB + MAR + ABR)
        pagos.add(new Pago("P202500008", LocalDateTime.of(2025, 4, 2, 10, 0, 0), 5400.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY), 1800.0,new Pago("P202500008")), // FEB
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.MARCH), 1800.0,new Pago("P202500008")), // MAR
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.APRIL), 1800.0,new Pago("P202500008"))  // ABR
            ), MetodoPago.EFECTIVO));
        
        return pagos;
    }
    
    private List<Pago> crearPagos2425Alumno3(Usuario cajero, Alumno alumno) throws Exception {
        List<CuotaMensual> cuotasMensuales = feeData.buscarCuotasMensuales(alumno, new CicloEscolar("24-25"));
        if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
            System.out.println("No se encontraron cuotas mensuales para el alumno");
            throw new Exception("No se encontraron cuotas mensuales para el alumno");
        }
        
        List<Cuota> cuotas = feeData.buscarCuotas(alumno, new CicloEscolar("24-25"));
        if (cuotas == null || cuotas.isEmpty()) {
            System.out.println("No se encontraron cuotas para el alumno");
            throw new Exception("No se encontraron cuotas para el alumno");
        }
        
        List<Pago> pagos = new ArrayList<>();
        
        //INSCRIPCION y LIBROS (falta UNIFORMES)
        pagos.add(new Pago("P202400012", LocalDateTime.of(2024, 8, 3, 10, 0, 0), 5400.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuota(Concepto.INSCRIPCION,cuotas), 2500.0,new Pago("P202400012")), // INSCRIPCION
                new DetallePago(feeData.buscarCuota(Concepto.LIBROS,cuotas), 400.0,new Pago("P202400012"))   // LIBROS
            ), MetodoPago.EFECTIVO));
        
        // OCTUBRE : colegiatura agosto,septiembre y octubre
        pagos.add(new Pago("P202400020", LocalDateTime.of(2024, 10, 15, 10, 0, 0), 4500.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1500.0,new Pago("P202400020")),
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1500.0,new Pago("P202400020")),
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1500.0,new Pago("P202400020"))
            ), MetodoPago.EFECTIVO));
        
        // NOVIEMBRE COLEGIATURA
        pagos.add(new Pago("P202400022", LocalDateTime.of(2024, 11, 15, 13, 0, 0), 1500.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1500.0,new Pago("P202400022"))
            ), MetodoPago.EFECTIVO));
        
        // DICIEMBRE COLEGIATURA
        pagos.add(new Pago("P202400025", LocalDateTime.of(2024, 12, 15, 10, 0, 0), 1500.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1500.0,new Pago("P202400025"))
            ), MetodoPago.EFECTIVO));
        
        // ENERO COLEGIATURA
        pagos.add(new Pago("P202500001", LocalDateTime.of(2025, 1, 10, 10, 0, 0), 1500.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1500.0,new Pago("P202500001"))
            ), MetodoPago.EFECTIVO));
        
        // FEBRERO COLEGIATURA
        pagos.add(new Pago("P202500005", LocalDateTime.of(2025, 2, 10, 12, 0, 0), 1500.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY), 1500.0,new Pago("P202500005"))
            ), MetodoPago.EFECTIVO));
        
        // MARZO COLEGIATURA
        pagos.add(new Pago("P202500006", LocalDateTime.of(2025, 3, 10, 10, 0, 0), 1500.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.MARCH), 1500.0,new Pago("P202500006"))
            ), MetodoPago.EFECTIVO));
        
        // ABRIL COLEGIATURA
        pagos.add(new Pago("P202500009", LocalDateTime.of(2025, 4, 7, 10, 0, 0), 1500.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.APRIL), 1500.0,new Pago("P202500009"))
            ), MetodoPago.EFECTIVO));
        
        return pagos;
    }
    
    private List<Pago> crearPagos2425Alumno2(Usuario cajero, Alumno alumno) throws Exception {
        List<CuotaMensual> cuotasMensuales = feeData.buscarCuotasMensuales(alumno, new CicloEscolar("24-25"));
        if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
            System.out.println("No se encontraron cuotas mensuales para el alumno");
            throw new Exception("No se encontraron cuotas mensuales para el alumno");
        }
        
        List<Cuota> cuotas = feeData.buscarCuotas(alumno, new CicloEscolar("24-25"));
        if (cuotas == null || cuotas.isEmpty()) {
            System.out.println("No se encontraron cuotas para el alumno");
            throw new Exception("No se encontraron cuotas para el alumno");
        }
        
        List<Pago> pagos = new ArrayList<>();
        
        // INSCRIPCION, UNIFORMES y LIBROS
        pagos.add(new Pago("P202400013", LocalDateTime.of(2024, 8, 3, 11, 0, 0), 6100.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuota(Concepto.UNIFORMES,cuotas), 2000.0,new Pago("P202400013")), // UNIFORMES
                new DetallePago(feeData.buscarCuota(Concepto.LIBROS,cuotas), 400.0,new Pago("P202400013")),  // LIBROS
                new DetallePago(feeData.buscarCuota(Concepto.INSCRIPCION,cuotas), 2500.0,new Pago("P202400013")),  // INSCRIPCION
                new DetallePago(feeData.buscarCuotaMensual(feeData.buscarCuotasMensuales(alumno, new CicloEscolar("23-24")),Month.JUNE), 1200.0,new Pago("P202400013"))  // COLEGIATURA JUNIO 23-24
            ), MetodoPago.EFECTIVO));
        
        // AGOSTO COLEGIATURA
        pagos.add(new Pago("P202400014", LocalDateTime.of(2024, 8, 10, 10, 0, 0), 1350.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1350.0,new Pago("P202400014"))
            ), MetodoPago.EFECTIVO));
        
        // SEPTIEMBRE COLEGIATURA
        pagos.add(new Pago("P202400016", LocalDateTime.of(2024, 9, 10, 10, 0, 0), 1350.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1350.0,new Pago("P202400016"))
            ), MetodoPago.EFECTIVO));
        
        // OCTUBRE COLEGIATURA
        pagos.add(new Pago("P202400018", LocalDateTime.of(2024, 10, 10, 10, 0, 0), 1350.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1350.0,new Pago("P202400018"))
            ), MetodoPago.EFECTIVO));
        
        // NOVIEMBRE COLEGIATURA
        pagos.add(new Pago("P202400023", LocalDateTime.of(2024, 11, 18, 10, 0, 0), 1350.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1350.0,new Pago("P202400023"))
            ), MetodoPago.EFECTIVO));
        
        // DICIEMBRE COLEGIATURA
        pagos.add(new Pago("P202400024", LocalDateTime.of(2024, 12, 10, 10, 0, 0), 1350.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1350.0,new Pago("P202400024"))
            ), MetodoPago.EFECTIVO));
        
        // ENERO COLEGIATURA
        pagos.add(new Pago("P202500003", LocalDateTime.of(2025, 1, 16, 10, 0, 0), 1350.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1350.0,new Pago("P202500003"))
            ), MetodoPago.EFECTIVO));
        
        // FEBRERO COLEGIATURA
        pagos.add(new Pago("P202500004", LocalDateTime.of(2025, 2, 10, 10, 0, 0), 1350.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY), 1350.0,new Pago("P202500004"))
            ), MetodoPago.EFECTIVO));
        
        // MARZO COLEGIATURA
        pagos.add(new Pago("P202500007", LocalDateTime.of(2025, 3, 20, 10, 0, 0), 1350.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.MARCH), 1350.0,new Pago("P202500007"))
            ), MetodoPago.EFECTIVO));
        
        // ABRIL COLEGIATURA
        pagos.add(new Pago("P202500010", LocalDateTime.of(2025, 4, 9, 10, 0, 0), 1350.00,
            0.0, "No aplica", cajero, alumno,
            List.of(
                new DetallePago(feeData.buscarCuotaMensual(cuotasMensuales,Month.APRIL), 1350.0,new Pago("P202500010"))
            ), MetodoPago.EFECTIVO));
        
        return pagos;
    }

}
