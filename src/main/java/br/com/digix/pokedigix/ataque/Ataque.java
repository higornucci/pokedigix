package br.com.digix.pokedigix.ataque;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.utils.EntidadeBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ataque extends EntidadeBase {

	@Basic
	private int forca = 0;

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

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Tipo tipo;

	@ManyToMany(mappedBy = "ataques")
	private Collection<Pokemon> pokemons;

	public Ataque(int forca,
			int acuracia,
			int pontosDePoder,
			Categoria categoria,
			String nome,
			String descricao,
			Tipo tipo)
			throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
		validarAcuracia(acuracia);
		validarForca(categoria, forca);
		this.forca = forca;
		this.acuracia = acuracia;
		this.pontosDePoder = pontosDePoder;
		this.categoria = categoria;
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
	}

	private void validarForca(Categoria categoria, int forca) throws ForcaInvalidaParaCategoriaException {
		if (!categoria.equals(Categoria.EFEITO) && forca <= 0) {
			throw new ForcaInvalidaParaCategoriaException(categoria);
		}
	}

	public Ataque(int acuracia, int pontosDePoder, Categoria categoria, String nome, String descricao, Tipo tipo)
			throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException {
		validarAcuracia(acuracia);
		validarForca(categoria, forca);
		this.tipo = tipo;
		this.acuracia = acuracia;
		this.pontosDePoder = pontosDePoder;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = Categoria.EFEITO;
	}

	private void validarAcuracia(int acuracia) throws AcuraciaInvalidaException {
		if (acuracia < 0 || acuracia > 100) {
			throw new AcuraciaInvalidaException();
		}
	}
}