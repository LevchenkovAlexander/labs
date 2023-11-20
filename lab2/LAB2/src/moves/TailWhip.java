package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class TailWhip extends StatusMove{
    
    public TailWhip (double pow, double acc){
        super(Type.NORMAL, 0, acc);
    }

    @Override
    protected void applyOppEffects (Pokemon p){
        super.applyOppEffects(p);
        if (0.1 > Math.random()){
            Effect effect = new Effect().stat(Stat.DEFENSE, -1);
            p.addEffect(effect);
        }
    }

}
