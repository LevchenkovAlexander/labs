package server.com;

import server.exceptions.NullValueException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
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

    public Vehicle(String string) throws NumberFormatException {
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
        List<String> requires_number = List.of("CoordX", "CoordY", "EnginePower", "NumOfWheels");

        for (int i = 0; i < s.length; i++) {
            if (requires_number.contains(keys.get(i))) {
                try {
                    Integer.parseInt(s[i]);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException(keys.get(i) + " requires a number: " + s[i] + " is not a number");
                }
            }
            try {
                Method method = this.getClass().getMethod("set" + keys.get(i), String.class);
                method.invoke(this, s[i]);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore) { }

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

    public Integer getId () {
        return id;
    }
    public String getName () {
        return name;
    }

    public int getCoordX () {
        return coordinates.x;
    }

    public int getCoordY () {
        return coordinates.y;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public long getNumberOfWheels() {
        return numberOfWheels;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public VehicleType getType () {
        return type;
    }

    public long countValue () {
        long value = 0;

        Map<FuelType, Integer> fuel = Map.of(
                FuelType.GASOLINE, 100,
                FuelType.KEROSENE, 200,
                FuelType.ALCOHOL, 300,
                FuelType.MANPOWER, 400
        );

        Map<VehicleType, Integer> type = Map.of(
                VehicleType.DRONE, 100,
                VehicleType.MOTORCYCLE, 200,
                VehicleType.HOVERBOARD, 300,
                VehicleType.SPACESHIP, 400
        );

        value = Math.abs(coordinates.x) + Math.abs(coordinates.y) +
                enginePower + numberOfWheels + type.get(this.type) + fuel.get(this.fuelType);

        return value;
    }

    @Override
    public String toString() {
        return id.toString() + ", " + name + ", " + coordinates.toString() + ", " + creationDate.toString() +
                ", " + enginePower + ", " + numberOfWheels + ", " + type + ", " + fuelType;
    }
}
