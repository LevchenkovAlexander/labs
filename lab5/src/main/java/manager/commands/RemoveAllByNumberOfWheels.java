package manager.commands;

import common.Request;
import common.Response;
import server.CollectionManager;

public class RemoveAllByNumberOfWheels extends Command{
    private final CollectionManager s1;

    public RemoveAllByNumberOfWheels(CollectionManager s1) {
        this.s1 = s1;
    }

    @Override
    public String help() {
        return "remove_all_by_number_of_wheels numberOfWheels: remove from the collection all elements whose numberOfWheels field value is equivalent to the specified one";
    }

    @Override
    public Response execute(Request request) {

        if (s1.removeByNumberOfWheels(request.getArg())) {
            return new Response("Elements removed");
        } else {
            return new Response("No elements with this number of wheels in the collection");
        }

    }
}
