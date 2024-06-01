package manager.commands;

import common.Request;
import server.CollectionManager;

import java.text.DecimalFormat;

public class AverageOfNumberOfWheels extends Command {
    private CollectionManager collection;

    public void setCollection(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public void create(Request request) {}
    @Override
    public String help() {
        return "average_of_number_of_wheels: display the average value of the numberOfWheels field for all elements of the collection";
    }

    @Override
    public String execute() {
        return new DecimalFormat("#0.00").format(collection.averageNumberOfWheels());
    }
}
