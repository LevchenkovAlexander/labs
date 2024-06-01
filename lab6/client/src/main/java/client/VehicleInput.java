package client;

import common.vehicle.FuelType;
import common.vehicle.Vehicle;
import common.vehicle.VehicleType;

import java.util.Arrays;
import java.util.Scanner;

public class VehicleInput {
    private static final String fuelTypes = "GASOLINE\nKEROSENE\nALCOHOL\nMANPOWER";
    private static final String vehicleTypes = "DRONE\nMOTORCYCLE\nHOVERBOARD\nSPACESHIP";
    public static Vehicle exec (Client client, Scanner scn, String fields_str) {
        String[] fields = fields_str.split(";");
        Vehicle vehicle = new Vehicle();
        boolean index = false;

        for (int i = 0; i < fields.length; i++) {
            System.out.println("Input: " + fields[i]);
            if (fields[i].equals("vehicleType")) {
                System.out.println("vehicle_types: " + "\n" + vehicleTypes);
            }
            if (fields[i].equals("fuelType")) {
                System.out.println("fuel_types: " + "\n" + fuelTypes);
            }

            String input = scn.nextLine();


            if (input.equalsIgnoreCase("stop")) {
                System.out.println("stopped");
                return null;
            }
            switch (fields[i]) {
                case "index" -> {
                    index = true;
                    int id = Integer.parseInt(input);
                    if (id <= 0) {
                        System.out.println("This ID is unavailable");
                        i--;
                    } else {
                        vehicle.setId(id);
                    }
                }
                case "name" -> {
                    String[] unsupportedSymbols = {" ", ",", ";", ":", "(", ")", "{", "}"};
                    boolean flag = false;
                    for (String str : unsupportedSymbols) {
                        if (input.contains(str)){
                            System.out.println("Inputted name contains unsupported character: " + Arrays.toString(unsupportedSymbols));
                            i--;
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        vehicle.setName(input);
                    }

                }
                case "coordinate_X" -> {
                    try {
                        int x = Integer.parseInt(input);
                        if (x > 880) {
                            System.out.println("Coordinate x cannot be greater than 880");
                            i--;
                        } else {
                            vehicle.setCoordinateX(x);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Unsupported character for number");
                        i--;
                    }

                }
                case "coordinate_Y" -> {
                    try{
                        int y = Integer.parseInt(input);
                        vehicle.setCoordinateY(y);
                    } catch (NumberFormatException e) {
                        System.out.println("Unsupported character for number");
                        i--;
                    }
                }
                case "enginePower" -> {
                    try {
                        int eng = Integer.parseInt(input);
                        if (eng <= 0) {
                            System.out.println("Engine power must be greater than 0");
                            i--;
                        } else {
                            vehicle.setEnginePower(eng);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Unsupported character for a number");
                        i--;
                    }
                }
                case "numberOfWheels" -> {
                    try {
                        long num = Long.parseLong(input);
                        if (num <= 0) {
                            System.out.println("Number of wheels must be greater than 0");
                            i--;
                        } else {
                            vehicle.setNumOfWheels(num);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Unsupported character for a number");
                        i--;
                    }
                }
                case "vehicleType" -> {
                    System.out.println("0.1");

                    if (!Arrays.asList(vehicleTypes.split("\n")).contains(input.toUpperCase())) {
                        System.out.println(input + " is not a Vehicle type");
                        System.out.println("0.1");
                        i--;
                    } else {
                        System.out.println("0.2");
                        vehicle.setType(VehicleType.valueOf(input.toUpperCase()));
                    }
                }
                case "fuelType" -> {
                    if (!Arrays.asList(fuelTypes.split("\n")).contains(input.toUpperCase())){
                        System.out.println(input + " is not a Fuel type");
                        System.out.println("1.1");
                        i--;
                    } else {
                        System.out.println("1.2");
                        vehicle.setFuelType(FuelType.valueOf(input.toUpperCase()));
                        System.out.println(input.toUpperCase());
                    }
                }
            }
        }
        if (!index) {
//            vehicle.setId(client.getLastId());
        }
        vehicle.setCreationDate();
        return vehicle;
    }
}
