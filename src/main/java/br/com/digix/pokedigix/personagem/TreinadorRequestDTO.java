package br.com.digix.pokedigix.personagem;

public class TreinadorRequestDTO {
	private Long idEndereco;
	private String nome;
	private Long idPrimeiroPokemon;
	
	public TreinadorRequestDTO() {}

	public TreinadorRequestDTO(Long idEndereco, String nome, Long idPrimeiroPokemon) {
		this.idEndereco = idEndereco;
		this.nome = nome;
		this.idPrimeiroPokemon = idPrimeiroPokemon;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdPrimeiroPokemon() {
		return idPrimeiroPokemon;
	}

	public void setIdPrimeiroPokemon(Long idPrimeiroPokemon) {
		this.idPrimeiroPokemon = idPrimeiroPokemon;
	}

	public Long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}
}