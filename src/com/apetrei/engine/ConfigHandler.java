package com.apetrei.engine;

public class ConfigHandler {

    //Propietatile jocului
    private static final double UPDATE_CAP= 1/60.0f;
    private static int width = 800;
    private static int height =600;

    private static float scale = 1f;
    private static String title = "Engine v1.0";


    private static boolean debugMode = false;

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

    public static boolean isDebugMode() {
        return debugMode;
    }


    //Player information

    private static float enginePower = 50;
    private static float maneuverability = 5f;

    public static float getManeuverability() {
        return maneuverability;
    }

    public static void setManeuverability(float maneuverability) {
        ConfigHandler.maneuverability = maneuverability;
    }

    public static float getEnginePower() {
        return enginePower;
    }

}
