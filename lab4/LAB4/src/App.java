import com.Edible;
import com.HE;
import com.Humans;
import com.Inedible;
import com.Humans.Feel;
import com.Mumla;
import com.NewNameNotSupportedException;
import com.Weather;
import com.Places;
import com.SizeChangeException;
import com.Things.Size;

public class App {
    public static void main(String[] args) throws Exception {

        Weather weather = new Weather() {
            private String weather;
            @Override
            public void setWeather (String weather) {
                this.weather = weather;
                System.out.println("The weather was " + weather + " that day");
            }
            
            @Override
            public String getWeather () {
                return weather;
            }

        };
        weather.setWeather("sunny");

        HE he = new HE ("He");
        Mumla mumla = new Mumla ("Mu");


        Edible keks = new Edible("a piece of cake");
        Places jarOfMilk = new Places("a jar of milk");
        Places table = new Places("a table");
        Inedible vase = new Inedible("a vase full of flowers");

        Inedible[] plates = new Inedible[6];
        for (int i = 0; i < 6; i ++) {
            plates[i] = new Inedible("plate_"+((i+1)+""));
        } 
        
        
        Inedible grass = new Inedible("grass");
        try {
            grass.setName("newname");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        grass.doSmth(" zashurshala");

        Humans MumiMama = new Humans("MumiMama");
        MumiMama.appear();

        MumiMama.doSmth(" sit");
        MumiMama.doSmth(" watch at the water");

        mumla.doSmth(" call for her sister");
        
        Mumla mu = new Mumla("Little Mu");
        mu.think(" She's gotta use honey to lure mme out");
        
        System.out.println(he.take(keks, table));
        System.out.println(he.put(keks, jarOfMilk, "in"));
        System.out.println(he.lookAround());
        
        System.out.println(table.passive_action("laid with six plates"));

        System.out.println(plates[5].stand("under the table on Veranda, whatever the f it is"));

    
        mumla.setFeel(Feel.COMFY);
        System.out.println(mumla.say("I feel " + mumla.getFeel().getString() + " under the table"));

        try {
            plates[5].setSize(Size.TINY);
        } catch (SizeChangeException e) {
            System.err.println(e.getMessage());
        }
        try {
            plates[5].setSize(Size.HUGE);
        } catch (SizeChangeException e) {
            System.err.println(e.getMessage());
        }


        System.out.println("The " + plates[5].getName() + " was " + plates[5].getSize().getString());
        System.out.println("The " + plates[5].hide("in the shadow of " + vase.getName()));

        System.out.print(he.take(table));

        MumiMama.appear();
        MumiMama.doSmth(" run at her top speed");

        Humans MumiTroll = new Humans("MumiTroll");
        MumiTroll.think(" Gonna be storming");
        
        MumiTroll.doSmth(" miss Snusnumrik");
        MumiTroll.doSmth(" somtimes tells about his travels");

        Humans snusnumrik = new Humans("Snusnumrik");
        Humans.Head snusHead = snusnumrik.new Head();
        snusHead.setSmart(true);



        Humans talkingTo = new Humans("The one Snusnumrik is talking to");
        talkingTo.doSmth(" feel proud for MumiTroll");

        if (weather.getWeather().equals("snowing")) {
            MumiTroll.doSmth(" go to sleep");
            snusnumrik.doSmth(" go Traveelling");
        }        

    }

}


// кастомная реализация Enum через анотации (свои анот свой Enum)
// две кастом анот