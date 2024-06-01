package server;

import common.MyBuffer;
import common.Request;
import common.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server implements Runnable{

    private final CommandManager commandManager;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 32327;
    private SocketChannel socketChannel;
    private final Selector selector;





    private Server() throws IOException {
        commandManager = new CommandManager();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
        serverSocketChannel.configureBlocking(false);
        System.out.println("Server started. PORT: " + SERVER_PORT);
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    private void accept(SelectionKey key) throws IOException {
        socketChannel = ((ServerSocketChannel) key.channel()).accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        Response response = new Response();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        MyBuffer myBuffer = new MyBuffer();

        while ((socketChannel.read(buffer) > 0)) {
            buffer.flip();
            myBuffer.addBytes(buffer.array());
            buffer.clear();
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(myBuffer.toByteBuffer().array());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Request request = (Request) ois.readObject();
        response.setResponse(commandManager.connect(request));

        if (request.getCommand().equals("exit")) {
            throw new IOException("Connection reset");
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(response);
        ByteBuffer response_buffer = ByteBuffer.wrap(bos.toByteArray());
        socketChannel.write(response_buffer);

    }


    @Override
    public void run() {

        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {

                    SelectionKey key = iter.next();
                    iter.remove();

                    if (key.isAcceptable()) {
                        accept(key);
                    }
                    else if (key.isReadable()) {
                        read(key);
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                if (e.getMessage().equals("Connection reset")) {
                    System.out.println("User disconnected");
                    try {
                        socketChannel.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
                else e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        new Thread(new Server()).start();

    }
}