package com;

public abstract class Entity {

    private String name;
    private String state;

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

    public void setName (String newName) throws NewNameNotSupportedException {
        if (getClass().getSuperclass().getName().equals("com.Things")) {
            throw new NewNameNotSupportedException("Cannot give new name");
        } else name = newName;
    }

    public String getState () {
        return state;
    }

    public void setState (String newState) {
        state = newState;
    }

    public String _toString () {
        return getClass().getName() + " " + getName();
    }

    public int _hashCode () {
        return hashCode();
    }

    public boolean _equals (Entity ent) {
        return getClass().getName().equals(ent.getClass().getName()) && state.equals(ent.state);
    }

        
}