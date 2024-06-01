package manager.commands;

import common.Request;
import server.CollectionManager;

public class RemoveAllByNumberOfWheels extends Command{
    private CollectionManager collection;
    private int num;


    @Override
    public void create(Request request) {
        this.num = request.getArg();
    }

    @Override
    public String help() {
        return "remove_all_by_number_of_wheels numberOfWheels: remove from the collection all elements whose numberOfWheels field value is equivalent to the specified one";
    }

    @Override
    public String execute() {

        if (collection.removeByNumberOfWheels(num)) {
            return "Elements removed";
        } else {
            return "No elements with this number of wheels in the collection";
        }

    }

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }
}
