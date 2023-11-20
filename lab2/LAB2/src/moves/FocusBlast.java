package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class FocusBlast extends SpecialMove{
    
    public FocusBlast (double pow, double acc){
        super(Type.FIGHTING, 120, 70);
    }

    @Override
    protected void applyOppEffects (Pokemon p){
        super.applyOppEffects(p);
        if (0.1 > Math.random()){
            Effect effect = new Effect().stat(Stat.SPECIAL_DEFENSE, -1);
            p.addEffect(effect);
        }
    }

}
