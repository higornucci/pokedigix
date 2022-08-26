package br.com.digix.pokedigix.personagem;

public class EnderecoResponseDTO {
	private Long id;
    private String regiao;
    private String cidade;


    public EnderecoResponseDTO(Long id, String regiao, String cidade) {
	}

    public Long getId() {
        return id;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}   
}
