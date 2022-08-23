package br.com.digix.pokedigix.personagem;

import java.util.Collection;

public class TreinadorResponseDTO {

    private Long id;
    private String nome;
    private Endereco endereco;
    private int dinheiro;
    private int nivel;
    private Collection<Insignia> insignias;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getDinheiro() {
        return dinheiro;
    }
    public void setDinheiro(int dinheiro) {
        this.dinheiro = dinheiro;
    }
    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    public Collection<Insignia> getInsignias() {
        return insignias;
    }
    public void setInsignias(Collection<Insignia> insignias) {
        this.insignias = insignias;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
