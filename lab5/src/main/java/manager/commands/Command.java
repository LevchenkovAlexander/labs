package manager.commands;

import common.*;
import common.vehicle.Vehicle;

public abstract class Command {

    public abstract String help();
    public abstract Response execute(Request request);
}
