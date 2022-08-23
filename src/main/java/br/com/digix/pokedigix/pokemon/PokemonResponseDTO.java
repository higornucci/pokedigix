package br.com.digix.pokedigix.pokemon;

import br.com.digix.pokedigix.ataque.AtaqueResponseDTO;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;

public class PokemonResponseDTO {

  private Long id;
  private String nome;
  private double altura;
  private double peso;
  private Genero genero;
  private int nivel;
  private int numeroPokedex;
  private int felicidade;
  private AtaqueResponseDTO ataque;
  private TipoResponseDTO tipo;

public PokemonResponseDTO(Long id, String nome, double altura, double peso, Genero genero, int nivel, int numeroPokedex,
        int felicidade, AtaqueResponseDTO ataque, TipoResponseDTO tipo) {
    this.id = id;
    this.nome = nome;
    this.altura = altura;
    this.peso = peso;
    this.genero = genero;
    this.nivel = nivel;
    this.numeroPokedex = numeroPokedex;
    this.felicidade = felicidade;
    this.ataque = ataque;
    this.tipo = tipo;
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

public AtaqueResponseDTO getAtaque() {
    return ataque;
}

public void setAtaque(AtaqueResponseDTO ataque) {
    this.ataque = ataque;
}

public TipoResponseDTO getTipo() {
    return tipo;
}

public void setTipo(TipoResponseDTO tipo) {
    this.tipo = tipo;
}
}
