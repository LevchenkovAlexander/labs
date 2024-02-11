package com;

public class SizeChangeException extends RuntimeException{
    public SizeChangeException () { }
    public SizeChangeException (String str) { super(str);}

    public String getMessage() {
        return "Cannot resize the object";
    }
}
