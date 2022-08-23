package br.com.digix.pokedigix.personagem;

import java.util.Collection;

public class TreinadorResponseDTO {
	
	private Long id;
	private String nome;
	private Endereco endereco;
	private int nivel;
	private int dinheiro;
	private Collection<Insignia> insignias;

	public TreinadorResponseDTO(Long id, String nome, Endereco endereco, int nivel, int dinheiro, Collection<Insignia> insignias) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.nivel = nivel;
		this.dinheiro = dinheiro;
		this.insignias = insignias;
	}
	public Long getId() {
		return id;
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
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getDinheiro() {
		return dinheiro;
	}
	public void setDinheiro(int dinheiro) {
		this.dinheiro = dinheiro;
	}
	public Collection<Insignia> getInsignias() {
		return insignias;
	}
	public void setInsignias(Collection<Insignia> insignias) {
		this.insignias = insignias;
	}

}
