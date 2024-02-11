package com;

public abstract class Things extends Objects{
    
    private Size size;
    
    public enum Size {
        NULL (""),
        NORMAL ("average size"),
        HUGE ("really big"),
        TINY ("tiny");

        final private String size;
        Size (final String size) {
            this.size = size;
        }

        public String getString () {
            return size;
        }
    }

    public Things (String name) {
        super(name);
        this.size = Size.NULL;
    }

    public Things (String name, Size size) {
        super(name);
        this.size = size;
    }

    public void setSize (Size size) throws SizeChangeException {

        if (this.size == Size.NULL || this.size == size) {
            this.size = size;
        } else {throw new SizeChangeException();}

    }

    public Size getSize () {
        return size;
    }

    public void doSmth (String what) {
        System.out.println(getName() + what);
    }

    public void doSmth (String how, String what) {
        System.out.println(getName() + how + what);
    }


   
}
