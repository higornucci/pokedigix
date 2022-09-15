package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.personagem.Treinador;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.utils.EntidadeBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pokemon extends EntidadeBase {
	private static final int LIMITE_ATAQUES = 4;

	private static final int LIMITE_TIPOS = 2;

	@Column(length = 15, nullable = false)
	private String nome;

	@Column(nullable = false)
	private double altura;

	@Column(nullable = false)
	private double peso;

	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = true)
	private Genero genero;

	@Column(nullable = false)
	private int nivel;

	@Column(nullable = false)
	private int numeroPokedex;

	@Column(nullable = false)
	private int felicidade;

	@ManyToOne
	private Treinador treinador;

	public boolean isSelvagem() {
		return treinador == null;
	}

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "pokemon_tipo", joinColumns = @JoinColumn(name = "pokemon_id"), inverseJoinColumns = @JoinColumn(name = "tipo_id"))
	private Collection<Tipo> tipos;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "pokemon_ataque", joinColumns = @JoinColumn(name = "pokemon_id"), inverseJoinColumns = @JoinColumn(name = "ataque_id"))
	private Collection<Ataque> ataques;

	public Pokemon(String nome, double altura, double peso, Genero genero, int nivel, int numeroPokedex,
			int felicidade, Collection<Tipo> tipos, Collection<Ataque> ataques)
			throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException,
			LimiteDeAtaquePokemonException {
		validarNivel(nivel);
		validarFelicidade(felicidade);
		this.nome = nome;
		this.altura = altura;
		this.peso = peso;
		this.genero = genero;
		this.nivel = nivel;
		this.numeroPokedex = numeroPokedex;
		this.felicidade = felicidade;
		setTipos(tipos);
		setAtaques(ataques);
	}

	public void setAtaques(Collection<Ataque> ataques) throws LimiteDeAtaquePokemonException {
		if (ataques.size() > LIMITE_ATAQUES) {
			throw new LimiteDeAtaquePokemonException();
		}
		this.ataques = ataques;
	}

	public void setTipos(Collection<Tipo> tipos) throws LimiteDeTipoPokemonException {
		if (tipos.size() > LIMITE_TIPOS) {
			throw new LimiteDeTipoPokemonException();
		}
		this.tipos = tipos;
	}

	private void validarFelicidade(int felicidade) throws FelicidadeInvalidaException {
		if (felicidade < 0 || felicidade > 100) {
			throw new FelicidadeInvalidaException();
		}
	}

	private void validarNivel(int nivel) throws NivelPokemonInvalidoException {
		if (nivel < 1 || nivel > 100) {
			throw new NivelPokemonInvalidoException();
		}

	}
}
