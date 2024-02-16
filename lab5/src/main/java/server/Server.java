package server;

import server.com.*;
import server.exceptions.NullValueException;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;


public final class Server {
    private File collection;
    private ArrayList<Vehicle> list = new ArrayList<>();

    private String info = "";

    public Server() {
        collection = new File(System.getenv("CollectionPath"));
        try {
            readFile();
        } catch (NullValueException e) {System.out.println("Error: " + e.getMessage());}
    }

    public String connect (String request) {
        String response=null;
        try {
            String[] req = request.split(" ");
            String command = req[0];
            String element;
            String arg;
            switch (req.length) {
                case 1 ->
                    response = comms.get(command).execute();

                case 2 -> {
                    if (request.contains(";")) {
                        element = req[req.length - 1];
                        response = comms.get(command).execute(element);
                        System.out.println(response);
                    } else {
                        arg = req[req.length - 1];
                        response = comms.get(command).execute(arg);
                    }
                }

                case 3 -> {
                    arg = req[1];
                    element = req[2];
                    System.out.println(command + " " + arg + " " + element);
                    response = comms.get(command).execute(Integer.parseInt(arg), new Vehicle(element));
                }
            }
            if (response == null) {
                throw new NullValueException("Error: use <help> to see available commands");
            }
        } catch (NullValueException e) {
            response = "Error: " + e.getMessage();
        }


        return response;
    }

