package client;

import common.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;




public class Client {
    boolean stopped = false;
    private static final String HOST = "localhost";
    private static final int PORT = 32327;
    private final SocketChannel socketChannel;
    private final Selector selector;



    private Client() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);


    }

    private void write(Request request) throws IOException{
        if (socketChannel.isConnected()){
//            System.out.println("Writing...");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(request);
            ByteBuffer buffer = ByteBuffer.wrap(bos.toByteArray());
            socketChannel.write(buffer);
            buffer.flip();
//            System.out.println("Done");
        } else {
            System.out.println("Channel lost connection");
        }
    }

    private String read(SelectionKey key) throws IOException, ClassNotFoundException {
//        System.out.println("Reading...");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        MyBuffer myBuffer = new MyBuffer();
        while ((socketChannel.read(buffer) > 0)) {
            buffer.flip();
            myBuffer.addBytes(buffer.array());
            buffer.clear();
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(myBuffer.toByteBuffer().array());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Response response = (Response) ois.readObject();
        return response.getResponse();


    }

    public Integer getLastId() {
        try {
            return Integer.parseInt(connect(new Request("last_id")));
        } catch (IOException | NumberFormatException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected String connect(Request request) throws IOException, ClassNotFoundException {
        write(request);

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

                Request request = new Request(input);

                String validator = Validator.isValid(request);
                if (validator.equals("valid")){
                    if (request.getCommand().equals("execute")) {
                        System.out.println(Script.execute(request.getFileName(), client));
                    } else if (request.getCommand().equals("exit")) {
                        client.connect(request);
                        client.stopped = true;
                    } else System.out.println(client.connect(request));


                } else if (validator.contains(";")) {


                    request.setVehicle(VehicleInput.exec(client, scn, validator));
                    System.out.println(client.connect(request));


                } else System.out.println(validator);
            }
            client.close();
            scn.close();


        } catch (IOException | ClassNotFoundException e) {
            if (e.getMessage().equals("Connection refused: connect") || e.getMessage().equals("Connection reset by peer")) {
                System.out.println("SERVER IS OUT OF ORDER.\nPlease try again later");
            } else e.printStackTrace();
        }
    }
}
