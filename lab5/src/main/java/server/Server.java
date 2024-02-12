package server;

import server.com.*;
import server.exceptions.NullValueException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;

// пофиксить необновляемое количество элементов в info
//  + FUEL и VEHICLE TYPE (не добавляются через add)
// в принципе add косячно работает (добавить проверки на количество введеных элементов)

public final class Server {
    private File collection;
    String creationDate;
    private ArrayList<Vehicle> list = new ArrayList<>();

    private String info = "";

    public Server() {
        collection = new File("src/main/resources/Collection.csv");
        readFile();
    }

    public void writeFile () {
        try (FileWriter outputFile = new FileWriter(collection);
        ) {

            outputFile.write(info);
            outputFile.write(listToString());
            outputFile.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readFile (){

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

    public String getCollection () {
        return listToString().replace("\n", ";");
    }

    public void add (Vehicle element) throws NullValueException {
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

    public void add (String str) throws NullValueException {
        add(new Vehicle(str.strip()));
    }

    public void insert (String index, String str) {
        ArrayList<Vehicle> tmp_list = new ArrayList<>();
        Vehicle element = new Vehicle(str);
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

    }

    public void update (String id, String str) {
        Vehicle element = new Vehicle (str.strip());
        element.setCreationDate(new Date().toString());
        element.setId(id);
        int i = Integer.parseInt(id);
        list.set(i-1, element);
    }

    public void remove (String id) {
        list.remove(Integer.parseInt(id)-1);
        list_update();
    }

    public void removeByNumberOfWheels (String numberOfWheels) {
        int num = Integer.parseInt(numberOfWheels);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNumberOfWheels() == num) {
                remove(i+1 +"");
            }
        }
    }

    public String averageNumberOfWheels () {
        double sum=0;
        for (Vehicle vehicle : list) {
            sum += vehicle.getNumberOfWheels();
        }

        return String.format("%8.2f",(sum/list.size())).replace(',', '.').strip();
    }

    public void clear () {
        list.clear();
    }

    public void add_if_min (String str) throws NullValueException{
        Vehicle element = new Vehicle(str.strip());

        if (element.countValue() < getMinimal()) {
            add(element);
        }
    }

    public String getInfo () {
        return info.replace("\n", ";");
    }

    public void save () {
        writeFile();
    }

    public String getFuelTypes () {
        String out = Arrays.toString(FuelType.ALCOHOL.getDeclaringClass().getEnumConstants());
        return out.substring(1, out.length()-1).replace(", ", ";");
    }

    public String getVehicleTypes () {
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
