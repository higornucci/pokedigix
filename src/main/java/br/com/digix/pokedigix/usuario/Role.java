package br.com.digix.pokedigix.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.digix.pokedigix.utils.EntidadeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class Role extends EntidadeBase{
  
    @Enumerated(EnumType.STRING)
   @Column(length = 20)
   private ERole name; 
   
}
