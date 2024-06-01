package common;


import common.vehicle.StrToV;
import common.vehicle.Vehicle;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 15L;
    
    String command;
    int arg = -1;
    String fileName = "";
    Vehicle vehicle;

    public Request (String request) {
        String[] s = request.split(" ");
        command = s[0];
        if (s.length == 2) {
            if (s[1].contains(";")) {
                vehicle = StrToV.exec(s[1]);
            } else {
                try {
                    arg = Integer.parseInt(s[1]);
                } catch (NumberFormatException e) {
                    fileName = s[1];
                }
            }
        }
        if (s.length == 3) {
            vehicle = StrToV.exec(s[2]);
            arg = Integer.parseInt(s[1]);
        }

    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getArg() {
        return arg;
    }

    public void setArg(int arg) {
        this.arg = arg;
    }

    public String getFileName() {
        return fileName;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(command);
        if (arg != -1) {
            str.append(arg);
        }
        if (fileName != null) {
            str.append(fileName);
        }
        if (vehicle != null) {
            str.append(vehicle.toString());
        }
        return str.toString();
    }

}
