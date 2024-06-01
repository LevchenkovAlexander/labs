package manager.commands;

import common.Request;
import server.CollectionManager;

public class Clear extends Command{

    private CollectionManager collection;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {}

    @Override
    public String help() {
        return "clear: clear the collection";
    }

    @Override
    public String execute() {
        collection.clear();
        System.out.println("Collection cleared");
        return "Collection cleared";
    }
}
