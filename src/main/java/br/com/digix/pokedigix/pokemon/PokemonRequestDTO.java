package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

public class PokemonRequestDTO extends PokemonDTO {

	private Collection<Long> tiposIds;
	private Collection<Long> ataquesIds;

	protected PokemonRequestDTO() {
	}

	public PokemonRequestDTO(String nome, double altura, double peso, Genero genero, int nivel, int numeroPokedex,
			int felicidade, Collection<Long> tiposIds, Collection<Long> ataquesIds) {
		super(nome, altura, peso, genero, nivel, numeroPokedex, felicidade);
		this.tiposIds = tiposIds;
		this.ataquesIds = ataquesIds;
	}

	public Collection<Long> getAtaquesIds() {
		return ataquesIds;
	}

	public void setAtaquesIds(Collection<Long> ataquesIds) {
		this.ataquesIds = ataquesIds;
	}

	public Collection<Long> getTiposIds() {
		return tiposIds;
	}

	public void setTiposIds(Collection<Long> tiposIds) {
		this.tiposIds = tiposIds;
	}

}