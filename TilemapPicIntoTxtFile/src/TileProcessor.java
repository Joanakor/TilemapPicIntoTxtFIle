import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Class for text file creation
public class TileProcessor {

    // ArrayList of all supported colors
    final ArrayList<Color> colorTable;
    public TileProcessor()
    {
        colorTable = new ArrayList<>();
        setColors();
    }

    // Set supported colors
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
    }

    // Convert File to BufferedImage
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

        // Fill the numMap array with color numbers
        int[][] numMap = fillNumberArray(img, height, width);

        writeToFile(numMap, height, width, outputFolderLocation);
    }

    private int[][] fillNumberArray(BufferedImage img, int height, int width)
    {
        int[][] numMap = new int [height][width];
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
        return numMap;
    }

    private void writeToFile(int [][] numMap, int height, int width, String outputFolderLocation)
    {
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
