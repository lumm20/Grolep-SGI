package mx.itson.sgi.data_access;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import mx.itson.sgi.data_access.services.AlumnoService;
import mx.itson.sgi.data_access.services.CuotaService;
import mx.itson.sgi.data_access.services.PagoService;
import mx.itson.sgi.data_access.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import mx.itson.sgi.data_access.dto.AdeudoDTO;
import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.Beca;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.DetallePago;
import mx.itson.sgi.data_access.entities.MetodoPago;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.data_access.entities.Rol;
import mx.itson.sgi.data_access.entities.Usuario;

@SpringBootApplication
public class DataAccessApplication implements CommandLineRunner {

	@Autowired
	UsuarioService userController;

	@Autowired
	CuotaService cuotaController;
	@Autowired
	AlumnoService alumnoController;
	@Autowired
	PagoService pagoController;


	public static void main(String[] args) {
		SpringApplication.run(DataAccessApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		registrarPago();
	}
	
	
	@Transactional(readOnly = true)
	private void obtenerAdeudos(){
		List<AdeudoDTO> adeudos = cuotaController.obtenerCuotasConAdeudos();
	
		if (!adeudos.isEmpty()) {
			for (AdeudoDTO a : adeudos) {
				System.out.println(a);
			}
		}
	}
	@Transactional(readOnly = true)
	private void obtenerAdeudosPorAlumno(){
		List<AdeudoDTO> adeudos = cuotaController.obtenerCuotasConAdeudosPorAlumno("A20220003","23-24");
	
		if (!adeudos.isEmpty()) {
			for (AdeudoDTO a : adeudos) {
				System.out.println(a);
			}
		}
	}

	@Transactional
	private void registrarPago(){
		Alumno alumno = alumnoController.buscarAlumnoPorMatricula("A20220003");
		Pago pago = new Pago(pagoController.generarFolio(),
		LocalDateTime.of(2023, 10, 10, 8, 30),
		0.0,new Usuario(2L),MetodoPago.EFECTIVO,alumno);
		// pago.setFolio(pagoController.generarFolio());
		// pago.setAlumno(alumno);
		// pago.setCajero(new Usuario(2L));
		// pago.setFecha(LocalDateTime.of(2023, 10, 10, 8, 30));
		// pago.setMetodoPago(MetodoPago.EFECTIVO);
		// pago.setMontoTotal(montoTotal);
		List<AdeudoDTO> adeudos = cuotaController.obtenerCuotasConAdeudosPorAlumno("A20220003","23-24");
		Double montoTotal =0.0;
		
		for (AdeudoDTO adeudoDTO : adeudos) {
			System.out.println(adeudoDTO);
			// detalle = new DetallePago(pago,adeudoDTO.getCuota(),adeudoDTO.getAdeudo());
			montoTotal+=adeudoDTO.getAdeudo();
			// System.out.println(detalle);
			pago.getDetalles().add(new DetallePago(adeudoDTO.getCuota(),adeudoDTO.getAdeudo(),pago));
		}
		pago.setMontoTotal(montoTotal);
		System.out.println(pago);
		pagoController.registrarPago(pago);
	}

	@Transactional
	private void registrarCuota() {
		CicloEscolar ciclo = cuotaController.obtenerCicloEscolarPorId("24-25");
		Alumno alumno = alumnoController.buscarAlumnoPorMatricula("A20220002");
		if (alumno != null) {
			Cuota nuevaCuota = new Cuota(2500.00, Concepto.INSCRIPCION);
			nuevaCuota.setAlumno(alumno);
			nuevaCuota.setCiclo(ciclo);
			cuotaController.agregarCuota(nuevaCuota);
		}
	}

	@Transactional
	private void registrarAlumno() {
		Alumno alumno = new Alumno("A20220004",
		"Lucia","Ruiz Dorame",
		Beca.DEPORTIVA,
		"6441223344",
		LocalDate.of(2015, 7, 12));
		List<Cuota> cuotas = new ArrayList<>();
		Cuota c1 = new Cuota(2500.00, new CicloEscolar("23-24"),Concepto.INSCRIPCION);
		Cuota c2 = new Cuota(1800.00, new CicloEscolar("23-24"),Concepto.COLEGIATURA);
		Cuota c3 = new Cuota(2000.00, new CicloEscolar("23-24"),Concepto.UNIFORMES);
		cuotas.add(c1);
		cuotas.add(c2);
		cuotas.add(c3);
		alumno.setCuotas(cuotas);
		c1.setAlumno(alumno);
		c2.setAlumno(alumno);
		c3.setAlumno(alumno);
		if(alumnoController.buscarAlumnoPorMatricula("A20220004") == null){
			alumnoController.registrarAlumno(alumno);
		}else{
			System.out.println("Ya existe un alumno con esa matricula");
		}
	}

	private void registrarUsuario() {
		Usuario user = new Usuario("Luisa10", "moel10", Rol.ADMINISTRADOR,
		"luisa@correo.com");
		Usuario newUser = userController.registrarUsuario(user);
		System.out.println(newUser);
	}

}
