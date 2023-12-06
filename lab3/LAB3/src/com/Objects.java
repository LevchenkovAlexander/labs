package com;

public abstract class Objects extends Entity implements NotAlive {
    
    


    public Objects (String name) {
        super(name);
    }

    @Override
    public void exist () { }

    @Override
    public String thinkAboutExistence () {
        exist();
        return "why am i not alive";
    }
        
    @Override 
    public String passive_action (String action) {
        return super.getName() + " is " + action;
    }

    @Override 
    public String stand (String where) {
        return super.getName() + " stands " + where;
    }
    
    @Override
    public String stand (Places where) {
        return super.getName() + " stands " + where.getName();
    }

    @Override 
    public String hide (Places where) {
        return super.getName() + " hides " + where.getName();
    }

    @Override
    public String hide (String where) {
        return super.getName() + " hides " + where;
    }

}
