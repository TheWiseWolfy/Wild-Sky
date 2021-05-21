package com.apetrei.engine.providers;
/*!
 * O clasa care incapsuleaza mai multe variabile de interes global, si care in viitor le va gestiona cu o baza de date.
 */
public class ConfigHandler {


    //GAME PROPIETIES
    private static final double UPDATE_CAP= 1/60.0f;

    private static float scale = 1f;
    private static String title = "Wild-Sky";
    public static double getUpdateCap() {
        return UPDATE_CAP;
    }
    public static float getScale() {
        return scale;
    }
    public static String getTitle() {
        return title;
    }



    private static boolean debugMode = false;
    public static boolean isDebugMode() {
        return debugMode;
    }
    public static void setDebugMode(boolean debugMode) {
        ConfigHandler.debugMode = debugMode;
    }

    //DATE SALVATE
    public static int CurrentLevel = 1;
    public static int getCurrentLevel() {
        return CurrentLevel;
    }
    public static void setCurrentLevel(int currentLevel) {
        CurrentLevel = currentLevel;
    }

    public static int Score = 0;
    public static int getScore() {
        return Score;
    }
    public static void setScore(int score) {
      ConfigHandler.Score = score;
    }

    //GAME SETTINGS
    public static float soundVolume = 0.5f;
    public static float getSoundVolume() {
        return soundVolume;
    }
    public static void setSoundVolume(float soundVolume) {
        soundVolume = Math.min(soundVolume, 1);
        soundVolume = Math.max(soundVolume, 0);
        ConfigHandler.soundVolume = soundVolume;
    }

    public static float musicVolume = 0.5f;
    public static float getMusicVolume() {
        return musicVolume;
    }
    public static void setMusicVolume(float volume) {
        volume = Math.min(volume, 1);
        volume = Math.max(volume, 0);
        musicVolume = volume ;
    }

    private static int width = 1280;
    private static int height =720;
    public static int getWidth() {
        return width;
    }
    public static int getHeight() {
        return height;
    }
    public static void setWidthAndHeight(int w, int h){
        width = w;
        height = h;
    }

    //PLAYER INFORMATION
    private static int maxPlayerHealt = 1400;
    public static int getMaxPlayerHealt() {
        return maxPlayerHealt;
    }
    public static void setMaxPlayerHealt(int maxPlayerHealt) {
        ConfigHandler.maxPlayerHealt = maxPlayerHealt;
    }

    private static float maneuverability = 5f;
    private static float maneuverabilityWithouthSails = 2f;
    public static float getManeuverability() {
        return maneuverability;
    }
    public static float getManeuverabilityWithouthSails() {
        return maneuverabilityWithouthSails;
    }


    private static float enginePower = 40;
    private static float enginePowerWithouthSails = 60;
    public static float getEnginePower() {
        return enginePower;
    }
    public static float getEnginePowerWithouthSails() {
        return enginePowerWithouthSails;
    }

    private static float withouthSailsDrag = 0.3f;
    private static float sailsDrag = 1.5f;
    public static float getWithouthSailsDrag() {
        return withouthSailsDrag;
    }
    public static float getSailsDrag() {
        return sailsDrag;
    }

    public static float playerMass = 5;
    public static float getPlayerMass() {
        return playerMass;
    }
    //OTHER INFORMATION

    private static float windChangeInterval =15f;
    public static float getWindChangeInterval() {
        return windChangeInterval;
    }

}
