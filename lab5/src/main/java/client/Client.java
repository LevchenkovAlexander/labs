package client;

import common.StrToV;
import common.Validator;
import manager.Manager;
import common.Request;

import java.util.Arrays;
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
//                    System.out.println("Exiting program");
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
            String input = in.nextLine().strip().toLowerCase();
            String response = "";
            Request request = new Request(input);
            try {
                if (Validator.isValid(request)) {
                    response = m.connect(request);
                }
            } catch (IllegalArgumentException e) {
                response = e.getMessage();
            }

            if (response.contains("/n")) {
                response = response.replace("/n", "\n");
            }

            if (response.contains(";")) {
                StringBuilder vehicle = new StringBuilder();

                String[] fields = response.split(";");
                for (int i = 0; i < fields.length; i ++) {
                    String field = fields[i];

                    System.out.println("Input " + field);
                    String param = in.nextLine().strip();
                    try {
                        if (param.isEmpty()) {
                            throw new IllegalArgumentException("This parameter cannot be null");
                        }
                        if (Validator.isValid(field.split("\n")[0], param)) {
                            if (field.equals("index")) {
                                request.setArg(Integer.parseInt(param));
                            } else vehicle.append(param).append(";");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        i --;
                    }
                }
                request.setVehicle(StrToV.exec(vehicle.toString()));
                response = m.connect(request);
            }


            System.out.println(response.strip());
            if (input.equals("exit")) {
                break;
            }
        }

    }
}
