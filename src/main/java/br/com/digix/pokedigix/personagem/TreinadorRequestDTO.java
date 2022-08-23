package br.com.digix.pokedigix.personagem;

import java.util.Collection;


public class TreinadorRequestDTO {
        private Long idEndereco;
        private String nome;
        private Collection<Insignia> insignias;
        private int nivel;
        private int dinheiro;
        private Long idPrimeiroPokemon;
        
        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }
        public Collection<Insignia> getInsignias() {
            return insignias;
        }
        public void setInsignias(Collection<Insignia> insignias) {
            this.insignias = insignias;
        }
        public int getNivel() {
            return nivel;
        }
        public void setNivel(int nivel) {
            this.nivel = nivel;
        }
        public int getDinheiro() {
            return dinheiro;
        }
        public void setDinheiro(int dinheiro) {
            this.dinheiro = dinheiro;
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