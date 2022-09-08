package br.com.digix.pokedigix.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.personagem.Endereco;
import br.com.digix.pokedigix.personagem.EnderecoRepository;
import br.com.digix.pokedigix.personagem.Treinador;
import br.com.digix.pokedigix.personagem.TreinadorRepository;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PokemonRepositoryTest {

  @Autowired
  private PokemonRepository pokemonRepository;

  @Autowired
  private EnderecoRepository enderecoRepository;

  @Autowired
  private TreinadorRepository treinadorRepository;

  @Autowired
  private TipoRepository tipoRepository;

<<<<<<< HEAD
    @Test
    void deve_salvar_um_pokemon() throws Exception {
        Pokemon gastly = new PokemonBuilder().construir();
=======
  @AfterEach
  @BeforeEach
  void resetDb() {
    pokemonRepository.deleteAll();
    treinadorRepository.deleteAll();
    tipoRepository.deleteAll();
  }
>>>>>>> dev

  @Test
  void deve_salvar_um_pokemon() throws Exception {
    Pokemon gastly = new PokemonBuilder().construir();

    pokemonRepository.save(gastly);

<<<<<<< HEAD
    @Test
    void deve_salvar_um_pokemon_com_um_tipo() throws Exception {
        int quantidadeDeTiposEsperada = 1;
        Tipo fantasma = new Tipo("Fantasma");
        Pokemon gastly = new PokemonBuilder().comTipo(fantasma).construir();
        pokemonRepository.save(gastly);
=======
    assertNotNull(gastly.getId());
  }
>>>>>>> dev

  @Test
  void deve_salvar_um_pokemon_com_um_tipo() throws Exception {
    int quantidadeDeTiposEsperada = 1;
    Tipo fantasma = new Tipo("Fantasma");
    Pokemon gastly = new PokemonBuilder().comTipo(fantasma).construir();
    pokemonRepository.save(gastly);

    Pokemon gastlyRetornado = pokemonRepository.findById(gastly.getId()).get();

<<<<<<< HEAD
    @Test
    void deve_salvar_um_pokemon_com_ataque() throws Exception {
        Ataque ataque = new AtaqueBuilder().construir();
        Pokemon pokemon = new PokemonBuilder().comAtaque(ataque).construir();
=======
    assertNotNull(gastlyRetornado.getTipos());
    assertEquals(quantidadeDeTiposEsperada, gastlyRetornado.getTipos().size());
    assertTrue(gastlyRetornado.getTipos().contains(fantasma));
  }
>>>>>>> dev

  @Test
  void deve_salvar_um_pokemon_com_ataque() throws Exception {
    Ataque ataque = new AtaqueBuilder().construir();
    Pokemon pokemon = new PokemonBuilder().comAtaque(ataque).construir();

    pokemonRepository.save(pokemon);

    assertTrue(pokemon.getAtaques().contains(ataque));
  }

<<<<<<< HEAD
    void deve_buscar_um_pokemon_pelo_seu_tipo() throws NivelPokemonInvalidoException,
            FelicidadeInvalidaException, LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {
=======
  @Test
  void deve_buscar_um_pokemon_pelo_seu_tipo()
    throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {
    Tipo tipo = new Tipo("Psíquico");
    Pokemon pokemon = new PokemonBuilder().comTipo(tipo).construir();
    tipoRepository.save(tipo);
>>>>>>> dev

    pokemonRepository.save(pokemon);

    Collection<Pokemon> pokemonsRetornados = pokemonRepository.buscarPorTipo(
      tipo.getId()
    );
    assertTrue(pokemonsRetornados.contains(pokemon));
  }

  @Test
  void deve_buscar_um_pokemon_por_nome() throws Exception {
    Pokemon pokemon = new PokemonBuilder().comNome("Gastly").construir();
    pokemonRepository.save(pokemon);

<<<<<<< HEAD
    @Test
    void deve_buscar_um_pokemon_por_nome() throws Exception {
        Pokemon pokemon = new PokemonBuilder().comNome("Gastly").construir();
        pokemonRepository.save(pokemon);
=======
    Collection<Pokemon> pokemonRetornado = pokemonRepository.findByNomeContaining(
      "Gastly"
    );
>>>>>>> dev

    assertTrue(pokemonRetornado.contains(pokemon));
  }

  @Test
  void pokemon_sem_treinador_e_selvagem() throws Exception {
    Pokemon pokemon = new PokemonBuilder().construir();
    pokemonRepository.save(pokemon);

<<<<<<< HEAD
    @Test
    void pokemon_sem_treinador_e_selvagem() throws Exception {
        Pokemon pokemon = new PokemonBuilder().construir();
        pokemonRepository.save(pokemon);
=======
    assertTrue(pokemon.isSelvagem());
  }
>>>>>>> dev

  @Test
  void pokemon_com_treinador_nao_e_selvagem() throws Exception {
    Pokemon pokemon = new PokemonBuilder().construir();
    Endereco endereco = new Endereco("Norte", "campo grande");
    Treinador treinador = new Treinador("Ash", endereco, pokemon);
    pokemonRepository.save(pokemon);
    enderecoRepository.save(endereco);
    treinadorRepository.save(treinador);

<<<<<<< HEAD
    @Test
    void pokemon_com_treinador_nao_e_selvagem() throws Exception {
        Pokemon pokemon = new PokemonBuilder().construir();
        Endereco endereco = new Endereco("Norte", "campo grande");
        Treinador treinador = new Treinador("Ash", endereco, pokemon);
        pokemonRepository.save(pokemon);
        enderecoRepository.save(endereco);
        treinadorRepository.save(treinador);
=======
    assertFalse(pokemon.isSelvagem());
  }
>>>>>>> dev

  @Test
  void pode_nao_ter_genero() throws Exception {
    Genero genero = null;
    Pokemon pokemon = new PokemonBuilder().comGenero(genero).construir();
    pokemonRepository.save(pokemon);

<<<<<<< HEAD
    @Test
    void pode_nao_ter_genero() throws Exception {
        Genero genero = null;
        Pokemon pokemon = new PokemonBuilder().comGenero(genero).construir();
        pokemonRepository.save(pokemon);
=======
    Collection<Pokemon> pokemonRetornado = pokemonRepository.findByNomeContaining(
      "Gastly"
    );
>>>>>>> dev

    assertTrue(pokemonRetornado.contains(pokemon));
  }

  @Test
  void pode_ter_um_genero_indefinido() throws Exception {
    Genero genero = Genero.INDEFINIDO;
    Pokemon pokemon = new PokemonBuilder().comGenero(genero).construir();
    pokemonRepository.save(pokemon);

<<<<<<< HEAD
    @Test
    void pode_ter_um_genero_indefinido() throws Exception {
        Genero genero = Genero.INDEFINIDO;
        Pokemon pokemon = new PokemonBuilder().comGenero(genero).construir();
        pokemonRepository.save(pokemon);
=======
    Pokemon pokemonRetornado = pokemonRepository
      .findById(pokemon.getId())
      .get();
>>>>>>> dev

    assertEquals(Genero.INDEFINIDO, pokemonRetornado.getGenero());
  }
}
