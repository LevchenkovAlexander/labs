package server;

import common.StrToV;
import common.Vehicle;

import java.io.*;
import java.util.*;

/**
 * Server-Class for getting access to the Collection file and working with it
 * Should be accessed via Manager
 * @author LevchenkovAlexander
 */

public final class Server {
    private final File collection;
    private final ArrayList<Vehicle> list = new ArrayList<>();
    private String info = "";
    /**
     * Map contains name - command for interacting with user
     */


    public Server() {
        collection = new File(System.getenv("CollectionPath"));
        try {
            readFile();
        } catch (FileNotFoundException | NullPointerException e) {System.out.println("Error: " + e.getMessage());}
    }

    private void readFile () throws FileNotFoundException, NullPointerException {
        Scanner scn = new Scanner(collection);
        StringBuilder inf = new StringBuilder();
        boolean coll = false;

        if (scn.hasNextLine()) {
            while (scn.hasNextLine()) {
                String input = scn.nextLine();
                if (input.strip().equalsIgnoreCase("information:")) {
                    coll = false;
                    input = scn.nextLine();
                }
                if (input.strip().equalsIgnoreCase("collection:")) {
                    coll = true;
                    input = scn.nextLine();
                }
                if (!coll) {
                    inf.append(input).append("\n");
                } else {
                    Vehicle current = StrToV.exec(input);
                    list.add(current);
                    if (Vehicle.last_id < current.getId()) {
                        Vehicle.last_id = current.getId();
                    }
                }
            }
        } else{
            createInfo();
        }
        info = inf.toString();
    }
    public void writeFile () {
        try (BufferedWriter writer = new BufferedWriter (new FileWriter (collection))) {
            writer.write(info);
            writer.write(listToString());
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String getInfo () {
        return info;
    }

    public void add (Vehicle element) {
        list.add(element);
        updateInfo();
    }

    public void add (int id, Vehicle element) {
        list.add(id, element);
        updateInfo();
    }

    public boolean addIfMin (Vehicle vehicle) {
        long min = list.get(0).countValue();
        for (int i = 1; i < list.size(); i++) {
            long tmp = list.get(i).countValue();
            if (min > tmp) {
                min = tmp;
            }
        }
        if (vehicle.countValue() < min) {
            add(vehicle);
            return true;
        }
        return false;
    }

    public void update (int id, Vehicle element) {
        list.set(id-1, element);
        updateInfo();
    }

    public Vehicle remove (int id) throws IllegalArgumentException {
        Vehicle tmp = null;
        boolean removed = false;

        if (list.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }

        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).getId() == id) {
                tmp = list.remove(i);
                removed =  true;
            }
        }
        if (removed) {
            updateInfo();
            return tmp;
        } else {
            throw new IllegalArgumentException("No element with this id");
        }
    }

    public void remove_first () throws IllegalArgumentException {
        if (!list.isEmpty()) {
            list.remove(0);
        } else {
            throw new IllegalArgumentException("List is empty");
        }
    }

    public boolean removeByNumberOfWheels (int num) {
        boolean removed = false;
        for (Vehicle vehicle : list) {
            if (vehicle.getNumberOfWheels() == num) {
                remove(vehicle.getId());
                removed = true;
            }
        }
        return removed;
    }

    public void clear () {
        list.clear();
        updateInfo();
    }

    public double averageNumberOfWheels () {
        double sum = 0;
        for (Vehicle vehicle : list) {
            sum += vehicle.getNumberOfWheels();
        }

        return sum / list.size();
    }

    public String field_descending_fuel_type () {
        StringBuilder str = new StringBuilder();
        ArrayList<Vehicle> vehicles = new ArrayList<>(list);

        Collections.sort(vehicles);
        for (Vehicle veh : vehicles) {
            str.append(veh.getFuelType().toString()).append("/n");
        }
        return str.toString();
    }

    /**
     * Method creating information for new file
     */
    private void createInfo () {
        info = "Type: " + "ArrayList" + "\n";
        info += "Initialization date: " + new Date() + "\n";
        info += "Number of elements: " + list.size() + "\n";
    }

    /**
     * Method for updating information whenever list size changes
     */
    private void updateInfo () {
        String[] inf = info.split("\n");
        info = inf[0] + "\n" + inf[1] + "\n";
        info += "Number of elements: " + list.size() + "\n";
    }

    /**
     * Method return String representation of Collection
     * @return String produced from ArrayList
     */
    public String listToString () {
        StringBuilder listString = new StringBuilder();
        if (!list.isEmpty()){
            for (Vehicle vehicle : list) {
                listString.append(vehicle.toString()).append("\n");
            }
        }
        return listString.toString();
    }



}
