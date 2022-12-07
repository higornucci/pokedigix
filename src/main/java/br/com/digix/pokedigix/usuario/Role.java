package br.com.digix.pokedigix.usuario;

import javax.persistence.*;

import br.com.digix.pokedigix.utils.EntidadeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Role extends EntidadeBase {
    
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
}
