package pokemons;
import moves.DizzyPunch;
import moves.FeintAttack;
import moves.Thunderbolt;
import moves.AerialAce;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Kecleon extends Pokemon{

    public Kecleon(String name, int lvl){
        super(name, lvl);
        setType(Type.NORMAL);
        setStats(60, 90, 70, 60, 120, 40);
        addMove(feintAttack);
        addMove(aerialAce);
        addMove(thunderbolt);
        addMove(dizzyPunch);

    }

    FeintAttack feintAttack = new FeintAttack(60);
    AerialAce aerialAce = new AerialAce(60);
    Thunderbolt thunderbolt = new Thunderbolt(90, 100);
    DizzyPunch dizzyPunch = new DizzyPunch(70, 100);
    
    
}