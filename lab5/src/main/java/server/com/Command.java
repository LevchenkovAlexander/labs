package server.com;

import server.exceptions.NullValueException;

import java.io.File;

/**
 * Interface for creates command
 */
public interface Command {
    public String help ();
    public String execute () throws NullValueException;
    public String execute (String string) throws NullValueException;
    public String execute (int id, Vehicle vehicle) throws NullValueException;
}
