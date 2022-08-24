package br.com.digix.pokedigix.personagem;

import java.util.Collection;

public class TreinadorUpdateDTO {
	private String nome;
	private Long enderecoId;
	private int nivel;
	private int dinheiro;
	private Collection<Insignia> insignias;

	protected TreinadorUpdateDTO() {}

	public TreinadorUpdateDTO(String nome, Long enderecoId, int nivel, int dinheiro,
			Collection<Insignia> insignias) {
		this.nome = nome;
		this.enderecoId = enderecoId;
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

	public Long getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(Long enderecoId) {
		this.enderecoId = enderecoId;
	}

	public void setInsignias(Collection<Insignia> insignias) {
		this.insignias = insignias;
	}
}
