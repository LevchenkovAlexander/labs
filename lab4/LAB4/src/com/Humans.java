package com;

public class Humans extends Entity implements Alive {

    private String[] hold_items = new String[2];
    private Feel feel;

    public class Head {

        boolean isSmart;
        public Head () {};
        public Head (boolean isSmart) {this.isSmart = isSmart;}

        public String think () {
            if (isSmart){
                return getName() + " uses their head to think";
            } else return getName() + " cannot think";
            
        }

        public void setSmart (boolean isSmart) {
            this.isSmart = isSmart;
        }

        public String wearHat () {
            return getName() + " wears hat on their head";
        }
    }

    public static class Anatomy {
        public static int numberOfLegs;
        public static int numberOfArms;
        public int height;

        public Anatomy() { }
    
        public Anatomy(int numberOfLegs, int numberOfArms, int height) {
            Anatomy.numberOfLegs = numberOfLegs;
            Anatomy.numberOfArms = numberOfArms;
            this.height = height;
        }
    
        public static int getNumberOfLegs() {
            return numberOfLegs;
        }
    
        public static void setNumberOfLegs(int numberOfLegs) {
            Anatomy.numberOfLegs = numberOfLegs;
        }
    
        public static int getNumberOfArms() {
            return numberOfArms;
        }
    
        public static void setNumberOfArms(int numberOfArms) {
            Anatomy.numberOfArms = numberOfArms;
        }
    
        public int getHeight() {
            return height;
        }
    
        public void setHeight(int height) {
            this.height = height;
        }
    }

    public enum Feel {

        OKAY ("okay"),
        COMFY ("more comfortable");

        private final String feel;
        Feel (final String feel) {
            this.feel = feel;
        }

        public String getString () {
            return feel;
        }
    }

    public Humans (String name) {
        super(name);
    }

    public Humans (String name, String state) {
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
        return super.getName() + " said to " + toWho.getName() + ": \"" + what + "\"";
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
    public String take (Things what, Places fromWhere) {
        return super.getName() + " took " + what.getName() + " from the " + fromWhere.getName(); 
    }

    @Override
    public String put (Things what, Places where) {
        return super.getName() + " put " + what.getName() + " " + where.getName();
    }

    @Override
    public String put (Things what, Places where, String prep) {
        return super.getName() + " put " + what.getName() + " " + prep + " " + where.getName();
    }

    @Override
    public String feels () {
        return super.getName() + " is feeling " + feel.getString();
    } 

    @Override
    public String lookAround () {
        return super.getName() + " looked around";
    }

    @Override
    public String eat (Edible what) {
        return super.getName() + " ate " + what;
    }

    @Override
    public void appear () {
        System.out.println(super.getName() + " appeared ");
    }

    
    @Override
    public void appear (Places where) {
        System.out.println(super.getName() + " appeared " + where);
    }

    @Override
    public void think(String what) {
        System.out.println(super.getName() + " thought " + what);
    }

    @Override
    public void doSmth (String what) {
        System.out.println(getName() + what);
    }
    
}
