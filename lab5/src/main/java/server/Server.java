package server;

import server.com.*;
import server.exceptions.NullValueException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
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


        if (request.equals("help")) {
            response = "help: вывести справку";
        }

        if (request.equals("info")) {
            response = getInfo();
        }

        if (request.equals("show")){
            response = getCollection();
        }

        if (request.equals("save")) {
            save();
            response = "Collection saved";
        }

        if (request.equals("clear")) {
            clear();
            response = "Collection cleared";
        }

        if (request.split(" ")[0].equals("add")) {
            if (request.split(" ").length == 1) {
                response = "";
                for (Field field : Vehicle.class.getDeclaredFields()) {
                    String name = field.getName();
                    if (name.equals("id") || name.equals("creationDate")) {
                    } else {
                        if (name.equals("coordinates")) {
                            response += "coordinate X" + ";" + "coordinate Y" + ";";
                        } else {
                            response += name + ";";
                        }

                    }
                }
            }
            else {
                try {
                    add (new Vehicle(request.split(" ")[1]));
                    response = "Element added";
                }   catch (NullValueException | NullPointerException | NumberFormatException e) {
                    response = "Error: " + e.getMessage();
                }
            }
//            for (int i = 0; i < list.size(); i++) {
//                System.out.println(list.get(i).toString());
//            }
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
                    response += connect(command)+"/n";
                }

            } catch (FileNotFoundException | NullPointerException e) {
                response = "Error: file not found";
            }

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
            while (in.hasNextLine()){
                i ++;
                input = in.nextLine();

                if (i < 4) {
                    if (i == 2) {
                        creationDate = input.split(":")[1].strip();
                        if (creationDate.equals("null")) {
                            creationDate = new Date().toString();
                            input = "Initialization date: " + creationDate;
                        }
                    }
                    info += input+"\n";
                }
                else{
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
        return info.replace("\n", "/n");
    }

    private void save () {
        writeFile();
    }

    private String getFuelTypes () {
        String out = Arrays.toString(FuelType.ALCOHOL.getDeclaringClass().getEnumConstants());
        return out.substring(1, out.length()-1).replace(", ", "/n");
    }

    private String getVehicleTypes () {
        String out = Arrays.toString(VehicleType.DRONE.getDeclaringClass().getEnumConstants());
        return out.substring(1, out.length()-1).replace(", ", "/n");
    }

    private String getCollection () {
        return listToString().replace("\n", "/n");
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
