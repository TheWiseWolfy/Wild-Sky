package com.apetrei.engine;
/*!
 * O clasa care incapsuleaza mai multe variabile de interes global, si care in viitor le va gestiona cu o baza de date.
 */
public class ConfigHandler {


    //GAME PROPIETIES
    private static final double UPDATE_CAP= 1/60.0f;
    private static int width = 1280;
    private static int height =720;
    private static float scale = 1f;
    private static String title = "Wild-Sky";
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

    //DATE SALVATE
    public static int CurrentLevel = 1;
    public static int getCurrentLevel() {
        return CurrentLevel;
    }
    public static void setCurrentLevel(int currentLevel) {
        CurrentLevel = currentLevel;
    }

    public static float Volume = 0.5f;
    public static float getVolume() {
        return Volume;
    }
    public static void setVolume(float volume) {
        volume = Math.min(volume, 1);
        volume = Math.max(volume, 0);
        Volume = volume ;
    }

    //PLAYER INFORMATION
    private static int maxPlayerHealt = 5000;
    public static int getMaxPlayerHealt() {
        return maxPlayerHealt;
    }
    public static void setMaxPlayerHealt(int maxPlayerHealt) {
        ConfigHandler.maxPlayerHealt = maxPlayerHealt;
    }

    private static float maneuverability = 5f;
    public static float getManeuverability() {
        return maneuverability;
    }
    public static void setManeuverability(float maneuverability) {
        ConfigHandler.maneuverability = maneuverability;
    }

    private static float enginePower = 50;
    public static float getEnginePower() {
        return enginePower;
    }

    private static float withouthSailsDrag = 0.3f;
    private static float sailsDrag = 1.5f;
    public static float getWithouthSailsDrag() {
        return withouthSailsDrag;
    }
    public static float getSailsDrag() {
        return sailsDrag;
    }
}
