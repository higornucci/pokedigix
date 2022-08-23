package br.com.digix.pokedigix.pokemon;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.tipo.Tipo;

public class PokemonRequestDTO {

  private String nome;
  private double altura;
  private double peso;
  private Genero genero;
  private int nivel;
  private int numeroPokedex;
  private int felicidade;
  private Long tipoId;
  private Long ataqueId;

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

public Long getAtaqueId() {
    return ataqueId;
}

public void setAtaqueId(Long ataqueId) {
    this.ataqueId = ataqueId;
}

public Long getTipoId() {
    return tipoId;
}

public void setTipoId(Long tipoId) {
    this.tipoId = tipoId;
}
}
