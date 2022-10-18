package br.com.digix.pokedigix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PokedigixApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedigixApplication.class, args);
	}

}
