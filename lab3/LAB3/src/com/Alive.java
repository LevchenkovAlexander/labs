package com;

public interface Alive {

    public String take (Objects what);
    
    public String take (Things what, Places fromWhere);

    public String put (Things what, Places where);

    public String put (Things what, Places where, String prep);

    public String feels ();

    public String lookAround ();


}
