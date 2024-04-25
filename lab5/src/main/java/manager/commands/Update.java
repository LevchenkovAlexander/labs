package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class Update extends Command{
    private final CollectionManager s1;

    public Update (CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "update id {element}: update the value of a collection element whose id is equal to a given one";
    }

    @Override
    public Response execute(Request request) {
        try {
            s1.update(request.getArg(), request.getVehicle());
        } catch (IllegalArgumentException e) {
            return new Response(e.getMessage());
        }
        System.out.println("Element with id " + request.getArg() + "was updated to " + request.getVehicle());
        return new Response("Element updated");
    }
}
