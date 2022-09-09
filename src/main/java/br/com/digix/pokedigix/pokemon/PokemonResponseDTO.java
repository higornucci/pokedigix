package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import br.com.digix.pokedigix.ataque.AtaqueResponseDTO;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;

public class PokemonResponseDTO {

	private Long id;
	private String nome;
	private double altura;
	private double peso;
	private Genero genero;
	private int nivel;
	private int numeroPokedex;
	private int felicidade;
	private Collection<AtaqueResponseDTO> ataques;
	private Collection<TipoResponseDTO> tipos;

	public Long getId() {
		return id;
	}

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

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getNumeroPokedex() {
		return numeroPokedex;
	}

	public void setNumeroPokedex(int numeroPokedex) {
		this.numeroPokedex = numeroPokedex;
	}

	public int getFelicidade() {
		return felicidade;
	}

	public void setFelicidade(int felicidade) {
		this.felicidade = felicidade;
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
