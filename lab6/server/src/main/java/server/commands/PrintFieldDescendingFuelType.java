package server.commands;

import common.Request;
import server.CollectionManager;

public class PrintFieldDescendingFuelType extends Command{
    private CollectionManager collection;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {}

    @Override
    public String help() {
        return "print_field_descending_fuel_type: display the fuelType field values of all elements in descending order";
    }

    @Override
    public String execute() {
        return collection.field_descending_fuel_type();
    }

}
