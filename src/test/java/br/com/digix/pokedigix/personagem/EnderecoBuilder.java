package br.com.digix.pokedigix.personagem;

public class EnderecoBuilder {

	private String regiao;
	private String cidade;

	public EnderecoBuilder() {
		this.cidade = "Pallet";
		this.regiao = "Kanto";
	}

	public Endereco construir() {
		return new Endereco(regiao, cidade);
	}

}
