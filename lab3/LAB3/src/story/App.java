package story;

import com.*;
import com.Humans.Feel;
import com.Objects.Size;

public class App {
    public static void main (String[] args) {
        HE he = new HE ("He");
        Mumla mumla = new Mumla ("Mu");
        Objects keks = new Objects ("a piece of cake");
        Objects jarOfMilk = new Objects ("a jar of milk");
        Objects table = new Objects("a table");
        Objects vase = new Objects("a vase full of flowers");
        
        // for (int i=0; i < plates.length; i++) {
        //     plates[i] = new Objects("plate_"+ ((i+1)+""));
        // }

        Objects[] plates = new Objects[6];
        for (int i = 0; i < 6; i ++) {
            plates[i] = new Objects("plate_"+((i+1)+""));
        } 
        
        
        
        System.out.println(he.take(keks, table));
        System.out.println(he.put(keks, jarOfMilk, "in"));
        System.out.println(he.lookAround());
        
        System.out.println(table.passive_action("laid with six plates"));

        System.out.println(plates[5].stand("under the table on Veranda, whatever the f it is"));

    
        mumla.setFeel(Feel.COMFY);
        System.out.println(mumla.say("I feel " + mumla.getFeel().getFeel() + " under the table"));

        plates[5].setSize(Size.TINY);
        System.out.println("The " + plates[5].getName() + " was " + plates[5].getSize().getSize());
        System.out.println("The " + plates[5].hide("in the shadow of " + vase.getName()));

    }
}
