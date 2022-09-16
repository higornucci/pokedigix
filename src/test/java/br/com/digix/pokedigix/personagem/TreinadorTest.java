package br.com.digix.pokedigix.personagem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonBuilder;
import org.junit.jupiter.api.Test;

class TreinadorTest {

	@Test
	void deve_comecar_com_um_pokemon() throws Exception {
		// Arrange
		int quantidadeDePokemonsEsperada = 1;
		Pokemon pokemonInicial = new PokemonBuilder().construir();

		// Action
		Treinador treinador = new TreinadorBuilder()
				.comPokemonInicial(pokemonInicial)
				.construir();

		// Assert
		assertTrue(treinador.getPokemons().contains(pokemonInicial));
		assertEquals(quantidadeDePokemonsEsperada, treinador.getPokemons().size());
	}

	@Test
	void deve_poder_capturar_um_pokemon() throws Exception {
		int quantidadeDePokemonsEsperada = 2;
		Pokemon pokemonACapturar = new PokemonBuilder().construir();

		Treinador treinador = new TreinadorBuilder().construir();
		// Action
		treinador.capturar(pokemonACapturar);

		assertTrue(treinador.getPokemons().contains(pokemonACapturar));
		assertEquals(quantidadeDePokemonsEsperada, treinador.getPokemons().size());
	}

	@Test
	void nao_pode_ter_mais_que_seis_pokemons_ao_mesmo_tempo() throws Exception {
		Treinador treinador = new TreinadorBuilder().construir();
		for (int i = 1; i <= 5; i++) {
			treinador.capturar(new PokemonBuilder().construir());
		}
		assertThrows(
				LimiteDePokemonException.class,
				() -> {
					treinador.capturar(new PokemonBuilder().construir());
				});
	}

	@Test
	void deve_ter_ate_seis_pokemons() throws Exception {
		int quantidadeDePokemonsEsperada = 6;
		Treinador treinador = new TreinadorBuilder().construir();
		for (int i = 1; i <= 5; i++) {
			treinador.capturar(new PokemonBuilder().construir());
		}

		assertEquals(quantidadeDePokemonsEsperada, treinador.getPokemons().size());
	}

}
