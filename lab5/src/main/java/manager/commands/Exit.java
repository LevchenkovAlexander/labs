package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;


public class Exit extends Command{
    private final CollectionManager s1;

    public Exit(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "exit: save end the program";
    }

    @Override
    public Response execute(Request request) {
        s1.writeFile();
        return new Response("Shutting down...");
    }

}
