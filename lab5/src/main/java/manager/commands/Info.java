package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class Info extends Command {

    private final CollectionManager s1;

    public Info (CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "info: display information about the collection";
    }

    @Override
    public Response execute(Request request) {
        return new Response(s1.getInfo());
    }

}
