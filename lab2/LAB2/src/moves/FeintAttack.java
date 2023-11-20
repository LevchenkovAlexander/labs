package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class FeintAttack extends PhysicalMove{
    // тип сила точность
    public FeintAttack(double power){
        super(Type.DARK, power, 0);

    }

    @Override
    protected boolean checkAccuracy (Pokemon var1, Pokemon var2){
        return true;
    }

    @Override
    protected String describe(){
        return "uses" + ((getClass().getName()).split("\\."))[1];
    }
}
