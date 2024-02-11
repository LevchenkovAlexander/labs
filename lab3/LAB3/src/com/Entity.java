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

    public boolean _equals (Entity object) {
        return (getClass().getName() == object.getClass().getName() && getName() == object.getName());
    }

    public String _toString () {
        return getClass().getName() + " " + getName();
    }

    public int _hashCode () {
        return hashCode();
    }

}
