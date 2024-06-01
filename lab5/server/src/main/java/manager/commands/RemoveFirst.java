package manager.commands;

import common.Request;
import server.CollectionManager;

public class RemoveFirst extends Command {
    private CollectionManager collection;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {

    }

    @Override
    public String help() {
        return "remove_first: remove the first element from the collection";
    }

    @Override
    public String execute() {
        try {
            collection.remove_first();
            System.out.println("First element was removed");
            return "Element removed";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
