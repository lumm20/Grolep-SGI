package mx.itson.sgi.data_access;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataAccessApplication implements CommandLineRunner {

	@Autowired
	private DataInitializer init;
	@Autowired
	private DataCleanup cleanup;


	public static void main(String[] args) {
		SpringApplication.run(DataAccessApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		cleanup.truncateTables();
		init.initData();
	}
	
}
