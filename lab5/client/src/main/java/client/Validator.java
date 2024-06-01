package client;


import common.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Validator {
    private static final String fuelTypes = "GASOLINE\nKEROSENE\nALCOHOL\nMANPOWER";
    private static final String vehicleTypes = "DRONE\nMOTORCYCLE\nHOVERBOARD\nSPACESHIP";
    public static String isFieldValid (String field, String input) {
        if (input.equalsIgnoreCase("stop")) {
            return "stopped";
        }
        switch (field) {
            case "index" -> {
                if (Integer.parseInt(input) <= 0) {
                    return "This ID is unavailable";
                }
            }
            case "name" -> {
                String[] unsupportedSymbols = {" ", ",", ";", ":", "(", ")", "{", "}"};
                for (String str : unsupportedSymbols) {
                    if (input.contains(str)){
                        return "Inputted name contains unsupported character: " + Arrays.toString(unsupportedSymbols);
                    }
                }
            }
            case "coordinate_X" -> {
                try {
                    if (Integer.parseInt(input) > 880) {
                        return "Coordinate x cannot be greater than 880";
                    }
                } catch (NumberFormatException e) {
                    return "Unsupported character for number";
                }

            }
            case "coordinate_Y" -> {
                try{
                    Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    return "Unsupported character for number";
                }

            }
            case "enginePower" -> {
                try {
                    int eng = Integer.parseInt(input);
                    if (eng <= 0) {
                        return "Engine power must be greater than 0";
                    }
                } catch (NumberFormatException e) {
                    return "Unsupported character for a number";
                }
            }
            case "numberOfWheels" -> {
                try {
                    long num = Long.parseLong(input);
                    if (num <= 0) {
                        return "Number of wheels must be greater than 0";
                    }
                } catch (NumberFormatException e) {
                    return "Unsupported character for a number";
                }
            }
            case "vehicleType" -> {
                if (!Arrays.asList(vehicleTypes.split("\n")).contains(input.toUpperCase())) {
                    return (input + " is not a Vehicle type");
                }
            }
            case "fuelType" -> {
                if (!Arrays.asList(fuelTypes.split("\n")).contains(input.toUpperCase())){
                    return (input + " is not a Fuel type");
                }
            }
        }
        return "valid";
    }
    public static String isValid (Request request) {

//        command.isValid();

        String command = request.getCommand();
        String[] commands = {
                "last_id",
                "help",
                "info",
                "show",
                "add",
                "update",
                "remove",
                "clear",
                "execute",
                "exit",
                "insert",
                "remove_first",
                "add_if_min",
                "remove_all_by_number_of_wheels",
                "average_of_number_of_wheels",
                "print_field_descending_fuel_type"
        };

        if (!Arrays.asList(commands).contains(command)) {
            return "Unknown command. Type <help> to see all the available commands";
        }

        if (command.equals("add") || command.equals("update") || command.equals("insert") || command.equals("add_if_min")) {
            if (request.getVehicle() == null) {
                StringBuilder response = new StringBuilder();

                ArrayList<String> fields  = new ArrayList<>(List.of(new String[]{"name", "coordinates", "enginePower", "numberOfWheels", "vehicleType", "fuelType"}));
                if (!command.equals("add")) {
                    fields.add(0, "index");
                }

                for (String field : fields) {

                    switch (field) {
                        case "coordinates" -> response.append("coordinate_X" + ";" + "coordinate_Y" + ";");
                        case "vehicleType" ->
                                response.append("vehicleType\nVehicle_Types:\n").append(vehicleTypes).append(";");
                        case "fuelType" ->
                                response.append("fuelType\nFuel_Types:\n").append(fuelTypes).append(";");
                        default -> response.append(field).append(";");
                    }

                }
                return response.toString();
            }
        }

        if (command.equals("execute")) {
            if (request.getFileName().isEmpty()) {
                return "File name cannot be null";
            }
        }

        if (command.equals("remove") && request.getArg() <= 0) {
            return "This ID is unavailable";
        }

        if (command.equals("remove_all_by_number_of_wheels") && request.getArg() <= 0) {
            return "Number of wheels must be greater than 0";
        }

        return "valid";
    }

}
