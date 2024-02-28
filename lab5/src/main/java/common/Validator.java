package common;

import client.Client;
import server.FuelType;
import server.VehicleType;

import static server.Vehicle.getFuelTypes;
import static server.Vehicle.getVehicleTypes;

import java.util.Arrays;
import java.util.Stack;


public class Validator {
    public static boolean isValid (String field, String input) throws IllegalArgumentException {
        switch (field) {
            case "index" -> {
                if (Integer.parseInt(input) <= 0) {
                    throw new IllegalArgumentException("This ID is unavailable");
                }
            }
            case "name" -> {
                String[] unsupportedSymbols = {" ", ",", ";", ":", "(", ")", "{", "}"};
                for (String str : unsupportedSymbols) {
                    if (input.contains(str)){
                        throw new IllegalArgumentException("Inputted name contains unsupported character: " + Arrays.toString(unsupportedSymbols));
                    }
                }
            }
            case "coordinate_X" -> {
                try {
                    if (Integer.parseInt(input) > 880) {
                        throw new IllegalArgumentException("Coordinate x cannot be greater than 880");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Unsupported character for number");
                }

            }
            case "coordinate_Y" -> {
                try{
                    Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Unsupported character for number");
                }

            }
            case "enginePower" -> {
                try {
                    int eng = Integer.parseInt(input);
                    if (eng <= 0) {
                        throw new IllegalArgumentException("Engine power must be greater than 0");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Unsupported character for a number");
                }
            }
            case "numberOfWheels" -> {
                try {
                    long num = Long.parseLong(input);
                    if (num <= 0) {
                        throw new IllegalArgumentException("Number of wheels must be greater than 0");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Unsupported character for a number");
                }
            }
            case "vehicleType" -> {
                try {
                    VehicleType.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(input + " is not a Vehicle type");
                }
            }
            case "fuelType" -> {
                try {
                    FuelType.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(input + " is not a Fuel type");
                }
            }
        }
        return true;
    }
    public static boolean isValid (Request request) throws IllegalArgumentException{
        if (request.getCommand().equals("add") || request.getCommand().equals("update") ||
                request.getCommand().equals("insert") || request.getCommand().equals("add_if_min")) {
            if (request.getVehicle() == null) {
            StringBuilder response = new StringBuilder();
                for (String field : new String[]{"index", "name", "coordinates", "enginePower", "numberOfWheels", "vehicleType", "fuelType"}) {
                    if (request.getCommand().equals("add") && field.equals("index")) {
                        continue;
                    }
                    switch (field) {
                        case "coordinates" -> response.append("coordinate_X" + ";" + "coordinate_Y" + ";");
                        case "vehicleType" ->
                                response.append("vehicleType/nVehicle_Types:/n").append(getVehicleTypes()).append(";");
                        case "fuelType" ->
                                response.append("fuelType/nFuel_Types:/n").append(getFuelTypes()).append(";");
                        default -> response.append(field).append(";");
                    }

                }
                throw new IllegalArgumentException(response.toString());
            }
        }

        if (request.getCommand().equals("execute_script")) {

            if (request.getFileName().isEmpty()) {
                throw new IllegalArgumentException("File name cannot be null");
            }
        }

        if (request.getCommand().equals("remove_by_id") && request.getArg() <= 0) {
            throw new IllegalArgumentException("This ID is unavailable");
        }

        if (request.getCommand().equals("remove_all_by_number_of_wheels") && request.getArg() <= 0) {
            throw new IllegalArgumentException("Number of wheels must be greater than 0");
        }

        return true;
    }

}
