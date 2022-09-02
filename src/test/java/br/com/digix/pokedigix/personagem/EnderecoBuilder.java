package br.com.digix.pokedigix.personagem;

public class EnderecoBuilder {

    private String regiao;
    private String cidade;

    public EnderecoBuilder() {
        this.regiao = "Kanto";
        this.cidade = "Pallet";
    }

    public EnderecoBuilder comCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public Endereco construir() {
        return new Endereco(regiao, cidade);
    }

    public EnderecoBuilder comRegiao(String regiao) {
        this.regiao = regiao;
        return this;
    }

   

}
