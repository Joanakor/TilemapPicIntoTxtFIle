import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TileProcessor {

    Color[] colorTable;
    int colorTableSize = 5;

    public TileProcessor()
    {
        colorTable = new Color[5];
        initColors();
    }

    private void initColors()
    {
        colorTable[0] = Color.white;
        System.out.println(colorTable[0].getRed() + " " + colorTable[0].getGreen() + " " + colorTable[0].getBlue());
        colorTable[1] = Color.black;
        System.out.println(colorTable[1].getRed() + " " + colorTable[1].getGreen() + " " + colorTable[1].getBlue());
        colorTable[2] = Color.cyan;
        System.out.println(colorTable[2].getRed() + " " + colorTable[2].getGreen() + " " + colorTable[2].getBlue());
        colorTable[3] = Color.green;
        System.out.println(colorTable[3].getRed() + " " + colorTable[3].getGreen() + " " + colorTable[3].getBlue());
        colorTable[4] = Color.orange;
        System.out.println(colorTable[4].getRed() + " " + colorTable[4].getGreen() + " " + colorTable[4].getBlue());
    }

    private BufferedImage fileIntoImage(File imgfile)
    {
        try {
            return ImageIO.read(imgfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(File imgfile, String outputFolderLocation)
    {
        BufferedImage img = fileIntoImage(imgfile);
        int height = img.getHeight(null);
        int width = img.getWidth(null);

        int[][] numMap = new int [height][width];

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int color = img.getRGB(j, i);
                for (int color_indx = 0; color_indx < colorTableSize; color_indx++)
                {
                    if (colorTable[color_indx].getRGB() == color)
                    {
                        numMap[i][j] = color_indx;
                    }
                }
            }
        }

        File txtfile;

        try {
            int exists = 1;
            while (true) {
                txtfile = new File(outputFolderLocation + "\\" + "tilemap" + exists + ".txt");
                if (txtfile.createNewFile()) {
                    break;
                } else {
                    exists++;
                }
            }
            FileWriter myWriter = new FileWriter(txtfile);

            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    myWriter.append(String.valueOf(numMap[i][j])).append(" ");
                }
                myWriter.append("\n");
            }

            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
