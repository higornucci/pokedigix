package br.com.digix.pokedigix.personagem;

import java.util.Collection;

import javax.persistence.metamodel.PluralAttribute.CollectionType;

import br.com.digix.pokedigix.pokemon.Pokemon;

public class TreinadorResponseDTO {
    private Long id; 
    private String nome;
    private Endereco endereco;
    private int dinheiro;    
    private int nivel;
    private Collection<Insignia> insignia;
    public TreinadorResponseDTO(Long id, String nome, Endereco endereco, int dinheiro, int nivel,
            Collection<Insignia> insignia) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.dinheiro = dinheiro;
        this.nivel = nivel;
        this.insignia = insignia;
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
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
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
    public Collection<Insignia> getInsignia() {
        return insignia;
    }
    public void setInsignia(Collection<Insignia> insignia) {
        this.insignia = insignia;
    }
    
    
    
}
