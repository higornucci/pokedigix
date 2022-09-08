package br.com.digix.pokedigix.personagem;

public class EnderecoRequestDTO {

	private String cidade;
	private String regiao;

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
