package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class NightSlash extends PhysicalMove{
    
    public NightSlash (double pow, double acc){
        super(Type.DARK, pow, acc);
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

}
