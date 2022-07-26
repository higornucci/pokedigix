package br.com.digix.pokedigix.ataque;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.digix.pokedigix.tipo.Tipo;

@Entity
public class Ataque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private int forca;

    @Column(nullable = false)
    private int acuracia;
    
    @Column(nullable = false, name = "pp")
    private int pontosDePoder;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Categoria categoria;
    
    @Column(nullable = false, length = 30)
    private String nome;
    
    @Column(nullable = false)
    private String descricao;

    @ManyToOne    
    private Tipo tipo;

    public Ataque(int forca, 
                int acuracia, 
                int pontosDePoder, 
                Categoria categoria, 
                String nome, 
                String descricao,
                Tipo tipo) {
        this.forca = forca;
        this.acuracia = acuracia;
        this.pontosDePoder = pontosDePoder;
        this.categoria = categoria;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getForca() {
        return forca;
    }

    public int getAcuracia() {
        return acuracia;
    }

    public int getPontosDePoder() {
        return pontosDePoder;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}
