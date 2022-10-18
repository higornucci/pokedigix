package br.com.digix.pokedigix.pokemon;

import org.springframework.cache.annotation.Cacheable;

public class TesteCachePokemonRepositoryImpl implements TesteCachePokemonRepository {

    @Override
    @Cacheable("pokemons")
    public String find(String nome) {
        simulateSlowService();
        return nome;
    }
    
    private void simulateSlowService() {
        try {
          long time = 3000L;
          Thread.sleep(time);
        } catch (InterruptedException e) {
          throw new IllegalStateException(e);
        }
      }
}
