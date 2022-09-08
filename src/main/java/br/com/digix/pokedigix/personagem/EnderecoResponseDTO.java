package br.com.digix.pokedigix.personagem;

public class EnderecoResponseDTO {

	private Long id;
	private String cidade;
	private String regiao;

	public EnderecoResponseDTO(Long id, String regiao, String cidade) {
		this.id = id;
		this.cidade = cidade;
		this.regiao = regiao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

}
