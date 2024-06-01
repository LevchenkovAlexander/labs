package server;

import common.*;
import server.commands.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class for establishing connection between Client and Server
 */
public final class CommandManager {
    private static final CollectionManager s1 = new CollectionManager();
    private final Map<String, Command> commands = new LinkedHashMap<>() {
        @Override
        public Command get(Object key) {
            return super.get(key);
        }
    };

    public CommandManager() {

        commands.put("last_id", new LastId());
        commands.put("help", new Help(commands));
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("add", new Add());
        commands.put("update", new Update());
        commands.put("remove", new Remove());
        commands.put("clear", new Clear());
        commands.put("exit", new Exit());
        commands.put("insert", new Insert());
        commands.put("remove_first", new RemoveFirst());
        commands.put("add_if_min", new AddIfMin());
        commands.put("remove_all_by_number_of_wheels", new RemoveAllByNumberOfWheels());
        commands.put("average_of_number_of_wheels", new AverageOfNumberOfWheels());
        commands.put("print_field_descending_fuel_type", new PrintFieldDescendingFuelType());

    }

    /**
     * Method for connecting to Server
     *
     * @param request User request
     * @return Server response
     */
    public String connect(Request request) {
        try {
            Command command = commands.get(request.getCommand());
            System.out.println("Executing: " + request.getCommand() + "...");
            command.setCollection(s1);
            command.create(request);
            System.out.println("Done");
            return command.execute();
        } catch (IllegalArgumentException | NullPointerException e) {
            return e.getMessage();
        }
    }

}


