package pokemons;

import moves.Agility;

public class Pikachu extends Pichu{
    // public Pikachu (String name, int lvl){
    //     super(name, lvl);
    //     setType(Type.ELECTRIC);
    //     addMove(tailWhip);
    //     addMove(facade);
    //     addMove(agility);
    // }

    // TailWhip tailWhip = new TailWhip(0, 100);
    // Facade facade = new Facade(70, 100);
    public Pikachu (String name, int lvl){
        super(name, lvl);
        setStats(35, 55, 40, 50, 50, 90);

        addMove(agility);
    }
    Agility agility = new Agility();
}
