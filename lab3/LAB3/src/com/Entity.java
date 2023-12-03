package com;

public abstract class Entity {

    private String name;
    private String state;

    public Entity () { } 

    public Entity (String name) {
        this.name = name;
        this.state = "okay";
    }

    public Entity (String name, String state) {
        this.name = name;
        this.state = state;
    }

    public String getName () {
        return name;
    }

    public void setName (String newName) {
        name = newName;
    }

    public String getState () {
        return state;
    }

    public void setState (String newState) {
        state = newState;
    }

    public boolean equals (Object object) {
        return (super.hashCode() == object.hashCode());
    }

    public String toString () {
        return getClass().getName() + " " + getName();
    }

}
