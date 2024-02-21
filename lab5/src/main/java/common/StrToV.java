package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StrToV {

    public static Vehicle exec (String string){
        if (!string.contains(";")) {
            try {
                String[] s = string.split(", ");
                SimpleDateFormat formatter = new SimpleDateFormat("EEE LLL d k:m:s z u", Locale.ENGLISH);
                return new Vehicle(Integer.parseInt(s[0]), s[1], new Coordinates(Integer.parseInt(s[2]),
                        Integer.parseInt(s[3])), formatter.parse(s[4]), Integer.parseInt(s[5]),
                        Long.parseLong(s[6]), VehicleType.valueOf(s[7]), FuelType.valueOf(s[8]));
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
    String[] s = string.split(";");
    return new Vehicle(++Vehicle.last_id, s[0], new Coordinates(Integer.parseInt(s[1]),
            Integer.parseInt(s[2])), new Date(), Integer.parseInt(s[3]), Long.parseLong(s[4]),
            VehicleType.valueOf(s[5].toUpperCase()), FuelType.valueOf(s[6].toUpperCase()));
    }
}
