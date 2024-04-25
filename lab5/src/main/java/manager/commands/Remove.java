package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;


public class Remove extends Command{

    private final CollectionManager s1;

    public Remove(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "remove id: remove an element from a collection by its id";
    }

    @Override
    public Response execute(Request request) {
        try {
            s1.remove(request.getArg());
            System.out.println("Element removed with id: " + request.getArg());
            return new Response("Element removed");
        } catch (IllegalArgumentException e) {
            return new Response(e.getMessage());
        }
    }
}
