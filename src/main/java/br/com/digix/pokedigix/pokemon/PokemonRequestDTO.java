package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

public class PokemonRequestDTO {

    private String nome;
    private double altura;
    private double peso;
    private Genero genero;
    private int nivel;
    private int numeroPokedex;
    private int felicidade;
    private Collection<Long> tiposIds;
    private Collection<Long> ataquesIds;

    public PokemonRequestDTO() {
    }

    public PokemonRequestDTO(String nome, double altura, double peso, Genero genero, int nivel, int numeroPokedex,
            int felicidade, Collection<Long> tiposIds, Collection<Long> ataquesIds) {
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.genero = genero;
        this.nivel = nivel;
        this.numeroPokedex = numeroPokedex;
        this.felicidade = felicidade;
        this.tiposIds = tiposIds;
        this.ataquesIds = ataquesIds;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNumeroPokedex() {
        return numeroPokedex;
    }

    public void setNumeroPokedex(int numeroPokedex) {
        this.numeroPokedex = numeroPokedex;
    }

    public int getFelicidade() {
        return felicidade;
    }

    public void setFelicidade(int felicidade) {
        this.felicidade = felicidade;
    }

    public Collection<Long> getAtaquesIds() {
        return ataquesIds;
    }

    public void setAtaquesIds(Collection<Long> ataquesIds) {
        this.ataquesIds = ataquesIds;
    }

    public Collection<Long> getTiposIds() {
        return tiposIds;
    }

    public void setTiposIds(Collection<Long> tiposIds) {
        this.tiposIds = tiposIds;
    }

}
