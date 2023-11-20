package pokemons;

import moves.Thunderbolt;


public class Raichu extends Pikachu{
    public Raichu (String name, int lvl){
        super(name, lvl);
        // setType(Type.ELECTRIC);
        setStats(60, 90, 55, 90, 80, 110);
        // addMove(tailWhip);
        // addMove(facade);
        // addMove(agility);
        addMove(thunderbolt);
    }

    // TailWhip tailWhip = new TailWhip(0, 100);
    // Facade facade = new Facade(70, 100);
    // Agility agility = new Agility();
    
    Thunderbolt thunderbolt = new Thunderbolt(90, 100);
}
