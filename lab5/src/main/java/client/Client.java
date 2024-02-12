package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public final class Client {
    public static void main(String[] args) {

        try (
                Socket socket = new Socket("192.168.1.180", 8000);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                )
        {

            System.out.println("Connected to server");

            Scanner scn = new Scanner(System.in);
            while (true) {
                String request = scn.nextLine();
                out.write(request);
                out.newLine();
                out.flush();


                String response = "";
                try { response = in.readLine();} catch (NullPointerException e) {response = "";}
                if (response.contains(";")) {
                    response = response.replace(";", "\n").strip();

                }
                System.out.println(response);

                if (request.strip().equalsIgnoreCase("exit")) {
                    scn.close();
                    System.out.println("Exiting programm");
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
