package server.commands;

import common.Request;
import server.CollectionManager;

public class LastId extends Command{

    public LastId () {}

    @Override
    public void create(Request request) {}

    @Override
    public String help() {
        return "private command. get latest available ID for vehicle";
    }

    @Override
    public String execute() {
        return "" + CollectionManager.lastId();
    }

}
