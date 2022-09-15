package br.com.digix.pokedigix.personagem;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreinadorUpdateDTO {
	private String nome;
	private Long enderecoId;
	private int nivel;
	private int dinheiro;
	private Collection<Insignia> insignias;
}
