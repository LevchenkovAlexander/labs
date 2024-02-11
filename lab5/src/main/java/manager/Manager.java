package manager;

import server.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
                        System.out.println("request handled");


                        if (request.strip().equalsIgnoreCase("help")) {
                            response = "help: вывести справку";
                        }

                        if (request.strip().equalsIgnoreCase("show")){
                            response = s1.getCollection();
                        }

                        if (request.strip().equalsIgnoreCase("info")) {
                            response = s1.getInfo();
                        }

                        if (request.strip().equalsIgnoreCase("save")) {
                            s1.save();
                            response = "Collection saved";
                        }

                        if (request.split(" ")[0].strip().equalsIgnoreCase("update")) {
                            if (request.split(" ")[1].strip().equalsIgnoreCase("help")) {
                                response = "id + {name, coordX, coordY, enginePower, numOfWheels, type, fuelType}";
                            }
                            else {
                                s1.update(request.split(" ")[1].strip(), request.split(" ")[2].strip());
                                response = "Updated, i guess...";
                            }
                        }

                        if (request.split(" ")[0].strip().equalsIgnoreCase("add")) {
                            if (request.split(" ")[1].strip().equalsIgnoreCase("help")) {
                                response = "{name, coordX, coordY, enginePower, numOfWheels, type, fuelType}";
                            }
                            else {
                                s1.add(request.split(" ")[1].strip());
                                response = "Element added";
                            }


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
