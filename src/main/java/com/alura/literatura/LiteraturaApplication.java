package com.alura.literatura;

import com.alura.literatura.principal.Principal;
import com.alura.literatura.repository.AutorRepositorio;
import com.alura.literatura.repository.IdiomaRepositorio;
import com.alura.literatura.repository.LibroRepositorio;
import com.alura.literatura.service.SLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private SLibro sLibro;

	@Autowired
	private LibroRepositorio libroRepositorio;

	@Autowired
	private AutorRepositorio autorRepositorio;

	@Autowired
	private IdiomaRepositorio idiomaRepositorio;



	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(sLibro, libroRepositorio,autorRepositorio,idiomaRepositorio);
		principal.menu();
	}
}
