package server;

import server.com.*;
import server.exceptions.NullValueException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.FileWriter;
import java.io.File;

// SCRIPT NOT TESTED

public final class Server {
    private File collection;
    String creationDate;
    private ArrayList<Vehicle> list = new ArrayList<>();

    private String info = "";

    public Server() {
        collection = new File("src/main/resources/Collection.csv");
        readFile();
    }

    public String connect (String request) {
        String response = "";
        response = (request + " poshol nahui");


        if (request.strip().equalsIgnoreCase("help")) {
            response = "help: вывести справку";
        }

        if (request.strip().equalsIgnoreCase("info")) {
            response = getInfo();
        }

        if (request.strip().equalsIgnoreCase("show")){
            response = getCollection();
        }

        if (request.strip().equalsIgnoreCase("save")) {
            save();
            response = "Collection saved";
        }

        if (request.strip().equalsIgnoreCase("clear")) {
            clear();
            response = "Collection cleared";
        }

        if (request.split(" ")[0].strip().equalsIgnoreCase("execute_script")) {
            File script = new File(request.split(" ")[1].strip());
            try {
                Scanner sc = new Scanner(script);
                String commands_str = "";
                while (sc.hasNextLine()) {
                    commands_str += sc.nextLine().strip();
                }
                List<String> commands = List.of(commands_str.split("\n"));
                for (String command: commands) {
                    response += connect(command)+";";
                }

            } catch (FileNotFoundException | NullPointerException e) {
                response = "Error: file not found";
            }

        }

        if (request.split(" ")[0].strip().equalsIgnoreCase("add")) {
            if (request.split(" ")[1].strip().equalsIgnoreCase("help")) {
                response = "{name, coordX, coordY, enginePower, numOfWheels, type, fuelType}";
            }
            else {
                List<String> l = Arrays.stream(request.split(" ")).toList();

                try {
                    add(String.join(" ", l.subList(1, l.size())));
                    response = "Element added";
                } catch (NullValueException e) {
                    response = "Error: Check inputted data: " + e.getMessage();
                }


            }


        }

        if (request.split(" ")[0].strip().equalsIgnoreCase("add_if_min")){
            List<String> l = Arrays.stream(request.split(" ")).toList();

            try {
                add_if_min(String.join(" ", l.subList(1, l.size())));
                response = "Element added";
            } catch (NullValueException e) {
                response = "Error: Check inputted data: " + e.getMessage();
            }
        }

        if (request.split(" ")[0].strip().equalsIgnoreCase("update")) {
            if (request.split(" ")[1].strip().equalsIgnoreCase("help")) {
                response = "id + {name, coordX, coordY, enginePower, numOfWheels, type, fuelType}";
            }
            else {
                List<String> l = Arrays.stream(request.split(" ")).toList();
                try {
                    update(l.get(1), String.join(" ", l.subList(2, l.size())));
                    response = "Element updated";
                } catch (NullValueException e) {
                    response = e.getMessage();
                }
            }
        }

        if (request.split(" ")[0].strip().equalsIgnoreCase("types")){

            if (request.strip().equalsIgnoreCase("types")) {
                response = "FuelTypes:;" + getFuelTypes() + ";VehicleTypes:;" + getVehicleTypes();
            }
            else if (request.split(" ")[1].strip().equalsIgnoreCase("fuel")){
                response = getFuelTypes();
            }
            else if (request.split(" ")[1].strip().equalsIgnoreCase("vehicle")){
                response = getVehicleTypes();
            }
        }

        if (request.split(" ")[0].strip().equalsIgnoreCase("insert")){
            List<String> l = Arrays.stream(request.split(" ")).toList();
            try {
                insert(l.get(1), String.join(" ", l.subList(2, l.size())));
                response = "Element inserted";
            } catch (NullValueException e) {
                response = e.getMessage();
            }
        }

        if (request.split(" ")[0].strip().equalsIgnoreCase("remove")) {
            remove(request.split(" ")[1].strip());
            response = "Element removed";
        }

        if (request.strip().equalsIgnoreCase("remove_first")) {
            remove(1+"");
            response = "Element removed";
        }

        if (request.split(" ")[0].strip().equalsIgnoreCase("remove_all_by_number_of_wheels")){
            removeByNumberOfWheels(request.split(" ")[1].strip());
            response = "Elements removed";
        }

        if (request.strip().equalsIgnoreCase("average_number_of_wheels")) {
            response = averageNumberOfWheels();
        }

        return response;
    }

