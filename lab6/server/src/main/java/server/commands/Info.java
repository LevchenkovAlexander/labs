package server.commands;

import common.Request;
import server.CollectionManager;

public class Info extends Command {

    private CollectionManager collection;

    public void setCollection (CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {

    }

    @Override
    public String help() {
        return "info: display information about the collection";
    }

    @Override
    public String execute() {
        return collection.getInfo();
    }

}
