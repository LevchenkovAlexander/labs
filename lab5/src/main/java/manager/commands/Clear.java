package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class Clear extends Command{

    private final CollectionManager s1;

    public Clear(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "clear: clear the collection";
    }

    @Override
    public Response execute(Request request) {
        s1.clear();
        System.out.println("Collection cleared");
        return new Response("Collection cleared");
    }
}
