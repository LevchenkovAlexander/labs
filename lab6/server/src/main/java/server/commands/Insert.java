package server.commands;

import common.Request;
import common.vehicle.Vehicle;
import server.CollectionManager;

public class Insert extends Command{

    private CollectionManager collection;
    private Integer arg;
    private Vehicle vehicle;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {
        this.arg = request.getArg();
        this.vehicle = request.getVehicle();
    }

    @Override
    public String help() {
        return "insert index {element}: add an element at a given position";
    }

    @Override
    public String execute() {
        collection.add(arg, vehicle);
        return "Element inserted";
    }
}
