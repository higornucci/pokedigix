package br.com.digix.pokedigix.personagem;

import java.util.Collection;


public class TreinadorResponseDTO {
    private Long id;
    private Endereco endereco;
    private String nome;
    private Collection<Insignia> insignias;
    private int nivel;
    private int dinheiro;
    public TreinadorResponseDTO(Long id, Endereco endereco, String nome, Collection<Insignia> insignias, int nivel,
            int dinheiro) {
        this.id = id;
        this.endereco = endereco;
        this.nome = nome;
        this.insignias = insignias;
        this.nivel = nivel;
        this.dinheiro = dinheiro;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Collection<Insignia> getInsignias() {
        return insignias;
    }
    public void setInsignias(Collection<Insignia> insignias) {
        this.insignias = insignias;
    }
    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    public int getDinheiro() {
        return dinheiro;
    }
    public void setDinheiro(int dinheiro) {
        this.dinheiro = dinheiro;
    }
}
