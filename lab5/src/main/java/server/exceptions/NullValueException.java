package server.exceptions;

import java.io.IOException;

public class NullValueException extends IOException {
    String message;
    public NullValueException (String s) {
        message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
