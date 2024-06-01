package server.commands;

import common.Request;
import common.vehicle.Vehicle;
import server.CollectionManager;

public class Update extends Command{
    private CollectionManager collection;
    private int id;
    private Vehicle vehicle;

    public void setCollection (CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {
        this.id = request.getArg();
        this.vehicle = request.getVehicle();
    }

    @Override
    public String help() {
        return "update id {element}: update the value of a collection element whose id is equal to a given one";
    }

    @Override
    public String execute() {
        try {
            collection.update(id, vehicle);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return "Element updated";
    }
}
