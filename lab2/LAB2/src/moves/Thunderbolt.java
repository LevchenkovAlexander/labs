package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;


public class Thunderbolt extends SpecialMove {
    public Thunderbolt (double power, double acc){
        super(Type.ELECTRIC, power, acc);

    }

    @Override
    protected void applyOppEffects (Pokemon p){
        super.applyOppEffects(p);
        if (0.1 > Math.random()){
            Effect.paralyze(p);
        }
    }

    @Override 
    public String describe(){
        return "uses " + ((getClass().getName()).split("\\."))[1];
    }
}
