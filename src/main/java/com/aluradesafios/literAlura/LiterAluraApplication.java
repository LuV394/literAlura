package com.aluradesafios.literAlura;

import com.aluradesafios.literAlura.model.DatosLibros;
import com.aluradesafios.literAlura.principal.Principal;
import com.aluradesafios.literAlura.service.ConsumoAPI;
import com.aluradesafios.literAlura.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();

	}
}
