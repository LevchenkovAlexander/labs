package manager;

import common.Request;

/**
 * Interface for creates command
 */
public interface Command {
    String help();
    String execute(Request request);
}
