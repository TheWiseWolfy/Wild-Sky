package com.apetrei.engine.gui;


/*!
 * O clasa care e folosita pentru toate liniile de dialog din joc.
 */
public class DialogLine {
    public String dialogLine = "";
    public String audioFile = "";
    public float duration = 2f;
    public int character = 0;

    public DialogLine(String dialogLine,String audioFile, float duration, int character) {
        this.dialogLine = dialogLine;
        this.audioFile = audioFile;
        this.duration = duration;
        this.character = character;
    }


}
