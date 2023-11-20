package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Withdraw extends StatusMove{
    public Withdraw(){
        super(Type.WATER, 0, 0);
    }

    @Override
    protected void applySelfEffects(Pokemon p){
        super.applySelfEffects(p);

        Effect effect = new Effect().stat(Stat.DEFENSE, 1);
        p.addEffect(effect);
    }
}
