package server.commands;

import common.Request;
import server.CollectionManager;


public class Remove extends Command{

    private CollectionManager collection;
    private int id;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {
        this.id = request.getArg();
    }

    @Override
    public String help() {
        return "remove id: remove an element from a collection by its id";
    }

    @Override
    public String execute() {
        try {
            collection.remove(id);
            return "Element removed";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
