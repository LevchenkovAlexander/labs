package common;

import server.com.Vehicle;
import server.com.VehicleType;
import server.com.FuelType;

import java.lang.reflect.Field;
import java.util.Arrays;

import static server.com.Vehicle.getFuelTypes;
import static server.com.Vehicle.getVehicleTypes;

public class Validator {
    public static boolean isValid (String field, String input) throws IllegalArgumentException {
        switch (field) {
            case "id" -> {
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
            case "coordinates" -> {
                try {
                    if (Integer.parseInt(input.split(" ")[0]) > 880) {
                        throw new IllegalArgumentException("Coordinate x cannot be greater than 880");
                    }
                    Integer.parseInt(input.split(" ")[1]);
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
            case "type" -> {
                try {
                    VehicleType.valueOf(input);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(input + " is not a Vehicle type");
                }
            }
            case "fuelType" -> {
                try {
                    FuelType.valueOf(input);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(input + " is not a Fuel type");
                }
            }
        }
        return true;
    }
    public static boolean isValid (Request request) throws IllegalArgumentException{
        if (request.getCommand().equals("add") || request.getCommand().equals("update") ||
                request.getCommand().equals("insert")) {
            if (request.getVehicle() == null) {
                StringBuilder response = new StringBuilder();
                for (Field field : Vehicle.class.getFields()) {
                    String name = field.getName();
                    if (!name.equals("id") && !name.equals("creationDate")) {

                        switch (name) {
                            case "coordinates" -> response.append("coordinate_X" + ";" + "coordinate_Y" + ";");
                            case "type" ->
                                    response.append("type/nVehicle_Types:/n").append(getVehicleTypes()).append(";");
                            case "fuelType" ->
                                    response.append("fuelType/nFuel_Types:/n").append(getFuelTypes()).append(";");
                            default -> response.append(name).append(";");
                        }

                    }
                }

                throw new IllegalArgumentException(response.toString());
            }
        }
        return true;
    }
}
