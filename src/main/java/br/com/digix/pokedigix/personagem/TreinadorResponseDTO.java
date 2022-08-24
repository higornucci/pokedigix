package br.com.digix.pokedigix.personagem;

import java.util.Collection;

public class TreinadorResponseDTO {

    private Long id;
    private String nome;
    private Endereco endereco;
    private int dinheiro;
    private int nivel;
    private Collection<Insignia> insignias;

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
}
