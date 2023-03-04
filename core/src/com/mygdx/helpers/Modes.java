package com.mygdx.helpers;


public class Modes {

    private boolean assist;
    private static Modes INSTANCE = null;

    private Modes() {
        INSTANCE = this;
    }

    public static Object getInstance() {
        if(INSTANCE == null)
        INSTANCE = new Modes();
    
        return INSTANCE;
    }

    public void setAssist(Boolean new_assist) {
        assist = new_assist;
    }

    public boolean getAssist() {
        return assist;
    }
}