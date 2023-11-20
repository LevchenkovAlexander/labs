package pokemons;

import moves.Facade;
import moves.TailWhip;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Pichu extends Pokemon {
    public Pichu (String name, int lvl){
        super(name, lvl);
        setType(Type.ELECTRIC);
        setStats(20, 40, 15, 35, 35, 60);
        addMove(tailWhip);
        addMove(facade);
    }

    TailWhip tailWhip = new TailWhip(0, 100);
    Facade facade = new Facade(70, 100);
    
}
