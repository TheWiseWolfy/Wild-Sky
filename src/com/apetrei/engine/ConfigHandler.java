package com.apetrei.engine;
/*!
 * O clasa care incapsuleaza mai multe variabile de interes global, si care in viitor le va gestiona cu o baza de date.
 */
public class ConfigHandler {

    //Propietatile jocului
    private static final double UPDATE_CAP= 1/60.0f;
    private static int width = 1280;
    private static int height =720;

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
    private static int maxPlayerHealt = 5000;

    public static int getMaxPlayerHealt() {
        return maxPlayerHealt;
    }

    public static void setMaxPlayerHealt(int maxPlayerHealt) {
        ConfigHandler.maxPlayerHealt = maxPlayerHealt;
    }

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
