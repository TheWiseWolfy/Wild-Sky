package com.apetrei.misc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class DebugFuctions {

    //Auxiliary debug fuctions
    static int i = 0;

    public static void saveBuffer(BufferedImage frame){
        File outputfile = new File("C:\\Users\\Lucian\\Desktop\\trash\\image"+ Integer.valueOf(i) + ".png");
        i++;

        try {

            ImageIO.write(frame, "png", outputfile);
            outputfile.createNewFile();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
