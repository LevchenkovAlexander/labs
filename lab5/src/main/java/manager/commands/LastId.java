package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class LastId extends Command{

    public LastId () {}

    @Override
    public String help() {
        return "private command. get latest available ID for vehicle";
    }


    @Override
    public Response execute(Request request) {
        return new Response("" + CollectionManager.lastId());
    }

}
