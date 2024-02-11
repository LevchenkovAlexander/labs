package com;

public class Edible extends Things {
    
    public Edible (String name) {
        super(name);
    }
    
    private String tasteValidation () {
        class Taste {
            boolean isTasty;
            Taste (boolean isTasty) {this.isTasty = isTasty;}
            
            boolean getTaste () {
                return isTasty;
            }
        }

        Taste taste = new Taste(true);

        if (taste.getTaste() == true) {
            return "was";
        } else return "wasn't";
    }

    public String beEaten () {
        return super.getName() + " is eaten and it " + tasteValidation() +" tasty";
    }

}
