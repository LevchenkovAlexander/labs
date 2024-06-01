package client;

import

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class Client {
    boolean stopped = false;
    private static final String HOST = "localhost";
    private static final int PORT = 32327;
    private final SocketChannel socketChannel;
    private final Selector selector;



    private Client() throws IOException {
//        TODO: comments -> LOGGING
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);


    }

    private void write(byte[] input) throws IOException{
        if (socketChannel.isConnected()){
//            TODO: comments -> LOGGING
//            System.out.println("Writing...");
            ByteBuffer buffer = ByteBuffer.wrap(input);
            socketChannel.write(buffer);
            buffer.flip();
//            TODO: comments -> LOGGING
//            System.out.println("Done");
        } else {
            System.out.println("Channel lost connection");
        }
    }

    private String read(SelectionKey key) throws IOException {
        // TODO: comments -> LOGGING
//        System.out.println("Reading...");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.configureBlocking(false);
        StringBuilder str = new StringBuilder();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String msg;
        while (socketChannel.isConnected() && socketChannel.read(buffer) > 0) {
            buffer.flip();
            msg = new String(buffer.array()).trim();
            if (msg.isEmpty()) {
                continue;
            }
            str.append(msg);
            buffer.clear();
        }
        return str.toString();


    }

    public Integer getLastId() {
        try {
            return Integer.parseInt(connect(new Request("last_id")));
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    protected String connect(Request request) throws IOException {
        write(request.export());

        String response = "";
        selector.select();
        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
        while (iter.hasNext()) {
            SelectionKey key = iter.next();
            if (key.isReadable()) {
                response = read(key);
            }
            iter.remove();
        }
        return response;
    }

    private void close() throws IOException{
        socketChannel.close();
        selector.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            Scanner scn = new Scanner(System.in);
            while(!client.stopped && scn.hasNextLine()) {
                String input = scn.nextLine().strip().toLowerCase();

                // TODO: add command validator
                Request request = new Request(input);

                String validator = Validator.isValid(request);
                if (validator.equals("valid")){
                    if (request.getCommand().equals("execute")) {
                        Script.execute(request.getFileName(), client);
                    } else if (request.getCommand().equals("exit")) {
                        client.connect(new Request("exit"));
                        client.stopped = true;
                    } else System.out.println(client.connect(request));


                } else if (validator.contains(";")) {
                    String[] fields = validator.split(";");
                    StringBuilder vehicle = new StringBuilder();
                    for (int i = 0; i < fields.length; i++) {
                        System.out.println("Input: " + fields[i]);
                        String in = scn.nextLine();
                        String field_validator = Validator.isFieldValid(fields[i].split("\n")[0], in);


                        if (field_validator.equals("valid")) {
                            vehicle.append(in).append(", ");
                        } else if (field_validator.equals("stopped")) {
                            System.out.println("Stopping input...");
                            break;
                        } else {
                            i--;
                            System.out.println(field_validator);
                        }
                    }

                    request.setVehicle(StrToV.exec((client.getLastId() + ", " + vehicle), (new Date() + "")));
                    System.out.println(client.getLastId() + ", " + vehicle);
                    System.out.println(client.connect(request));


                } else System.out.println(validator);
            }
            client.close();
            scn.close();


        } catch (IOException e) {
            if (e.getMessage().equals("Connection refused: connect") || e.getMessage().equals("Connection reset by peer")) {
                System.out.println("SERVER IS OUT OF ORDER.\nPlease try again later");
            }
            e.printStackTrace();
        }
    }
}
