import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TileProcessor {

    ArrayList<Color> colorTable;
    int colorTableSize = 5;

    public TileProcessor()
    {
        colorTable = new ArrayList<>();
        setColors();
    }

    private void setColors()
    {
        colorTable.add(Color.white);
        colorTable.add(Color.black);
        colorTable.add(Color.red);
        colorTable.add(Color.green);
        colorTable.add(Color.blue);
        colorTable.add(Color.yellow);
        colorTable.add(Color.magenta);
        colorTable.add(Color.cyan);
        colorTable.add(new Color(0, 200, 50));
        colorTable.add(new Color(200, 0, 50));

        for (Color clr : colorTable)
        {
            System.out.println(clr.getRed() + " " + clr.getGreen() + " " + clr.getBlue());
        }
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

        // Fill the numMap array with color numbers
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int color = img.getRGB(j, i);
                for (Color clr : colorTable)
                {
                    if (clr.getRGB() == color)
                    {
                        numMap[i][j] = colorTable.indexOf(clr);
                        break;
                    }
                    else
                    {
                        numMap[i][j] = -1;
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
