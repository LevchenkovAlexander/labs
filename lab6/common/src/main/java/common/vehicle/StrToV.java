package common.vehicle;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class StrToV {

    public static Vehicle exec (String string) {
        try {
            String[] s = string.split(", ");
            SimpleDateFormat formatter = new SimpleDateFormat("EEE LLL d k:m:s z u", Locale.ENGLISH);
            return new Vehicle(Integer.parseInt(s[0]), s[1], new Coordinates(Integer.parseInt(s[2]),
                    Integer.parseInt(s[3])), formatter.parse(s[4]), Integer.parseInt(s[5]),
                    Long.parseLong(s[6]), VehicleType.valueOf(s[7]), FuelType.valueOf(s[8]));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

//    public static Vehicle exec (String string, String date) {
//        try {
//            String[] s = string.split(", ");
//            SimpleDateFormat formatter = new SimpleDateFormat("EEE LLL d k:m:s z u", Locale.ENGLISH);
//            return new Vehicle(Integer.parseInt(s[0]), s[1], new Coordinates(Integer.parseInt(s[2]),
//                    Integer.parseInt(s[3])), formatter.parse(date), Integer.parseInt(s[4]),
//                    Long.parseLong(s[5]), VehicleType.valueOf(s[6].toUpperCase()), FuelType.valueOf(s[7].toUpperCase()));
//        } catch (ParseException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
}
