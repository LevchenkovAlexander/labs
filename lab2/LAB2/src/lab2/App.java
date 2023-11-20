package lab2;

import pokemons.*;
import ru.ifmo.se.pokemon.*;

public class App {
    public static void main(String[] args) {

    //     Battle b = new Battle();
    //     Pokemon p1 = new Pokemon("Чужой", 1);
    //     Pokemon p2 = new Pokemon("Хищник", 1);
    //     b.addAlly(p1);
    //     b.addFoe(p2);
    //     b.go();

        
    // Kecleon kecleon = null;
    // kecleon.getHP();
    Binacle binacle = new Binacle("bin", 1);
    Barbaracle barbaracle = new Barbaracle("barb", 1);
    Pichu pichu = new Pichu("pich", 1);
    Pikachu pikachu = new Pikachu("pika", 1);
    Raichu raichu = new Raichu("raic", 1);
    Battle b = new Battle();
    // b.addAlly(kecleon);
    b.addAlly(binacle);
    b.addAlly(barbaracle);
    b.addFoe(pichu);
    b.addFoe(pikachu);
    b.addFoe(raichu);
    b.go();


}
}