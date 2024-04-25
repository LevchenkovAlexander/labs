package manager;

import common.*;
import manager.commands.*;
import server.CollectionManager;

import java.util.*;

/**
 * Class for establishing connection between Client and Server
 */
public final class CommandManager {
    private static final CollectionManager s1 = new CollectionManager();
    private final Map<String, Command> commands = new LinkedHashMap<>() {
        @Override
        public Command get(Object key) {
            // TODO
            return super.get(key);
        }
    };

    public CommandManager() {

        commands.put("help", new Help(commands));
        commands.put("info", new Info(s1));
        commands.put("show", new Show(s1));
        commands.put("add", new Add(s1));
        commands.put("update", new Update(s1));
        commands.put("remove", new Remove(s1));
        commands.put("clear", new Clear(s1));
        commands.put("exit", new Exit(s1));
        commands.put("insert", new Insert(s1));
        commands.put("remove_first", new RemoveFirst(s1));
        commands.put("add_if_min", new AddIfMin(s1));
        commands.put("remove_all_by_number_of_wheels", new RemoveAllByNumberOfWheels(s1));
        commands.put("average_of_number_of_wheels", new AverageOfNumberOfWheels(s1));
        commands.put("print_field_descending_fuel_type", new PrintFieldDescendingFuelType(s1));


    }

    /**
     * Method for connecting to Server
     *
     * @param request User request
     * @return Server response
     */
    public Response connect(Request request) {
        try {
            System.out.println(request.getCommand());

            return commands.get(request.getCommand()).execute(request);
        } catch (IllegalArgumentException e) {
            return new Response(e.getMessage());
        }
    }

}


