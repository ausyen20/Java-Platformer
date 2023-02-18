package com.mygdx.helpers;
public class Constants {

    public static final float PPM = 32.0f;
    
    //Filters
    public static final short BIT_PLAYER = 1;
    public static final short BIT_FLOOR = 2;
    public static final short BIT_OBS = 4;
    
    // World parameters
    public static final float WORLD_WIDTH = 320.0f;
    public static final float WORLD_HEIGHT = 180.0f;

    // Window size
    public static final float WINDOW_WIDTH = 1600.0f;
    public static final float WINDOW_HEIGHT = 900.0f;

    // Assets
    public static final float ASSET_BACKGROUND_WIDTH = 660.0f;
    public static final float ASSET_LAYOUT_WIDTH = 5120.0f;
    public static final float ASSET_LAYOUT_HEIGHT = 192.0f;

    // Player
    public static final int PLAYER_SPRITE_WIDTH = 20;
    public static final int PLAYER_SPRITE_HEIGHT = 20;
    public static final int PLAYER_HITBOX_WIDTH = 14;
    public static final int PLAYER_HITBOX_HEIGHT = 20;

    // Assist mode
    private boolean assist = false;

    /*
     * If assist mode is chosen, will get set to true
     */
    public void setAssist(boolean state) {
        this.assist = state;
    }

    /*
     * Check if true or false for adjusting user input
     */
    public boolean getAssist() {
        return this.assist;
    }


}