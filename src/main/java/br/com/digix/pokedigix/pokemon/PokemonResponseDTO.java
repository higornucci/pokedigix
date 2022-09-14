package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import br.com.digix.pokedigix.ataque.AtaqueResponseDTO;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;

public class PokemonResponseDTO extends PokemonModelo {
    private Long id;
    private Collection<AtaqueResponseDTO> ataques;
    private Collection<TipoResponseDTO> tipos;

    protected PokemonResponseDTO() {
    }

    public PokemonResponseDTO(Long id, String nome, double altura, double peso, Genero genero, int nivel,
            int numeroPokedex,
            int felicidade, Collection<AtaqueResponseDTO> ataques, Collection<TipoResponseDTO> tipos) {
        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.genero = genero;
        this.nivel = nivel;
        this.numeroPokedex = numeroPokedex;
        this.felicidade = felicidade;
        this.ataques = ataques;
        this.tipos = tipos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<AtaqueResponseDTO> getAtaques() {
        return ataques;
    }

    public void setAtaques(Collection<AtaqueResponseDTO> ataques) {
        this.ataques = ataques;
    }

    public Collection<TipoResponseDTO> getTipos() {
        return tipos;
    }

    public void setTipos(Collection<TipoResponseDTO> tipos) {
        this.tipos = tipos;
    }

}
