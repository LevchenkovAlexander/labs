package com;

public interface Alive {

    public String take (Objects what);
    
    public String take (Objects what, Objects fromWhere);

    public String put (Objects what, Objects where);

    public String put (Objects what, Objects where, String prep);

    public String feels ();

    public String lookAround ();


}
