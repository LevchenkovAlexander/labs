package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class Show extends Command {

    private final CollectionManager s1;

    public Show(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "show: display all elements of a collection";
    }

    @Override
    public Response execute(Request request) {

        return new Response(("Id, Name, Coordinates, CreationDate, EnginePower, NumberOfWheels, VehicleType, FuelType\n" +
                s1.listToString()).replace(", ", "\t"));
    }
}
