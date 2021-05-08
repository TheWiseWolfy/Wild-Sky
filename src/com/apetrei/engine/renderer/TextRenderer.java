package com.apetrei.engine.renderer;

import com.apetrei.misc.Vector2;

import java.awt.*;

public class TextRenderer {
    Graphics graphics;

    TextRenderer(Graphics graphics){
        this.graphics = graphics;
    }

    public void drawText(String string, Vector2 poz, CustomFonts customFont,int size , Color color ){
        drawText(string,  poz,  customFont.toString() , size ,  color);
    }

    public void drawText(String string, Vector2 poz, String fontName,int size , Color color ){
        graphics.setColor(color);

        Font font = new Font(fontName, Font.PLAIN, size);

        graphics.setFont(font);
        FontMetrics fontMetrics = graphics.getFontMetrics();

        Vector2 finalPoz = new Vector2(poz);

        finalPoz.sub( new Vector2(   fontMetrics.stringWidth(string)/2, 0 ));

        graphics.drawString(string, (int) finalPoz.x, (int) finalPoz.y);
    }

    //________________________SETTER_____________________________________

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

}
