package common.vehicle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Class for containing vehicle
 */
public class Vehicle implements Comparable  <Vehicle>, Serializable {
    private Integer id;
    private String name;
    private final Coordinates coordinates;
    private Date creationDate;
    private int enginePower;
    private long numberOfWheels;
    private VehicleType type;
    private FuelType fuelType;


    public Vehicle(Integer id, String name, Coordinates coordinates, Date creationDate,
                   int enginePower, long numberOfWheels, VehicleType type, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.type = type;
        this.fuelType = fuelType;
    }


    public void setName(String name) {
       this.name = name;
    }

    public void setId (Integer id) {
        this.id = id;
    }
    public void setCoordinates(int x, int y) {
        coordinates.setX(x);
        coordinates.setY(y);
    }

    public void setCreationDate () {
        creationDate = new Date();
    }
    public void setEnginePower(int power) {
        this.enginePower = power;
    }

    public void setNumOfWheels(long numOfWheels) {
        this.numberOfWheels = numOfWheels;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public void setFuelType(FuelType fuelType) throws IllegalArgumentException{
        this.fuelType = fuelType;

    }


    public Integer getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates () {return coordinates;}

    public int getEnginePower() {
        return enginePower;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public long getNumberOfWheels() {
        return numberOfWheels;
    }


    public VehicleType getType () {
        return type;
    }

    public long countValue () {
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

        return Math.abs(coordinates.x) + Math.abs(coordinates.y) +
                enginePower + numberOfWheels + type.get(this.type) + fuel.get(this.fuelType);
    }

    @Override
    public String toString() {
        return id.toString() + ", " + name + ", " + coordinates.toString() + ", " + creationDate.toString() +
                ", " + enginePower + ", " + numberOfWheels + ", " + type + ", " + fuelType;
    }

    public boolean equals(Vehicle vehicle) {
        return this.countValue() == vehicle.countValue();
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        return Long.compare(this.countValue(), vehicle.countValue());

    }

    public static String getVehicleTypes () {
        String out = Arrays.toString(VehicleType.DRONE.getDeclaringClass().getEnumConstants());
        return out.substring(1, out.length()-1).replace(", ", "/n");
    }

    public static String getFuelTypes () {
        String out = Arrays.toString(FuelType.ALCOHOL.getDeclaringClass().getEnumConstants());
        return out.substring(1, out.length()-1).replace(", ", "/n");
    }


}
