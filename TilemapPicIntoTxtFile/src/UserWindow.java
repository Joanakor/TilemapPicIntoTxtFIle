import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserWindow extends JFrame implements ActionListener {

    JMenuBar menuBar;
    JMenu help;
    JLabel preview;
    JLabel text1;
    JLabel text2;
    JComboBox<String> addFile;
    JComboBox<String> addFolder;
    JLabel status;
    ImageIcon previewIcon;

    String statusMessage;
    public UserWindow(){
        setDefaultConfigurations();

        //------Menu bar-------
        menuBar = new JMenuBar();
        help = new JMenu("Help");
        help.addActionListener(this);
        menuBar.add(help);

        //------Preview label----------
        preview = new JLabel();
        preview.setBounds(150, 10, 100, 100);
        preview.setBorder(BorderFactory.createLineBorder(null, 1));

        //------First prompt-----------
        text1 = new JLabel("Select properly coloured tilemap image:");
        text1.setBounds(80, 120, 250, 20);

        addFile = new JComboBox<String>();
        addFile.setBounds(80, 150, 230, 20);
        addFile.setFocusable(false);

        addFile.addItem("");
        addFile.addItem("Select picture...");


        //--------Second prompt----------
        text2 = new JLabel("Select the folder for the output file:");
        text2.setBounds(80, 180, 250, 20);

        addFolder = new JComboBox<String>();
        addFolder.setBounds(80, 210, 230, 20);
        addFolder.setFocusable(false);

        addFolder.addItem("");
        addFolder.addItem("Select folder...");

        //-------Status---------
        statusMessage = "So far so good";
        status = new JLabel("Status: " + statusMessage);
        status.setBounds(80, 220, 250, 100);

        setJMenuBar(menuBar);
        add(preview);
        add(text1);
        add(addFile);
        add(text2);
        add(addFolder);
        add(status);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public ImageIcon resizeAndSetImageIcon(String pathname)
    {
        BufferedImage newImg = null;

        try {
            newImg = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = newImg.getScaledInstance(preview.getWidth(), preview.getHeight(), Image.SCALE_DEFAULT);
        return new ImageIcon(dimg);
    }

    public void setDefaultConfigurations()
    {
        setTitle("Tilemap picture to txt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400,400);
        setLayout(null);
        setResizable(false);
    }

}
