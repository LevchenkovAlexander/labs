package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class Insert extends Command{

    private final CollectionManager s1;

    public Insert(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "insert index {element}: add an element at a given position";
    }

    @Override
    public Response execute(Request request) {
        s1.add(request.getArg(), request.getVehicle());
        return new Response("Element inserted");
    }
}
