package br.com.digix.pokedigix.tipo;

public class TipoRequestDTO {

    private String nome;

    protected TipoRequestDTO(){}
    
    public TipoRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}