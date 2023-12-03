package com;

public class HE extends Humans{

    public HE (String name) {
        super(name);
    }
    
    @Override
    public String lookAround () {
        return super.getName() + " carefully look around";
    }

}
