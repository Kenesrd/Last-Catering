package src.main.java;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public class Compress {
    public static void main(String[] args) throws IOException {

        compress("/Users/home/Desktop/DSC_0332_result.jpg",
                "/Users/home/Desktop/Resizet.jpg",
                6016, 4000);
    }

    public static void compress(String imagePathToRead,
                                String imagePathToWrite, int resizeWidth, int resizeHeight) throws IOException {
        File fileToRead = new File(imagePathToRead);
        BufferedImage bufferedImageInput = ImageIO.read(fileToRead);

        BufferedImage bufferedImageOutput = new BufferedImage(resizeWidth,
                resizeHeight, bufferedImageInput.getType());

        Graphics2D g2d = bufferedImageOutput.createGraphics();
        g2d.drawImage(bufferedImageInput, 0, 0, resizeWidth, resizeHeight, null);
        g2d.dispose();

        String formatName = imagePathToWrite.substring(imagePathToWrite
                .lastIndexOf(".") + 1); //Получаем формат jpg
        System.out.println(formatName);

        ImageIO.write(bufferedImageOutput, formatName, new File(imagePathToWrite));
    }


}
