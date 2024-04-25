package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

import java.text.DecimalFormat;

public class AverageOfNumberOfWheels extends Command {
    private final CollectionManager s1;

    public AverageOfNumberOfWheels(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "average_of_number_of_wheels: display the average value of the numberOfWheels field for all elements of the collection";
    }

    @Override
    public Response execute(Request request) {
        return new Response(new DecimalFormat("#0.00").format(s1.averageNumberOfWheels()));
    }
}
