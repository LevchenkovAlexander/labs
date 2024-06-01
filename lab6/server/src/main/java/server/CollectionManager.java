package server;

import common.vehicle.StrToV;
import common.vehicle.Vehicle;

import java.io.*;
import java.util.*;


/**
 * Server-Class for getting access to the Collection file and working with it
 * Should be accessed via Manager
 * @author LevchenkovAlexander
 */

public final class CollectionManager {
    private final File collection;
    private final ArrayList<Vehicle> list = new ArrayList<>();

    private String info = "";

    private static final ArrayList<Integer> idStack = new ArrayList<>();


    public CollectionManager() {
        collection = new File(System.getenv("CollectionPath"));
//        collection = new File("src/main/resources/Collection.csv");
        try {
            readFile();
        } catch (FileNotFoundException | NullPointerException e) {System.out.println("Error: " + e.getMessage());}
    }

    private void readFile () throws FileNotFoundException, NullPointerException {
        Scanner scn = new Scanner(collection);
        StringBuilder inf = new StringBuilder();
        boolean coll = false;
        boolean ids = false;
        if (scn.hasNextLine()) {
            while (scn.hasNextLine()) {
                String input = scn.nextLine();
                if (input.strip().equalsIgnoreCase("information:")) {
                    coll = false;
                    input = scn.nextLine();
                }
                if (input.strip().equalsIgnoreCase("idStack:")){
                    String stackSTR = scn.nextLine();
                    ids = true;
                    for (String idSTR : stackSTR.strip().split(",")) {
                        idStack.add(Integer.parseInt(idSTR.strip()));
                    }

                }
                if (input.strip().equalsIgnoreCase("collection:")) {
                    coll = true;
                    if (scn.hasNextLine()) {
                        input = scn.nextLine();
                    } else {
                        break;
                    }
                }
                if (!coll && !ids) {
                    inf.append(input).append("\n");
                } else if (coll){
                    Vehicle tmp = StrToV.exec(input);
                    list.add(tmp);
                }
            }
        } else{
            createInfo();
        }
        info = inf.toString();
    }
    public void writeFile () {
        try (BufferedWriter writer = new BufferedWriter (new FileWriter (collection))) {
            writer.write("Information:\n");
            writer.write(info);
            writer.write("IdStack:\n");
            writer.write(idStack_toString() + "\n");
            writer.write("Collection:\n");
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

    public void update (int id, Vehicle element) throws IllegalArgumentException {
        if (idStack.contains(id)) {
            throw new IllegalArgumentException("No element with such id");
        }
        idStack.add(element.getId());
        element.setId(id);
        list.set(id-1, element);
        System.out.println("Element with id " + id + "was updated to " + element);
        updateInfo();
    }

    public void remove (int id) throws IllegalArgumentException {
        boolean removed = false;

        if (list.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        if (idStack.contains(id)) {
            throw new IllegalArgumentException("No element with such id");
        }

        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).getId() == id) {

                list.remove(i);
                removed =  true;
                idStack.add(id);
                System.out.println("Element removed with id: " + id);
                break;
            }
        }
        if (removed) {
            updateInfo();
        } else {
            throw new IllegalArgumentException("No element with this id");
        }
    }

    public void remove_first () throws IllegalArgumentException {
        if (!list.isEmpty()) {

            idStack.add(list.remove(0).getId());

        } else {
            throw new IllegalArgumentException("List is empty");
        }
    }

    public boolean removeByNumberOfWheels (int num) {
        boolean removed = false;

        ArrayList<Integer> idsToRemove = new ArrayList<>();
        for (Vehicle vehicle : list) {
            if (vehicle.getNumberOfWheels() == num) {
                idsToRemove.add(vehicle.getId());
                removed = true;
            }
        }
        for (Integer id : idsToRemove) {
            remove(id);
        }

        return removed;
    }

    public void clear () {
        list.clear();
        idStack.clear();
        idStack.add(1);
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
        List<Vehicle> vehicles = list.stream().sorted((v1, v2) -> -v1.getFuelType().compareTo(v2.getFuelType())).toList();
        for (Vehicle vehicle : vehicles) {
            str.append(vehicle.toString()).append("\n");
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

    public static int lastId () {
        Collections.sort(idStack);
        int id = idStack.remove(0);

        if (idStack.isEmpty()) {
            idStack.add(id+1);
        }
        return id;
    }

    private String idStack_toString() {
        StringBuilder str = new StringBuilder();
        for (int id : idStack) {
            str.append(id).append(", ");
        }

        return str.toString();
    }
}