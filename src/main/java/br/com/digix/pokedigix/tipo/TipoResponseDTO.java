package br.com.digix.pokedigix.tipo;

public class TipoResponseDTO {

    private Long id;
    private String nome;
    
    protected TipoResponseDTO() {}

    public TipoResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}