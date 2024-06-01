package manager.commands;

import common.Request;
import server.CollectionManager;

public class Show extends Command {

    private CollectionManager collection;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {}

    @Override
    public String help() {
        return "show: display all elements of a collection";
    }

    @Override
    public String execute() {

        return ("Id, Name, Coordinates, CreationDate, EnginePower, NumberOfWheels, VehicleType, FuelType\n" +
                collection.listToString()).replace(", ", "\t");
    }
}
