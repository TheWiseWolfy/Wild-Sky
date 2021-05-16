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

    public void drawTextInABox(String string, Vector2 pozA,Vector2 pozB , String fontName,int size , Color color ){
        graphics.setColor(color);

        Font font = new Font(fontName, Font.PLAIN, size);
        graphics.setFont(font);
        FontMetrics fontMetrics = graphics.getFontMetrics();

        //Vector math
        float maxRowLenght = pozB.x - pozA.x;

        Vector2 textPoz = new Vector2(pozA);
        textPoz.add( new Vector2(  0, fontMetrics.getHeight() ));

        //Algoritm complicat de scris in cutie

        String[] words =string.split(" ");
        StringBuilder currentRow = new StringBuilder();
        StringBuilder potentialCurrentRow = new StringBuilder();

        //Pentru fiecare cuvant din text.
        for( var word : words){
            //Adaugam cate un cuvand pe rand in stringBuilder
            potentialCurrentRow.append(word);
            potentialCurrentRow.append(" ");

            //Daca randul a devenim prea lung atunci afisam ce sa concatenat pana la cuvantul asta
            if( fontMetrics.stringWidth(  potentialCurrentRow.toString()  ) + 50> maxRowLenght ){
                //We draw the current row
                graphics.drawString(currentRow.toString(), (int) textPoz.x, (int) textPoz.y);
                //Desenam cu un rand mai jos acum.
                textPoz.add( new Vector2(  0, fontMetrics.getHeight() ));

                //Resetam stringBuilder-urile si adaugam cuvantul care a trecut peste limita la inceputul urmatorului rand
                currentRow.setLength(0);
                potentialCurrentRow.setLength(0);
                currentRow.append(word);
                currentRow.append(" ");
            }else {
                //Daca nu am trecut inca peste limita, adaugam cuvantul verificat la rand
                currentRow.append(word);
                currentRow.append(" ");
            }
        }
        graphics.drawString(currentRow.toString(), (int) textPoz.x, (int) textPoz.y);
    }




    //________________________SETTER_____________________________________

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

}
