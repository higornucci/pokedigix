package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.tipo.Tipo;

@Entity
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 15, nullable = false)
    private String nome;

    @Column(nullable = false)
    private double altura;

    @Column(nullable = false)
    private double peso;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Genero genero;

    @Column(nullable = false)
    private int nivel;

    @Column(nullable = false)
    private int numeroPokedex;

    @Column(nullable = false)
    private int felicidade;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "pokemon_tipo", 
            joinColumns =  @JoinColumn(name = "pokemon_id"), 
            inverseJoinColumns = @JoinColumn(name = "tipo_id"))
    private Collection<Tipo> tipos;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "pokemon_ataque",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "ataque_id"))
    private Collection<Ataque> ataques;

    protected Pokemon() {}

    public Pokemon(String nome, double altura, double peso, Genero genero, int nivel, int numeroPokedex,
            int felicidade, Collection<Tipo> tipos, Collection<Ataque> ataques) throws NivelPokemonInvalidoException, FelicidadeInvalidaException {
        validarNivel(nivel);
        validarFelicidade(felicidade);
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.genero = genero;
        this.nivel = nivel;
        this.numeroPokedex = numeroPokedex;
        this.felicidade = felicidade;
        this.tipos = tipos;
        this.ataques = ataques;
    }

    private void validarFelicidade(int felicidade) throws FelicidadeInvalidaException {
        if(felicidade < 0 || felicidade > 100) {
            throw new FelicidadeInvalidaException();
        }
    }

    private void validarNivel(int nivel) throws NivelPokemonInvalidoException {
        if(nivel < 1 || nivel > 100) {
            throw new NivelPokemonInvalidoException();
        }
    }

    public String getNome() {
        return nome;
    }

    public double getAltura() {
        return altura;
    }

    public double getPeso() {
        return peso;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getNivel() {
        return nivel;
    }

    public int getNumeroPokedex() {
        return numeroPokedex;
    }

    public int getFelicidade() {
        return felicidade;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Tipo> getTipos() {
        return tipos;
    }

    public Collection<Ataque> getAtaques() {
        return ataques;
    }

}
