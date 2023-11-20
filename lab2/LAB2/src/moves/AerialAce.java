package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class AerialAce extends PhysicalMove {
    public AerialAce(double power){
        super(Type.FLYING, power, 0);
    }

    @Override
    protected boolean checkAccuracy (Pokemon var1, Pokemon var2){
        return true;
    }

    @Override
    public String describe(){
        return "uses" + ((getClass().getName()).split("\\."))[1];
    }
}
