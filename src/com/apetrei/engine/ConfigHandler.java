package com.apetrei.engine;

public class ConfigHandler {

    //Propietatile jocului
    private static final double UPDATE_CAP= 1/60.0f;
    private static int width = 640;
    private static int height =480;

    private static float scale = 2f;
    private static String title = "Engine v1.0";


    public static double getUpdateCap() {
        return UPDATE_CAP;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static float getScale() {
        return scale;
    }

    public static String getTitle() {
        return title;
    }


}
