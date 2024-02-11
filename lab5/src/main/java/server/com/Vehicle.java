package server.com;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

public class Vehicle {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private String creationDate;
    private int enginePower;
    private long numberOfWheels;
    private VehicleType type;
    private FuelType fuelType;

    public Vehicle() { }

    public Vehicle(Integer id, String name, Coordinates coordinates, String creationDate, int enginePower,
                   long numberOfWheels, VehicleType type, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.type = type;
        this.fuelType = fuelType;
    }

    public Vehicle(String string) {
        Map<Integer, String> keys;
        if (string.charAt(0) == '{') {
            string = string.substring(1, string.length() - 1);
            keys = Map.of(
                    0, "Name",
                    1, "CoordX",
                    2, "CoordY",
                    3, "EnginePower",
                    4, "NumOfWheels",
                    5, "Type",
                    6, "FuelType"
            );
        }
        else{
            keys = Map.of(
                    0, "Id",
                    1, "Name",
                    2, "CoordX",
                    3, "CoordY",
                    4, "CreationDate",
                    5, "EnginePower",
                    6, "NumOfWheels",
                    7, "Type",
                    8, "FuelType"
            );
        }

        String[] s = string.split(", ");

        coordinates = new Coordinates();

        for (int i = 0; i < s.length; i++) {
            try {
                Method method = this.getClass().getMethod("set" + keys.get(i), String.class);
                try {
                    method.invoke(this, s[i]);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    public void setId (String id) {
        this.id = Integer.valueOf(id);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCoordX(String x) {
        coordinates.setX(Integer.parseInt(x));
    }

    public void setCoordY(String y) {
        coordinates.setY(Integer.parseInt(y));
    }

    public void setEnginePower(String power) {
        this.enginePower = Integer.parseInt(power);
    }

    public void setNumOfWheels(String numOfWheels) {
        this.numberOfWheels = Long.parseLong(numOfWheels);
    }

    public void setType(String type) {
        this.type = VehicleType.valueOf(type);
    }

    public void setFuelType(String fuelType) {
        this.fuelType = FuelType.valueOf(fuelType);
    }

    public void setCreationDate (String date) {
        creationDate = date;
    }

    @Override
    public String toString() {
        return id.toString() + ", " + name + ", " + coordinates.toString() + ", " + creationDate.toString() +
                ", " + enginePower + ", " + numberOfWheels + ", " + type + ", " + fuelType;
    }
}
