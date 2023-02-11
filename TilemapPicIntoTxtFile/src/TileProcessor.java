import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        colorTable[2] = Color.red;
        System.out.println(colorTable[2].getRed() + " " + colorTable[2].getGreen() + " " + colorTable[2].getBlue());
        colorTable[3] = Color.green;
        System.out.println(colorTable[3].getRed() + " " + colorTable[3].getGreen() + " " + colorTable[3].getBlue());
        colorTable[4] = Color.blue;
        System.out.println(colorTable[4].getRed() + " " + colorTable[4].getGreen() + " " + colorTable[4].getBlue());
    }

    private BufferedImage fileIntoImage(File imgFile)
    {
        try {
            return ImageIO.read(imgFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(File imgFile, String outputFolderLocation)
    {
        BufferedImage img = fileIntoImage(imgFile);
        int height = img.getHeight(null);
        int width = img.getWidth(null);

        int[][] numMap = new int [height][width];

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int color = img.getRGB(j, i);
                for (int color_index = 0; color_index < colorTableSize; color_index++)
                {
                    if (colorTable[color_index].getRGB() == color)
                    {
                        numMap[i][j] = color_index;
                    }
                }
            }
        }

        File txtFile;

        try {
            int exists = 1;
            while (true) {
                txtFile = new File(outputFolderLocation + "\\" + "tilemap" + exists + ".txt");
                if (txtFile.createNewFile()) {
                    break;
                } else {
                    exists++;
                }
            }
            FileWriter myWriter = new FileWriter(txtFile);

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
