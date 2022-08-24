package br.com.digix.pokedigix.personagem;

public class TreinadorRequestDTO {
        private Long idEndereco;
        private String nome;
        private Long idPrimeiroPokemon;
        
        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }
        public Long getIdPrimeiroPokemon() {
            return idPrimeiroPokemon;
        }
        public void setIdPrimeiroPokemon(Long idPrimeiroPokemon) {
            this.idPrimeiroPokemon = idPrimeiroPokemon;
        }
        public Long getIdEndereco() {
            return idEndereco;
        }
        public void setIdEndereco(Long idEndereco) {
            this.idEndereco = idEndereco;
        }
   
        
}