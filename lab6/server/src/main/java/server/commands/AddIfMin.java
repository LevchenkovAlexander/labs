package server.commands;

import common.Request;
import common.vehicle.Vehicle;
import server.CollectionManager;

public class AddIfMin extends Command{
    private CollectionManager collection;
    private Vehicle vehicle;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {
        this.vehicle = request.getVehicle();
    }

    @Override
    public String help() {
        return "add_if_min {element}: add a new element to a collection if its value is less than the smallest element of this collection";
    }

    @Override
    public String execute() {
        if (collection.addIfMin(vehicle)) {
            return "Element added";
        } else {
            return "Element is not minimal";
        }
    }
}
