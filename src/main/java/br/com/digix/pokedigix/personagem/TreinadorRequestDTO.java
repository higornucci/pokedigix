package br.com.digix.pokedigix.personagem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreinadorRequestDTO {
	private Long idEndereco;
	private String nome;
	private Long idPrimeiroPokemon;
}