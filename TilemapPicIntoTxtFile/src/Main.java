import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class Main {
    public static void main(String[] args)
    {
        File file = new File("C:/Users/mark6/OneDrive/Documents/TilemapPicIntoTxtFile/TilemapPicIntoTxtFile/src/reference_map.png");
        TileProcessor tileProcessor = new TileProcessor();

        tileProcessor.run(file);
    }
}