        Map<String, Command> comms = new HashMap<>() {{
            put("help", new Command() {
                @Override
                public String help() {
                    return "help: вывести справку по доступным командам";
                }

                @Override
                public String execute() {
                    StringBuilder out = new StringBuilder();
                    for (Command com : comms.values()) {
                        out.append(com.help()).append("/n");
                    }
                    return out.toString();
                }

                @Override
                public String execute(String string) throws NullValueException{
                    return null;
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }

            });
            put("info", new Command() {
                @Override
                public String help() {
                    return "info: вывести информацию о коллекции";
                }

                @Override
                public String execute() {
                    return info.replace("\n", "/n");
                }

               @Override
               public String execute(String string) throws NullValueException{
                    if (string.equals("help")) {
                        return help();
                    }
                   return null;
               }

               @Override
               public String execute(int id, Vehicle vehicle) throws NullValueException{
                   return null;
               }

           });
            put("show", new Command() {
                @Override
                public String help() {
                    return "show: вывести все элементы коллекции";
                }

                @Override
                public String execute() {
                    StringBuilder list_string = new StringBuilder("Id, Name, Coordinates, CreationDate, EnginePower, NumberOfWheels, VehicleType, FuelType/n");
                    for (Vehicle vehicle : list) {
                        list_string.append(vehicle.toString()).append("/n");
                    }

                    return list_string.toString().replace(", ", "\t");
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    return null;
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException{
                    return null;
                }

            });
            put("add", new Command() {
                @Override
                public String help() {
                    return "add {element}: добавить новый элемент в коллекцию";
                }

                @Override
                public String execute() {
                    StringBuilder response = new StringBuilder("add ");
                    for (Field field : Vehicle.class.getDeclaredFields()) {
                        String name = field.getName();
                        if (!name.equals("id") && !name.equals("creationDate")) {

                            switch (name) {
                                case "coordinates" -> response.append("coordinate_X" + ";" + "coordinate_Y" + ";");
                                case "type" ->
                                        response.append("type/nVehicle_Types:/n").append(getVehicleTypes()).append(";");
                                case "fuelType" ->
                                        response.append("fuelType/nFuel_Types:/n").append(getFuelTypes()).append(";");
                                default -> response.append(name).append(";");
                            }

                        }
                    }

                    return response.toString();
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    Vehicle element = new Vehicle(string);
                    element.setId("" + (list.size() + 1));
                    element.setCreationDate((new Date()).toString());

                    list.add(element);
                    updateInfo();
                    return "Element added";
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }


            });
            put("update", new Command() {
                @Override
                public String help() {
                    return "update id {element}: обновить значение элемента коллекции, id которого равен заданному";
                }

                @Override
                public String execute() {
                    StringBuilder response = new StringBuilder("update ");
                    for (Field field : Vehicle.class.getDeclaredFields()) {
                        String name = field.getName();
                        if (!name.equals("creationDate")) {

                            switch (name) {
                                case "coordinates" -> response.append("coordinate_X" + ";" + "coordinate_Y" + ";");
                                case "type" -> response.append("type/nVehicle_Types:/n").append(getVehicleTypes()).append(";");
                                case "fuelType" -> response.append("fuelType/nFuel_Types:/n").append(getFuelTypes()).append(";");
                                default -> response.append(name).append(";");
                            }

                        }
                    }

                    return response.toString();
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    return null;
                }

                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    vehicle.setId(id + "");
                    vehicle.setCreationDate(new Date().toString());

                    list.set(id, vehicle);
                    updateInfo();
                    return "Element updated";
                }

            });
            put("remove", new Command() {
                @Override
                public String help() {
                    return "remove id: удалить элемент из коллекции по его id";
                }

                @Override
                public String execute() throws NullValueException {
                    return null;
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    int id = Integer.parseInt(string);
                    list.remove(id - 1);
                    list_update();
                    return "Element removed";
                }
            });
            put("clear", new Command() {
                @Override
                public String help() {
                    return "clear: очистить коллекцию";
                }

                @Override
                public String execute() {
                    list.clear();
                    updateInfo();
                    return "Collection cleared";
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    return null;
                }


                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }
            });
            put("save", new Command() {
                @Override
                public String help() {
                    return "save: сохранить коллекцию";
                }

                @Override
                public String execute() {
                    try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(collection));
                    ) {

                        outputFile.write(info);
                        outputFile.write(listToString());
                        outputFile.flush();

                    } catch (IOException e) {
                        return "Error" + e.getMessage();
                    }
                    return "Collection saved";
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    return null;
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }

            });
            put("execute_script", new Command() {
                @Override
                public String help() {
                    return "execute_script file_name: считать и исполнить скрипт из указанного файла";
                }

                @Override
                public String execute() {
                    return "File_name required";
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    File script = new File (string);
                    StringBuilder response = new StringBuilder();
                    try {
                        Scanner sc = new Scanner(script);
                        StringBuilder commands_str = new StringBuilder();
                        while (sc.hasNextLine()) {
                            commands_str.append(sc.nextLine().strip());
                        }
                        List<String> commands = List.of(commands_str.toString().split("\n"));
                        for (String command : commands) {
                            response.append(connect(command)).append("/n");
                        }

                    } catch (FileNotFoundException | NullPointerException e) {
                        response = new StringBuilder("Error: file not found");
                    }
                    return response.toString();
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }

            });
            put("exit", new Command() {
                @Override
                public String help() {
                    return "exit: завершить программу (без сохранения)";
                }

                @Override
                public String execute() {
                    return "Shutting down...";
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    return null;
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }

            });
            put("insert", new Command() {
                @Override
                public String help() {
                    return "insert index {element}: добавить элемент в заданную позицию";
                }

                @Override
                public String execute() throws NullValueException {
                    StringBuilder response = new StringBuilder("insert ");
                    for (Field field : Vehicle.class.getDeclaredFields()) {
                        String name = field.getName();
                        if (!name.equals("creationDate")) {

                            switch (name) {
                                case "coordinates" -> response.append("coordinate_X" + ";" + "coordinate_Y" + ";");
                                case "type" -> response.append("type/nVehicle_Types:/n").append(getVehicleTypes()).append(";");
                                case "fuelType" -> response.append("fuelType/nFuel_Types:/n").append(getFuelTypes()).append(";");
                                default -> response.append(name).append(";");
                            }

                        }
                    }
                    return response.toString();
                }


                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    return null;
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    vehicle.setId(id+"");
                    vehicle.setCreationDate(new Date().toString());
                    if (id > list.size()) {
                        return "Error: id is too big";
                    }
                    list.add(id-1, vehicle);
                    list_update();
                    return "Element inserted";
                }

            });
            put("remove_first", new Command() {
                @Override
                public String help() {
                    return "remove_first: удалить первый элемент из коллекции";
                }

                @Override
                public String execute() throws NullValueException {
                    return comms.get("remove").execute("1");
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    return null;
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }
            });
            put("add_if_min", new Command(){

                @Override
                public String help() {
                    return "add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
                }

                @Override
                public String execute() throws NullValueException {
                    return null;
                }

                @Override
                public String execute(String string) throws NullValueException {
                    Vehicle vehicle = new Vehicle(string);
                    vehicle.setId(list.size()+"");
                    vehicle.setCreationDate(new Date().toString());
                    if (getMinimal() > vehicle.countValue()) {
                        return comms.get("add").execute(vehicle.toString());
                    }
                    return "Element is not minimal";
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }
            });

            put("remove_all_by_number_of_wheels", new Command() {
                @Override
                public String help() {
                    return "remove_all_by_number_of_wheels numberOfWheels: удалить из коллекции все элементы, значения поля numberOfWheels которого эквивалентно заданному";
                }

                @Override
                public String execute() throws NullValueException {
                    return null;
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    long numberOfWheels = Long.parseLong(string);
                    ArrayList<Integer> ids = new ArrayList<>();
                    for (Vehicle vehicle: list) {
                        if (vehicle.getNumberOfWheels() == numberOfWheels) {
                            ids.add(vehicle.getId());
                        }
                    }
                    for (int i = ids.size()-1; i > -1; i--) {
                        comms.get("remove").execute(ids.get(i) +"");
                    }
                    list_update();
                    return "Elements removed";
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }
            });
            put("average_of_number_of_wheels", new Command() {
                @Override
                public String help() {
                    return "average_of_number_of_wheels: вывести среднее значение поля numberOfWheels для всех элементов коллекции";
                }

                @Override
                public String execute() throws NullValueException {
                    double sum=0;
                    for (Vehicle vehicle : list) {
                        sum += vehicle.getNumberOfWheels();
                    }

                    return String.format("%8.2f",(sum/list.size())).replace(',', '.').strip();
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    return null;
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }
            });
            put("print_field_descending_fuel_type", new Command() {
                @Override
                public String help() {
                    return "print_field_descending_fuel_type: вывести значения поля fuelType всех элементов в порядке убывания";
                }

                @Override
                public String execute() throws NullValueException {
                    ArrayList<FuelType> fuels = new ArrayList<>();
                    StringBuilder str = new StringBuilder();
                    for (Vehicle vehicle: list) {
                        fuels.add(vehicle.getFuelType());
                    }

                    Collections.sort(fuels);
                    for (FuelType fuel : fuels) {
                        str.append(fuel.toString()).append("/n");
                    }
                    return str.toString();
                }

                @Override
                public String execute(String string) throws NullValueException {
                    if (string.equals("help")) {
                        return help();
                    }
                    return null;
                }

                @Override
                public String execute(int id, Vehicle vehicle) throws NullValueException {
                    return null;
                }
            });

        }};




    private void readFile () throws NullValueException {

        try {
            Scanner in = new Scanner(collection);
            StringBuilder inf = new StringBuilder();

            String input;
            if (in.hasNextLine()) {
                int i = 0;
                while (in.hasNextLine()) {
                    input = in.nextLine();
                    i++;
                    if (i < 4) {
                        inf.append(input).append("\n");
                    } else {
                        list.add(new Vehicle(input));
                    }
                }
                info = inf.toString();

            }
            else {
                createInfo();
            }
            in.close();
        }
        catch (FileNotFoundException ignore) {}


    }

    private void createInfo () {
        info = "Type: " + "ArrayList" + "\n";
        info += "Initialization date: " +new Date().toString() + "\n";
        info += "Number of elements: " + list.size() + "\n";
    }

    private void updateInfo () {
        String[] inf = info.split("\n");
        info = inf[0] + "\n" + inf[1] + "\n";
        info += "Number of elements: " + list.size() + "\n";
    }

    private String listToString () {
        StringBuilder listString = new StringBuilder();
        if (!list.isEmpty()){
            for (Vehicle vehicle : list) {
                listString.append(vehicle.toString()).append("\n");
            }
        }
        return listString.toString();
    }


    private String getFuelTypes () {
        String out = Arrays.toString(FuelType.ALCOHOL.getDeclaringClass().getEnumConstants());
        return out.substring(1, out.length()-1).replace(", ", "/n");
    }

    private String getVehicleTypes () {
        String out = Arrays.toString(VehicleType.DRONE.getDeclaringClass().getEnumConstants());
        return out.substring(1, out.length()-1).replace(", ", "/n");
    }


    private void list_update() throws NullValueException {
        for (int i = 0; i < list.size(); i ++) {
            Vehicle tmp =  list.get(i);
            tmp.setId((i+1)+"");
            list.set(i, tmp);
        }
        updateInfo();
    }

    private long getMinimal () {
        long min = list.get(0).countValue();
        for (int i = 1; i < list.size(); i++) {
            long tmp = list.get(i).countValue();
            if (min > tmp) {
                min = tmp;
            }
        }
        return min;
    }

}
