package com;

public class Things extends Objects{
    
    private Size size;
    
    public enum Size {
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
        size = Size.NORMAL;
    }

    public Things (String name, Size size) {
        super(name);
        this.size = size;
    }

    public void setSize (Size size) {
        this.size = size;
    }

    public Size getSize () {
        return size;
    }


   
}
