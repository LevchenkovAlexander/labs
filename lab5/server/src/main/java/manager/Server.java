package manager;

import common.Request;
import common.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Server implements Runnable{

    private final CommandManager commandManager;

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 32327;

    private SocketChannel socketChannel;
    private final Selector selector;

    private static final Charset CHARSET = StandardCharsets.UTF_8;


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

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        StringBuilder str = new StringBuilder();

        String msg;
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while ((socketChannel.read(buffer) > 0)) {
            buffer.flip();
            msg = CHARSET.decode(buffer).toString();
            System.out.println(msg);
            str.append(msg);
            buffer.clear();
        }

        System.out.println("New message: " + str);


        Response response = new Response(commandManager.connect(new Request(str.toString())));

        ByteBuffer response_buffer = ByteBuffer.wrap(response.export());
        socketChannel.write(response_buffer);

        if (str.toString().equals("exit")) {
            throw new IOException("Connection reset");
        }


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
            } catch (IOException e) {
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