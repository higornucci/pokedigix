package br.com.digix.pokedigix.personagem;

public class EnderecoRequestDTO {

  private String cidade;
  private String regiao;
  
public EnderecoRequestDTO(){

}
  public EnderecoRequestDTO(String regiao, String cidade) {
    this.regiao = regiao;
    this.cidade = cidade;
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
