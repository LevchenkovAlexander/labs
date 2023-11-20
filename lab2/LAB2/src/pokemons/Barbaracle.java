package pokemons;

import moves.FocusBlast;
import moves.NightSlash;
import moves.StoneEdge;
import moves.Withdraw;
import ru.ifmo.se.pokemon.Pokemon;

public class Barbaracle extends Pokemon{

    public Barbaracle (String name, int lvl){
        super(name, lvl);
        setType();
        addMove(stoneEdge);
        addMove(withdraw);
        addMove(nightSlash);
        addMove(focusBlast);
    }
    StoneEdge stoneEdge = new StoneEdge(100, 80);
    Withdraw withdraw = new Withdraw();
    NightSlash nightSlash = new NightSlash(70, 100);
    FocusBlast focusBlast = new FocusBlast(120, 70);
}