package br.com.digix.pokedigix.personagem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String regiao;
    private String cidade;

    protected Endereco() {}

    public Endereco(String regiao, String cidade) {
        this.regiao = regiao;
        this.cidade = cidade;
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
