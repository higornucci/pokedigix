package br.com.digix.pokedigix.ataque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public abstract class AtaqueDTO {

	private int forca;
	private int acuracia;
	private int pontosDePoder;
	private Categoria categoria;
	private String nome;
	private String descricao;

}
