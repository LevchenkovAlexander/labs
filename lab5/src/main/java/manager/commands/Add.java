package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class Add extends Command{

    private final CollectionManager s1;

    public Add(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "add {element}: add a new element to the collection";
    }

    @Override
    public Response execute(Request request) {
        s1.add(request.getVehicle());
        System.out.println("added new element" + request.getVehicle().toString());
        return new Response("Element added");
    }
}
