package server;

import server.com.*;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    Date creationDate;
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
        creationDate = new Date();
        String info = "Type: " + "ArrayList" + "\n";
        info += "Initialization date: " + creationDate.toString() + "\n";
        info += "Number of elements: " + list.size() + "\n";
        info += "Headers: Id, Name, Coordinates, CreationDate, EnginePower, NumberOfWheels, Type, FuelType" + "\n";
        return info;
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

    public void add (Vehicle element) {
        element.setId("" + (list.size()+1));
        element.setCreationDate((new Date()).toString());
        list.add(element);
    }

    public void add (String str) {
        add(new Vehicle(str));
    }

    public void insert_at_index (int index, String element) {
        ArrayList<Vehicle> tmp_list = new ArrayList<>();
        for (int i = 0; i < list.size() + 1; i ++){
            if (i == index) {
                tmp_list.add(new Vehicle(element));
            }
            else {
                tmp_list.add(list.get(i));
            }
            tmp_list.get(i).setId(i+"");
        }
    }

    public void update (String id, String element) {
        int i = Integer.parseInt(id);
        list.set(i, new Vehicle(element));
        list.get(i).setId(i+"");
    }

    public void clear () {
        list.clear();
    }

    public String getInfo () {
        return info.replace("\n", ";");
    }

    public void save () {
        writeFile();
    }
}
