package br.com.digix.pokedigix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class PokedigixApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedigixApplication.class, args);
	}

}
