package server.commands;

import common.Request;
import common.vehicle.Vehicle;
import server.CollectionManager;

public class Add extends Command{

    private CollectionManager collection;
    private Vehicle vehicle = null;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {
        System.out.println("created");
        this.vehicle = request.getVehicle();
    }

    @Override
    public String help() {
        return "add {element}: add a new element to the collection";
    }

    @Override
    public String execute() {
        collection.add(vehicle);
        System.out.println("added new element" + vehicle.toString());
        return "Element added";
    }

}
