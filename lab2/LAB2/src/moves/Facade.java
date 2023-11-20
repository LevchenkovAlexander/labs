package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Status;

public class Facade extends PhysicalMove{
    
    public Facade (double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected void applyOppEffects (Pokemon p){
        super.applyOppEffects(p);
        if (p.getCondition() == Status.BURN || p.getCondition() == Status.PARALYZE || p.getCondition() == Status.POISON){
            super.power *= 2;
        }
    }



}
