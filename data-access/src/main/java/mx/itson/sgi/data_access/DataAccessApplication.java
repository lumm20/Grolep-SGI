package mx.itson.sgi.data_access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mx.itson.sgi.data_access.controllers.UsuarioController;
import mx.itson.sgi.data_access.entities.Rol;
import mx.itson.sgi.data_access.entities.Usuario;

@SpringBootApplication
public class DataAccessApplication implements CommandLineRunner {

	@Autowired
	UsuarioController controller;

	public static void main(String[] args) {
		SpringApplication.run(DataAccessApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Usuario user = new Usuario("Luisa10", "moel10", Rol.ADMIN, "luisa@correo.com");
		Usuario newUser = controller.registrarUsuario(user);
		System.out.println(newUser);
	}

}
