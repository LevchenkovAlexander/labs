package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;


public class Exit extends Command{
    private CollectionManager collection;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {}

    @Override
    public String help() {
        return "exit: save end the program";
    }

    @Override
    public String execute() {
        collection.writeFile();
        return "Shutting down...";
    }

}
