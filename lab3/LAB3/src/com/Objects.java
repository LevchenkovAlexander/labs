package com;

public class Objects extends Entity implements NotAlive {
    
    public enum Size {
        NORMAL ("average size"),
        HUGE ("really big"),
        TINY ("tiny");

        final private String size;
        Size (final String size) {
            this.size = size;
        }

        public String getSize () {
            return size;
        }
    }

    private Size size;

    public Objects (String name) {
        super(name);
        size = Size.NORMAL;
    }

    public Objects (String name, Size size) {
        super(name);
        this.size = size;
    }

    public void setSize (Size size) {
        this.size = size;
    }

    public Size getSize () {
        return size;
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
    public String stand (Objects where) {
        return super.getName() + " stands " + where.getName();
    }

    @Override 
    public String hide (Objects where) {
        return super.getName() + " hides " + where.getName();
    }

    @Override
    public String hide (String where) {
        return super.getName() + " hides " + where;
    }

}
