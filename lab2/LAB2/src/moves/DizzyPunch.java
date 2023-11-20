package moves;

import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;

public class DizzyPunch extends PhysicalMove{

    public DizzyPunch(double power, double acc){
        super(Type.NORMAL, power, acc);
    }


    @Override 
    protected void applyOppEffects (Pokemon p){
        super.applyOppEffects(p);
        if (0.2 > Math.random()){
            Effect.confuse(p);
        }
    }

    @Override   
    public String describe(){
        return "uses " + ((getClass().getName()).split("\\."))[1];
    }
}
