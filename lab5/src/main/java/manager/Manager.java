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
    private Server s1;
    public Manager () {
        s1 = new Server();
    }
//    public static void main (String[] args){
//
//        try (ServerSocket server = new ServerSocket(8000);)
//        {
//            Server s1 = new Server();
//            System.out.println("Server started");
//
//
//
//            while (!server.isClosed()) {
//
//                try (Socket socket = server.accept();
//                     BufferedWriter out = new BufferedWriter(
//                             new OutputStreamWriter(socket.getOutputStream()));
//                     BufferedReader in = new BufferedReader(
//                             new InputStreamReader(socket.getInputStream()));
//                )
//                {
//                    System.out.println("New Connection");
//                    while (!socket.isClosed()) {
//                        String request = in.readLine();
//                        String response = "";
//
//                        if (request.strip().equalsIgnoreCase("exit")) {
//                            System.out.println("Client finished dialog");
//                            response = "server closing";
//
//                            out.write(response);
//                            out.newLine();
//                            out.flush();
//
//                            server.close();
//                            break;
//                        }
//
//                        try {
//                            response = s1.connect(request);
//                        } catch (NumberFormatException e) {
//                            response = e.getMessage();
//                        }
//
//                        out.write(response);
//                        out.newLine();
//                        out.flush();
//                    }
//                } catch (IOException e) {
//                    System.out.println("Connection Error: User Disconnected");
//                }
//
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
    public String connect (String request) {
        String response="";
        response = s1.connect(request);

        return response;
    }
}
