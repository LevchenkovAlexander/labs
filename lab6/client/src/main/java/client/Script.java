package client;

import common.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Script {
    private static final Stack<String> scripts = new Stack<>();

    public static String execute(String fileName, Client client) {
        if (scripts.contains(fileName)) {
            return "Script is cycled";
        }
        scripts.push(fileName);
        try {
            File file = new File(fileName);
            Scanner scn = new Scanner(file);
            while (scn.hasNextLine()){
                Request request = new Request(scn.nextLine().strip());
                if (request.getCommand().equals("exit")) {
                    client.stopped = true;
                    System.out.println(client.connect(request));
                    break;
                } System.out.println(client.connect(request));
            }

        } catch (IllegalArgumentException | FileNotFoundException e) {
          System.out.println(e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        scripts.pop();
        return "Script " + fileName + " is finished";

    }

}
