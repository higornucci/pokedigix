package br.com.digix.pokedigix.personagem;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.digix.pokedigix.utils.EntidadeBase;

@Entity
public class Endereco extends EntidadeBase {

    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private String regiao;

    protected Endereco() {
    }

    public Endereco(String regiao, String cidade) {
        this.cidade = cidade;
        this.regiao = regiao;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

}
