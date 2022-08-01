package br.com.digix.pokedigix.ataque;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import br.com.digix.pokedigix.pokemon.Pokemon;
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

    @ManyToMany(mappedBy = "ataques")
    private Collection<Pokemon> pokemons;

    public Ataque(int forca, 
                int acuracia, 
                int pontosDePoder, 
                Categoria categoria, 
                String nome, 
                String descricao,
                Tipo tipo) throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
        validarAcuracia(acuracia);
        validarForca(categoria, forca);
        validarTipo(categoria, tipo);
        this.forca = forca;
        this.acuracia = acuracia;
        this.pontosDePoder = pontosDePoder;
        this.categoria = categoria;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }
    
    private void validarTipo(Categoria categoria, Tipo tipo) throws TipoInvalidoParaCategoriaException {
        if(!categoria.equals(Categoria.EFEITO) && tipo == null) {
            throw new TipoInvalidoParaCategoriaException(categoria);
        }
    }

    private void validarForca(Categoria categoria, int forca) throws ForcaInvalidaParaCategoriaException {
        if(!categoria.equals(Categoria.EFEITO) && forca <= 0) {
            throw new ForcaInvalidaParaCategoriaException(categoria);
        }
    }

    public Ataque(int acuracia, int pontosDePoder, String nome, String descricao) throws AcuraciaInvalidaException {
        validarAcuracia(acuracia);
        this.acuracia = acuracia;
        this.pontosDePoder = pontosDePoder;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = Categoria.EFEITO;
    }
    
    private void validarAcuracia(int acuracia) throws AcuraciaInvalidaException {
        if(acuracia < 0 || acuracia > 100) {
            throw new AcuraciaInvalidaException();
        }
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

    public Collection<Pokemon> getPokemons() {
        return pokemons;
    }
}
