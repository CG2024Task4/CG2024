package com.cgvsu.texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    public int wight;
    public int height;
    public int[][][] pixelData;

    public void loadImage(String path) {

        BufferedImage img;

        try {
            img = ImageIO.read(new File(path));

            pixelData = new int[img.getWidth()][img.getHeight()][3];
            wight = img.getWidth();
            height = img.getHeight();
            int[] rgb;

            for(int i = 0; i < img.getWidth(); i++){  //для лица и куба
                for(int j = 0; j < img.getHeight(); j++){
                    rgb = getPixelData(img, i, j);
                    pixelData[img.getWidth() - 1 - i][img.getHeight() - 1 - j][0] = rgb[0];
                    pixelData[img.getWidth() - 1 - i][img.getHeight() - 1 - j][1] = rgb[1];
                    pixelData[img.getWidth() - 1 - i][img.getHeight() - 1 - j][2] = rgb[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int[] getPixelData(BufferedImage img, int x, int y) {
        int argb = img.getRGB(x, y);

        return new int[] {
                (argb >> 16) & 0xff, //red
                (argb >>  8) & 0xff, //green
                (argb      ) & 0xff  //blue
        };
    }
}
