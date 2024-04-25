package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class AddIfMin extends Command{
    private final CollectionManager s1;

    public AddIfMin(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "add_if_min {element}: add a new element to a collection if its value is less than the smallest element of this collection";
    }

    @Override
    public Response execute(Request request) {
        if (s1.addIfMin(request.getVehicle())) {
            return new Response("Element added");
        } else {
            return new Response("Element is not minimal");
        }
    }
}
