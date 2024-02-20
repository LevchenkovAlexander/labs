package common;

/**
 * Interface for creates command
 */
public interface Command {
    String help();
    String execute(Request request);
}
