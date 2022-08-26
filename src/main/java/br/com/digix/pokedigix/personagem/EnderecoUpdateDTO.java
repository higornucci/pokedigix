package br.com.digix.pokedigix.personagem;

public class EnderecoUpdateDTO {
    private String cidade;
    private String regiao;
    
    public EnderecoUpdateDTO() {
    }
    
    public EnderecoUpdateDTO(String regiao, String cidade) {
        this.regiao = regiao;
        this.cidade = cidade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
}
