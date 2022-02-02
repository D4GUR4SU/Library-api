package com.dagurasu.libraryapi;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dagurasu.libraryapi.api.service.EmailService;

@EnableScheduling
@SpringBootApplication
public class LibraryApiApplication {

	@Autowired
	private EmailService emailService;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public CommandLineRunner runner() {
		return args -> {
			List<String> emails = Arrays.asList("library-api@gmail.com");
			emailService.sendEmails("Testando serviço de e-mails", emails);
			System.out.println("E-mail enviado com sucesso!");
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}

}
