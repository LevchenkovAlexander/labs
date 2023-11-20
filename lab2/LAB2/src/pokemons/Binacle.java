package pokemons;

import moves.NightSlash;
import moves.StoneEdge;
import moves.Withdraw;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Binacle extends Pokemon{
    public Binacle (String name, int lvl){
        super(name, lvl);
        setType(Type.ROCK, Type.WATER);
        setStats(42, 52, 67, 39, 56, 50);
        addMove(stoneEdge);
        addMove(withdraw);
        addMove(nightSlash);
    }

    StoneEdge stoneEdge = new StoneEdge(100, 80);
    Withdraw withdraw = new Withdraw();
    NightSlash nightSlash = new NightSlash(70, 100);
}
