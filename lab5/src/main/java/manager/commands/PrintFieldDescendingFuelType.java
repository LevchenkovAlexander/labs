package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class PrintFieldDescendingFuelType extends Command{
    private final CollectionManager s1;

    public PrintFieldDescendingFuelType(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "print_field_descending_fuel_type: display the fuelType field values of all elements in descending order";
    }

    @Override
    public Response execute(Request request) {
        return new Response(s1.field_descending_fuel_type());
    }

}
