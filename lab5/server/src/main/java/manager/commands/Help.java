package manager.commands;

import common.Request;
import common.Response;
import java.util.Map;

public class Help extends Command {

    private final Map<String, Command> commands;

    public Help(Map<String, Command> commands) {
        this.commands = commands;
    }


    @Override
    public void create(Request request) {}

    @Override
    public String help() {
        return "help: display help on available commands";
    }

    @Override
    public String execute() {
        StringBuilder out = new StringBuilder();
        for (Command com : commands.values()) {
            out.append(com.help()).append("\n");
        }
        return out.toString();
    }
}
