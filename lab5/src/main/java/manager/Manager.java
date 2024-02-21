package manager;

import common.Request;
import server.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Class for establishing connection between Client and Server
 */
public final class Manager {
    private final Server s1;
    public Manager () {
        s1 = new Server();
    }

    /**
     * Method for connecting to Server
     * @param request User request
     * @return Server response
     */
    public String connect (Request request) {
        return commands.get(request.getCommand()).execute(request);
    }

    private final Map<String, Command> commands = new LinkedHashMap<>() {
        @Override
        public Command get(Object key) {

            if (!containsKey(key)) {
                return new Command() {
                    @Override
                    public String help() {
                        return null;
                    }

                    @Override
                    public String execute(Request request) {
                        return "Error: unknown command. Type <help> to see all the available commands";
                    }

                };
            }
            return super.get(key);
        }

        {
            put("help", new Command() {
                @Override
                public String help() {
                    return "help: display help on available commands";
                }

                @Override
                public String execute(Request request) {
                    StringBuilder out = new StringBuilder();
                    for (Command com : commands.values()) {
                        out.append(com.help()).append("/n");
                    }
                    return out.toString();
                }

            });
            put("info", new Command() {
                @Override
                public String help() {
                    return "info: display information about the collection";
                }

                @Override
                public String execute(Request request) {
                    return s1.getInfo().replace("\n", "/n");
                }


            });
            put("show", new Command() {
                @Override
                public String help() {
                    return "show: display all elements of a collection";
                }

                @Override
                public String execute(Request request) {

                    return ("Id, Name, Coordinates, CreationDate, EnginePower, NumberOfWheels, VehicleType, FuelType/n" +
                            s1.listToString()).replace(", ", "\t");
                }


            });
            put("add", new Command() {
                @Override
                public String help() {
                    return "add {element}: add a new element to the collection";
                }

                @Override
                public String execute(Request request) {
                    s1.add(request.getVehicle());
                    return "Element added";
                }


            });
            put("update", new Command() {
                @Override
                public String help() {
                    return "update id {element}: update the value of a collection element whose id is equal to a given one";
                }

                @Override
                public String execute(Request request) {
                    s1.update(request.getArg(), request.getVehicle());
                    return "Element updated";
                }


            });
            put("remove_by_id", new Command() {
                @Override
                public String help() {
                    return "remove id: remove an element from a collection by its id";
                }

                @Override
                public String execute (Request request) {
                    try{
                        s1.remove(request.getArg());
                        return "Element removed";
                    } catch (IllegalArgumentException e) {
                        return e.getMessage();
                    }
                }

            });
            put("clear", new Command() {
                @Override
                public String help() {
                    return "clear: clear the collection";
                }

                @Override
                public String execute(Request request) {
                    s1.clear();
                    return "Collection cleared";
                }

            });
            put("save", new Command() {
                @Override
                public String help() {
                    return "save: save the collection";
                }

                @Override
                public String execute(Request request) {
                    s1.writeFile();
                    return "Collection saved";
                }

            });
            put("execute_script", new Command() {
                @Override
                public String help() {
                    return "execute_script file_name: read and execute the script from the specified file";
                }

                @Override
                public String execute(Request request) {

                    File script = new File(request.getFileName());
                    StringBuilder response = new StringBuilder();
                    try {
                        Scanner sc = new Scanner(script);
                        StringBuilder commands_str = new StringBuilder();
                        while (sc.hasNextLine()) {
                            commands_str.append(sc.nextLine().strip());
                        }

                        for (String req : commands_str.toString().split("\n")) {
                            response.append(connect(new Request(req))).append("/n");
                        }

                    } catch (FileNotFoundException | NullPointerException e) {
                        response = new StringBuilder("Error: file not found");
                    }
                    return response.toString();
                }

            });
            put("exit", new Command() {
                @Override
                public String help() {
                    return "exit: end the program (without saving)";
                }

                @Override
                public String execute(Request request) {
                    return "Shutting down...";
                }

            });
            put("insert", new Command() {
                @Override
                public String help() {
                    return "insert index {element}: add an element at a given position";
                }

                @Override
                public String execute(Request request) {
                    s1.add(request.getArg(), request.getVehicle());
                    return "Element inserted";
                }

            });
            put("remove_first", new Command() {
                @Override
                public String help() {
                    return "remove_first: remove the first element from the collection";
                }

                @Override
                public String execute(Request request) {
                    try {
                        s1.remove_first();
                        return "Element removed";
                    } catch (IllegalArgumentException e) {
                        return e.getMessage();
                    }
                }


            });
            put("add_if_min", new Command() {

                @Override
                public String help() {
                    return "add_if_min {element}: add a new element to a collection if its value is less than the smallest element of this collection";
                }

                @Override
                public String execute(Request request) {
                    if (s1.addIfMin(request.getVehicle())) {
                        return "Element added";
                    } else {
                        return "Element is not minimal";
                    }
                }

            });
            put("remove_all_by_number_of_wheels", new Command() {
                @Override
                public String help() {
                    return "remove_all_by_number_of_wheels numberOfWheels: remove from the collection all elements whose numberOfWheels field value is equivalent to the specified one";
                }

                @Override
                public String execute(Request request) {

                    if (s1.removeByNumberOfWheels(request.getArg())) {
                        return "Elements removed";
                    } else {
                        return "No elements with this number of wheels in the collection";
                    }

                }

            });
            put("average_of_number_of_wheels", new Command() {
                @Override
                public String help() {
                    return "average_of_number_of_wheels: display the average value of the numberOfWheels field for all elements of the collection";
                }

                @Override
                public String execute(Request request) {
                    return new DecimalFormat("#0.00").format(s1.averageNumberOfWheels());
                }

            });
            put("print_field_descending_fuel_type", new Command() {
                @Override
                public String help() {
                    return "print_field_descending_fuel_type: display the fuelType field values of all elements in descending order";
                }

                @Override
                public String execute(Request request) {
                   return  s1.field_descending_fuel_type();
                }

            });
        }

    };

}
