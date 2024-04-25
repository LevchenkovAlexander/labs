package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class RemoveFirst extends Command {
    private final CollectionManager s1;

    public RemoveFirst(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "remove_first: remove the first element from the collection";
    }

    @Override
    public Response execute(Request request) {
        try {
            s1.remove_first();
            System.out.println("First element was removed");
            return new Response("Element removed");
        } catch (IllegalArgumentException e) {
            return new Response(e.getMessage());
        }
    }
}
