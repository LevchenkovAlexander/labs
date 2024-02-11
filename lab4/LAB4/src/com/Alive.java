package com;

public interface Alive {

    public void doSmth (String what);

    public String take (Objects what);
    
    public String take (Things what, Places fromWhere);

    public String put (Things what, Places where);

    public String put (Things what, Places where, String prep);

    public String feels ();

    public String eat (Edible what);

    public String lookAround ();

    public void appear ();

    public void appear (Places where);

    public void think (String what);

}
