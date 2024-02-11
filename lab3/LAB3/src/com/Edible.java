package com;

public class Edible extends Things {
    
    public Edible (String name) {
        super(name);
    }

    public String beEaten () {
        return super.getName() + " is eaten";
    }

}