    private void writeFile () {
        try (FileWriter outputFile = new FileWriter(collection);
        ) {

            outputFile.write(info);
            outputFile.write(listToString());
            outputFile.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readFile (){

        try {
            Scanner in = new Scanner(collection);

            String input;
            int i = 0;

            while (in.hasNextLine()) {
                i ++;

                input = in.nextLine();
                if (input.isEmpty()) {
                    info = createInfo();
                }

                else if (i > 0 && i < 5) {
                    if (input.split(": ")[0].strip().equalsIgnoreCase("Initialization date")){
                        creationDate = input.split(": ")[1];
                    }
                    info += input + "\n";
                }
                else {
                    list.add(new Vehicle(input));
                }
            }
            in.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private String createInfo () {
        creationDate = new Date().toString();
        String info = "Type: " + "ArrayList" + "\n";
        info += "Initialization date: " + creationDate + "\n";
        info += "Number of elements: " + list.size() + "\n";
        info += "Headers: Id, Name, Coordinates, CreationDate, EnginePower, NumberOfWheels, Type, FuelType" + "\n";
        return info;
    }

    private void updateInfo () {
        info = "Type: " + "ArrayList" + "\n";
        info += "Initialization date: " + creationDate + "\n";
        info += "Number of elements: " + list.size() + "\n";
        info += "Headers: Id, Name, Coordinates, CreationDate, EnginePower, NumberOfWheels, Type, FuelType" + "\n";
    }

    private String listToString () {
        String listString = "";
        if (!list.isEmpty()){
            for (int i = 0; i < list.size(); i++){
                listString += list.get(i).toString() + "\n";
            }
        }
        return listString;
    }

    private String getCollection () {
        return listToString().replace("\n", ";");
    }

    private void add (Vehicle element) throws NullValueException {
        element.setId("" + (list.size()+1));
        element.setCreationDate((new Date()).toString());
        if (element.getType() == null) {
            throw new NullValueException("VehicleType");
        }
        if (element.getFuelType() == null) {
            throw new NullValueException("FuelType");
        }
        list.add(element);
        updateInfo();
    }

    private void add (String str) throws NullValueException, NumberFormatException {
        Vehicle element = new Vehicle(str);
        add(element);
    }

    private void insert (String index, String str) throws NullValueException, NumberFormatException {
        ArrayList<Vehicle> tmp_list = new ArrayList<>();
        Vehicle element = new Vehicle(str);
        if (element.getType() == null) {
            throw new NullValueException("VehicleType");
        }
        if (element.getFuelType() == null) {
            throw new NullValueException("FuelType");
        }
        element.setId(index);
        element.setCreationDate(new Date().toString());
        int id = Integer.parseInt(index)-1;

        for (int i = 0; i < list.size(); i++) {
            if (id == (i+1)) {
                list.add(id, element);
            }
            if ((i+1) > id) {
                Vehicle tmp = list.get(i);
                tmp.setId(i+1+"");
                list.set(i, tmp);
            }
        }
        updateInfo();
    }

    private void add_if_min (String str) throws NullValueException, NumberFormatException {
        Vehicle element = new Vehicle(str.strip());

        if (element.countValue() < getMinimal()) {
            add(element);
            updateInfo();
        }
    }

    private void update (String id, String str) throws NullValueException, NumberFormatException {
        Vehicle element = new Vehicle (str.strip());
        if (element.getType() == null) {
            throw new NullValueException("VehicleType");
        }
        if (element.getFuelType() == null) {
            throw new NullValueException("FuelType");
        }
        element.setCreationDate(new Date().toString());
        element.setId(id);
        int i = Integer.parseInt(id);
        list.set(i-1, element);
        updateInfo();
    }

    private void remove (String id) {
        list.remove(Integer.parseInt(id)-1);
        list_update();
        updateInfo();
    }

    private void removeByNumberOfWheels (String numberOfWheels) {
        int num = Integer.parseInt(numberOfWheels);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNumberOfWheels() == num) {
                remove(i+1 +"");
            }
        }
    }

    private String averageNumberOfWheels () {
        double sum=0;
        for (Vehicle vehicle : list) {
            sum += vehicle.getNumberOfWheels();
        }

        return String.format("%8.2f",(sum/list.size())).replace(',', '.').strip();
    }

    private void clear () {
        list.clear();
        updateInfo();
    }

    private String getInfo () {
        return info.replace("\n", ";");
    }

    private void save () {
        writeFile();
    }

    private String getFuelTypes () {
        String out = Arrays.toString(FuelType.ALCOHOL.getDeclaringClass().getEnumConstants());
        return out.substring(1, out.length()-1).replace(", ", ";");
    }

    private String getVehicleTypes () {
        String out = Arrays.toString(VehicleType.DRONE.getDeclaringClass().getEnumConstants());
        return out.substring(1, out.length()-1).replace(", ", ";");
    }


    private void list_update(){
        for (int i = 0; i < list.size(); i ++) {
            Vehicle tmp =  list.get(i);
            tmp.setId((i+1)+"");
            list.set(i, tmp);
            updateInfo();
        }
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
