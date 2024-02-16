package server.com;

import server.exceptions.NullValueException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Vehicle implements Comparable <Vehicle>{
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

    public Vehicle(String string) throws NumberFormatException, NullValueException {
        Map<Integer, String> keys;
        String[] s;
        if (string.contains(";")) {
            s = string.split(";");
            keys = Map.of(
                    0, "Name",
                    1, "CoordX",
                    2, "CoordY",
                    3, "EnginePower",
                    4, "NumOfWheels",
                    5, "Type",
                    6, "FuelType"
            );
        } else {
            s = string.split(", ");
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

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                if (e.getCause() instanceof NullValueException || e.getCause() instanceof IllegalArgumentException) {
                    throw new NullValueException(e.getCause().getMessage());
                }
            }

        }
    }

    public void setId (String id) throws NullValueException{
        if (id.equals("null")) {
            throw new NullValueException("Id cannot be null");
        }
        this.id = Integer.valueOf(id);
    }

    public void setName(String name) throws NullValueException {
        if (name.equals("null")){
            throw new NullValueException("Name cannot be null");
        }
        this.name = name;
    }

    public void setCoordX(String x) throws NullValueException {
        if (x.equals("null")) {
            throw new NullValueException("Coordinate x cannot be null");
        }
        coordinates.setX(Integer.parseInt(x));
    }

    public void setCoordY(String y) throws NullValueException{
        if (y.equals("null")) {
            throw new NullValueException("Coordinate y cannot be null");
        }
        coordinates.setY(Integer.parseInt(y));
    }

    public void setCreationDate (String date) throws NullValueException {
        if (date.equals("null")) {
            throw new NullValueException("Date cannot be null");
        }
        creationDate = date;
    }
    public void setEnginePower(String power) throws NullValueException {
        int pow = Integer.parseInt(power);
        if (pow <= 0) {
            throw new NullValueException("Engine power must be greater than 0");
        }
        this.enginePower = pow;
    }

    public void setNumOfWheels(String numOfWheels) throws NullValueException {
        long num =Long.parseLong(numOfWheels);
        if (num <= 0) {
            throw new NullValueException("Number of wheels must be greater than 0");
        }
        this.numberOfWheels = num;
    }

    public void setType(String type) throws IllegalArgumentException {
        try {
            this.type = VehicleType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw  new IllegalArgumentException("Unknown vehicle type");
        }

    }

    public void setFuelType(String fuelType) throws IllegalArgumentException{
        try {
            this.fuelType = FuelType.valueOf(fuelType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw  new IllegalArgumentException("Unknown fuel type");
        }

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
        return id.toString() + ", " + name + ", " + coordinates.toString() + ", " + creationDate +
                ", " + enginePower + ", " + numberOfWheels + ", " + type + ", " + fuelType;
    }

    public boolean equals(Vehicle vehicle) {
        return this.countValue() == vehicle.countValue();
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        return Long.compare(this.countValue(), vehicle.countValue());

    }
}
