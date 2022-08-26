package br.com.digix.pokedigix.personagem;

public class EnderecoResponseDTO {

    private Long id;
    private String regiao;
    private String cidade;

    public EnderecoResponseDTO(String regiao, String cidade, Long id) {
        this.regiao = regiao;
        this.cidade = cidade;
        this.id = id;
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

    protected EnderecoResponseDTO() {
    }
    
}
