package client;

import common.Request;
import common.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

import static javax.swing.UIManager.put;

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

                    System.out.println(client.connect(new Request(scn.nextLine().strip())));
                }

            } catch (IllegalArgumentException | FileNotFoundException e) {
              System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scripts.pop();
            return "Script " + fileName + " is finished";

    }

}
