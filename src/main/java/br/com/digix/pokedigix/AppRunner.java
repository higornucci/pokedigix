package br.com.digix.pokedigix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.digix.pokedigix.pokemon.PokemonRepository;

@Component
public class AppRunner implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

  private final PokemonRepository pokemonRepository;

  public AppRunner(PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    logger.info(".... Carregando Pokemons");
    logger.info("Pokemon Pikachu -->" + pokemonRepository.find("Pikachu"));
    logger.info("Pokemon Raichu -->" + pokemonRepository.find("Raichu"));
    logger.info("Pokemon Pikachu 2 -->" + pokemonRepository.find("Pikachu"));
    logger.info("Pokemon Gengar -->" + pokemonRepository.find("Gengar"));
    logger.info("Pokemon Pikachu 3 -->" + pokemonRepository.find("Pikachu"));
    logger.info("Pokemon Gengar 2 -->" + pokemonRepository.find("Gengar"));
    logger.info("Pokemon Pichu -->" + pokemonRepository.find("Pichu"));
  }

}
