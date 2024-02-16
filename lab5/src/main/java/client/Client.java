package client;

import manager.Manager;

import java.io.*;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * Console application that interacts with user and sends request to Manager
 */
public final class Client {
    public static void main(String[] args) {

//        try (
//                Socket socket = new Socket("192.168.1.180", 8000);
//                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                )
//        {
//
//            System.out.println("Connected to server");
//
//            Scanner scn = new Scanner(System.in);
//            while (true) {
//                String request = scn.nextLine();
//                out.write(request);
//                out.newLine();
//                out.flush();
//
//
//                String response = "";
//                try { response = in.readLine();} catch (NullPointerException e) {response = "";}
//                if (response.contains(";")) {
//                    response = response.replace(";", "\n").strip();
//
//                }
//                System.out.println(response);
//
//                if (request.strip().equalsIgnoreCase("exit")) {
//                    scn.close();
//                    System.out.println("Exiting programm");
//                    break;
//                }
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Manager m = new Manager();
        System.out.println("Connected");
        Scanner in = new Scanner(System.in);
        while (true) {
            String request = in.nextLine().strip().toLowerCase();

            String response = m.connect(request).strip();

            if (request.equals("exit")) {
                in.close();
                System.out.println("Exiting program");
                break;
            }

            if (response.contains("/n")) {
                response = response.replace("/n", "\n").strip();
            }
            if (response.contains(";")) {
                String[] fields = response.split(" ")[1].split(";");
                StringBuilder lower_request = new StringBuilder(response.split(" ")[0]).append(" ");
                for (int i = 0; i < fields.length; i ++ ) {
                    System.out.println("Input " + fields[i]);
                    lower_request.append(in.nextLine());
                    if (fields[i].equals("id")) {
                        lower_request.append(" ");
                    }else if (i!=fields.length-1) {
                            lower_request.append(";");
                    }
                }
                response = m.connect(lower_request.toString());
            }
            System.out.println(response);
        }

    }
}
