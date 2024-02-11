package server.exceptions;


import java.io.IOException;

public class FileIsEmptyException extends IOException {
    public FileIsEmptyException () {
        super();
    }

    @Override
    public void printStackTrace() {
        System.out.println("Empty File ERROR");
    }
}
