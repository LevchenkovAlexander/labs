package moves;

import ru.ifmo.se.pokemon.Type;
// import ru.ifmo.se.pokemon.Effect;
// import ru.ifmo.se.pokemon.Messages;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;

public class StoneEdge extends PhysicalMove{
    public StoneEdge (double power, double acc){
        super(Type.ROCK, power, acc);

    }

    @Override
    protected double calcCriticalHit(Pokemon var1, Pokemon var2) {
        if (var1.getStat(Stat.SPEED) * 3 / 512.0 > Math.random()) {
           System.out.println("Critical Hit!");
           return 2.0;
        } else {
           return 1.0;
        }
    }

    @Override
    public String describe(){
        return "uses" + ((getClass().getName()).split("\\."))[1];
    }
}