package br.com.digix.pokedigix.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.tipo.Tipo;

public class PokemonTest {
    @Test
    public void deve_criar_um_pokemon() throws Exception {
        String nome = "Gastly";
        double altura = 0.8;
        double peso = 0.1;
        Genero genero = Genero.MASCULINO;
        int nivel = 5;
        int numeroPokedex = 92;
        int felicidade = 0;
        List<Tipo> tipos = new ArrayList<>();
        tipos.add(new Tipo("Fantasma"));
        List<Ataque> ataques = new ArrayList<>();
        ataques.add(new AtaqueBuilder().construir());

        Pokemon gastly = new Pokemon(nome,
                altura,
                peso,
                genero,
                nivel,
                numeroPokedex,
                felicidade,
                tipos,
                ataques);

        assertEquals(nome, gastly.getNome());
        assertEquals(altura, gastly.getAltura());
        assertEquals(peso, gastly.getPeso());
        assertEquals(genero, gastly.getGenero());
        assertEquals(nivel, gastly.getNivel());
        assertEquals(numeroPokedex, gastly.getNumeroPokedex());
        assertEquals(felicidade, gastly.getFelicidade());
    }

    @Test
    public void deve_cadastrar_um_tipo_para_o_pokemon() throws Exception {
        Tipo tipo = new Tipo("Fantasma");

        Pokemon gastly = new PokemonBuilder().comTipo(tipo).construir();

        assertTrue(gastly.getTipos().contains(tipo));
    }

    @Test
    public void nao_deve_possuir_mais_que_dois_tipos() throws Exception {

        assertThrows(LimiteDeTipoPokemonException.class, () -> {
            new PokemonBuilder()
                    .comTipo(new Tipo("Fogo"))
                    .comTipo(new Tipo("Fantasma"))
                    .comTipo(new Tipo("Planta"))
                    .construir();
        });
    }

    @Test
    public void nao_deve_possuir_mais_que_quatro_ataques() throws Exception {

        assertThrows(LimiteDeAtaquePokemonException.class, () -> {
            new PokemonBuilder()
                    .comAtaque(new AtaqueBuilder().construir())
                    .comAtaque(new AtaqueBuilder().construir())
                    .comAtaque(new AtaqueBuilder().construir())
                    .comAtaque(new AtaqueBuilder().construir())
                    .comAtaque(new AtaqueBuilder().construir())
                    .construir();
        });
    }

    @Test
    public void deve_permitir_ter_ate_dois_tipos() throws Exception {

        int quantidadeDeAtaquesEsperada = 2;
        Pokemon pokemon = new PokemonBuilder()
                .comTipo(new Tipo("Fogo"))
                .comTipo(new Tipo("Fantasma"))
                .construir();

        assertEquals(quantidadeDeAtaquesEsperada, pokemon.getTipos().size());
    }

    @Test
    public void deve_permitir_ter_ate_quatro_ataques() throws Exception {

        int quantidadeDeAtaquesEsperada = 4;
        Pokemon pokemon = new PokemonBuilder().comAtaque(new AtaqueBuilder().construir())
                .comAtaque(new AtaqueBuilder().construir())
                .comAtaque(new AtaqueBuilder().construir())
                .comAtaque(new AtaqueBuilder().construir())
                .construir();

        assertEquals(quantidadeDeAtaquesEsperada, pokemon.getAtaques().size());
    }

    @Test
    public void deve_ter_nivel_minimo_um() throws Exception {
        int nivelMinimo = 1;

        Pokemon pokemon = new PokemonBuilder().comNivel(nivelMinimo).construir();

        assertEquals(nivelMinimo, pokemon.getNivel());
    }

    @Test
    public void deve_ter_nivel_maximo_cem() throws Exception {
        int nivelMaximo = 100;

        Pokemon pokemon = new PokemonBuilder().comNivel(nivelMaximo).construir();

        assertEquals(nivelMaximo, pokemon.getNivel());
    }

    @Test
    public void nao_pode_ter_nivel_menor_que_um() {
        int nivelInvalido = 0;

        assertThrows(NivelPokemonInvalidoException.class, () -> {
            new PokemonBuilder().comNivel(nivelInvalido).construir();
        });
    }

    @Test
    public void nao_pode_ter_nivel_maior_que_cem() {
        int nivelInvalido = 101;

        assertThrows(NivelPokemonInvalidoException.class, () -> {
            new PokemonBuilder().comNivel(nivelInvalido).construir();
        });
    }

    @Test
    public void deve_ter_felicidade_minima_zero() throws Exception {
        int felicidadeMinima = 0;

        Pokemon pokemon = new PokemonBuilder().comFelicidade(felicidadeMinima).construir();

        assertEquals(felicidadeMinima, pokemon.getFelicidade());
    }

    @Test
    public void deve_ter_felicidade_maxima_cem() throws Exception {
        int felicidadeMaxima = 100;

        Pokemon pokemon = new PokemonBuilder().comFelicidade(felicidadeMaxima).construir();

        assertEquals(felicidadeMaxima, pokemon.getFelicidade());
    }

    @Test
    public void nao_deve_ter_felicidade_menor_que_zero() {
        int felicidadeInvalida = -1;

        assertThrows(FelicidadeInvalidaException.class, () -> {
            new PokemonBuilder().comFelicidade(felicidadeInvalida).construir();
        });
    }

    @Test
    public void nao_deve_ter_felicidade_maior_que_cem() {
        int felicidadeInvalida = 101;

        assertThrows(FelicidadeInvalidaException.class, () -> {
            new PokemonBuilder().comFelicidade(felicidadeInvalida).construir();
        });
    }
    
}
