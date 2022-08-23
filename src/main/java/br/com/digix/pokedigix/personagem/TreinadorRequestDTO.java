package br.com.digix.pokedigix.personagem;

import java.util.Collection;

public class TreinadorRequestDTO {
	private String nome;
	private Endereco endereco;
	private int nivel;
	private int dinheiro;
	private Collection<Insignia> insignias;

	public TreinadorRequestDTO(String nome, Endereco endereco, int nivel, int dinheiro,
			Collection<Insignia> insignias) {
		this.nome = nome;
		this.endereco = endereco;
		this.nivel = nivel;
		this.dinheiro = dinheiro;
		this.insignias = insignias;
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
	public void setInsignia(Collection<Insignia> insignias) {
		this.insignias = insignias;
	}

	
}
