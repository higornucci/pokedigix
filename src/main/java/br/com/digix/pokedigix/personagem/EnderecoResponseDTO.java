package br.com.digix.pokedigix.personagem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponseDTO {

    private Long id;
    private String regiao;
    private String cidade;
    

}
