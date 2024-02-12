package manager;

import server.*;
import server.com.FuelType;
import server.exceptions.NullValueException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public final class Manager {
    public static void main (String[] args){

        try (ServerSocket server = new ServerSocket(8000);)
        {
            Server s1 = new Server();
            System.out.println("Server started");



            while (!server.isClosed()) {

                try (Socket socket = server.accept();
                     BufferedWriter out = new BufferedWriter(
                             new OutputStreamWriter(socket.getOutputStream()));
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()));
                )
                {
                    System.out.println("New Connection");
                    while (!socket.isClosed()) {
                        String request = in.readLine();
                        String response = "";

                        response = (request + " poshol nahui");


                        if (request.strip().equalsIgnoreCase("help")) {
                            response = "help: вывести справку";
                        }

                        if (request.strip().equalsIgnoreCase("info")) {
                            response = s1.getInfo();
                        }

                        if (request.strip().equalsIgnoreCase("show")){
                            response = s1.getCollection();
                        }

                        if (request.strip().equalsIgnoreCase("save")) {
                            s1.save();
                            response = "Collection saved";
                        }

                        if (request.strip().equalsIgnoreCase("clear")) {
                            s1.clear();
                            response = "Collection cleared";
                        }

                        if (request.split(" ")[0].strip().equalsIgnoreCase("add")) {
                            if (request.split(" ")[1].strip().equalsIgnoreCase("help")) {
                                response = "{name, coordX, coordY, enginePower, numOfWheels, type, fuelType}";
                            }
                            else {
                                List<String> l = Arrays.stream(request.split(" ")).toList();

                                try {
                                    s1.add(String.join(" ", l.subList(1, l.size())));
                                    response = "Element added";
                                } catch (NullValueException e) {
                                    response = "Error: Check inputted data: " + e.getMessage();
                                }


                            }


                        }

                        if (request.split(" ")[0].strip().equalsIgnoreCase("add_if_min")){
                            List<String> l = Arrays.stream(request.split(" ")).toList();

                            try {
                                s1.add_if_min(String.join(" ", l.subList(1, l.size())));
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
                                s1.update(l.get(1), String.join(" ", l.subList(2, l.size())));
                                response = "Updated, i guess...";
                            }
                        }

                        if (request.split(" ")[0].strip().equalsIgnoreCase("types")){

                            if (request.strip().equalsIgnoreCase("types")) {
                                response = "FuelTypes:;" + s1.getFuelTypes() + ";VehicleTypes:;" + s1.getVehicleTypes();
                            }
                            else if (request.split(" ")[1].strip().equalsIgnoreCase("fuel")){
                                response = s1.getFuelTypes();
                            }
                            else if (request.split(" ")[1].strip().equalsIgnoreCase("vehicle")){
                                response = s1.getVehicleTypes();
                            }
                        }

                        if (request.split(" ")[0].strip().equalsIgnoreCase("insert")){
                            List<String> l = Arrays.stream(request.split(" ")).toList();
                            s1.insert(l.get(1), String.join(" ", l.subList(2, l.size())));
                        }

                        if (request.split(" ")[0].strip().equalsIgnoreCase("remove")) {
                            s1.remove(request.split(" ")[1].strip());
                            response = "Element removed";
                        }

                        if (request.strip().equalsIgnoreCase("remove_first")) {
                            s1.remove(1+"");
                            response = "Element removed";
                        }

                        if (request.split(" ")[0].strip().equalsIgnoreCase("remove_all_by_number_of_wheels")){
                            s1.removeByNumberOfWheels(request.split(" ")[1].strip());
                            response = "Elements removed";
                        }

                        if (request.strip().equalsIgnoreCase("average_number_of_wheels")) {
                            response = s1.averageNumberOfWheels();
                        }

                        if (request.strip().equalsIgnoreCase("exit")) {
                            System.out.println("Client finished dialog");
                            response = "server closing";

                            out.write(response);
                            out.newLine();
                            out.flush();

                            server.close();
                            break;
                        }

                        out.write(response);
                        out.newLine();
                        out.flush();
                    }
                } catch (IOException e) {
                    System.out.println("Connection Error: User Disconnected");
//                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
