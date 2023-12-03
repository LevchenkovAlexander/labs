package com;

public class Humans extends Entity implements Alive {

    private String[] hold_items = new String[2];
    private Feel feel;
    
    public enum Feel {

        OKAY ("okay"),
        COMFY ("more comfortable"),
        ALIVE (null);


        Feel (final String feel) { }
    }

    Humans (String name) {
        super(name);
    }

    Humans (String name, String state) {
        super(name, state);
    }

    public void setFeel (Feel feel) {
        this.feel = feel;
    }

    public Feel getFeel () {
        return feel;
    }
    
    public String say (String what) {
        return super.getName() + " said: \"" + what + "\"";
    }

    public String say (String what, Humans toWho) {
        return super.getName() + " said " + what + " to " + toWho.getName();
    }

    @Override
    public String take (Objects what) {
        if (hold_items.length == 0) {
            hold_items[0] = what.getName();
        } else {
            hold_items[1] = what.getName();
        }
        return super.getName() + " took " + what.getName();
    }

    public String get_items_holding () {
        if (hold_items.length == 0) {
            return "Not holding any items";
        } else if (hold_items.length == 1) {
            return hold_items[0];
        } 
        return hold_items[0] + " and " + hold_items[1];
    }

    @Override
    public String take (Objects what, Objects fromWhere) {
        return super.getName() + " took " + what.getName() + " from the " + fromWhere.getName(); 
    }

    @Override
    public String put (Objects what, Objects where) {
        return super.getName() + " put " + what.getName() + " " + where.getName();
    }

    @Override
    public String put (Objects what, Objects where, String prep) {
        return super.getName() + " put " + what.getName() + " " + prep + " " + where.getName();
    }

    @Override
    public String feels () {
        return super.getName() + " is feeling " + feel;
    } 

    @Override
    public String lookAround () {
        return super.getName() + " looked around";
    }

    
}
