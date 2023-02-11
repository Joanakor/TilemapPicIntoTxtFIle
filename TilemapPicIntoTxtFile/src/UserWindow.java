import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserWindow extends JFrame implements ActionListener {

    TileProcessor tileProcessor;
    JMenuBar menuBar;
    JMenu help;
    JLabel preview;
    JLabel text1;
    JLabel text2;
    JComboBox<String> addFile;
    JComboBox<String> addFolder;
    JLabel status;

    JButton confirmButton;

    File selectedPicture;

    String selectedOutputFolder;

    public UserWindow(){
        tileProcessor = new TileProcessor();

        setDefaultConfigurations();

        //------Menu bar-------
        setMenuBar();

        //------Preview label--------
        setPreviewLabel();

        //------First prompt-------
        setFilePrompt();

        //--------Second prompt--------
        setFolderPrompt();

        setConfirmButton();

        setStatus();
        
        addAllComponents();
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addFile && addFile.getSelectedItem() == "Select picture...")
        {
            setTilemapPictureFile();
        }
        if (e.getSource() == addFolder && addFolder.getSelectedItem() == "Select folder...")
        {
            setOutputFolder();
        }
        if (e.getSource() == confirmButton)
        {
            if(selectedPicture != null && selectedOutputFolder != null)
            {
                tileProcessor.run(selectedPicture, selectedOutputFolder);
                setStatusMessage("Done!");
            }
            else if(selectedPicture == null)
            {
                setStatusMessage("You have not specify tilemap picture!");
            }
            else
            {
                setStatusMessage("You have not specify output folder!");
            }
        }
    }

    private void setDefaultConfigurations()
    {
        setIconImage(new ImageIcon("src/blueprint.png").getImage());
        setTitle("Tilemap picture to txt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400,400);
        setLayout(null);
        setResizable(false);
    }
    
    private void setMenuBar()
    {
        menuBar = new JMenuBar();
        help = new JMenu("Help");
        help.addActionListener(this);
        menuBar.add(help);
    }
    
    private void setPreviewLabel()
    {
        preview = new JLabel();
        preview.setBounds(150, 10, 100, 100);
        preview.setBorder(BorderFactory.createLineBorder(null, 1));
    }
    
    private void setFilePrompt()
    {
        text1 = new JLabel("Select properly coloured tilemap image:");
        text1.setBounds(80, 120, 250, 20);

        addFile = new JComboBox<>();
        addFile.setBounds(80, 150, 230, 20);
        addFile.setFocusable(false);

        addFile.addActionListener(this);

        addFile.addItem("");
        addFile.addItem("Select picture...");
        addFile.setSelectedIndex(0);
    }
    
    private void setFolderPrompt()
    {
        text2 = new JLabel("Select the folder for the output file:");
        text2.setBounds(80, 180, 250, 20);

        addFolder = new JComboBox<>();
        addFolder.setBounds(80, 210, 230, 20);
        addFolder.setFocusable(false);

        addFolder.addActionListener(this);

        addFolder.addItem("");
        addFolder.addItem("Select folder...");
        addFolder.setSelectedIndex(0);
    }

    private void setConfirmButton()
    {
        confirmButton = new JButton("confirmButton");
        confirmButton.setFocusable(false);
        confirmButton.addActionListener(this);
        confirmButton.setBounds(150, 240, 100, 20);
    }

    private void setStatus()
    {
        status = new JLabel("So far so good");
        status.setBounds(80, 265, 230, 70);
        status.setHorizontalAlignment(JLabel.CENTER);

        status.setBorder(BorderFactory.createLineBorder(null, 2, true));
        status.setVerticalTextPosition(JLabel.NORTH);
    }

    private void addAllComponents()
    {
        setJMenuBar(menuBar);
        add(preview);
        add(text1);
        add(addFile);
        add(text2);
        add(addFolder);
        add(confirmButton);
        add(status);
    }

    private void resizeAndSetImageIcon(File selectedPicture)
    {
        BufferedImage newImg = null;

        try {
            newImg = ImageIO.read(selectedPicture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert newImg != null;
        Image tempImage = newImg.getScaledInstance(preview.getWidth(), preview.getHeight(), Image.SCALE_DEFAULT);
        preview.setIcon(new ImageIcon(tempImage));
    }
    private void setTilemapPictureFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".png .jpg .jpeg", "png", "jpg", "jpeg");
        fileChooser.setFileFilter(filter);
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION)
        {
            selectedPicture = fileChooser.getSelectedFile();
            addFile.removeItemAt(0);
            addFile.insertItemAt(fileChooser.getSelectedFile().getAbsolutePath(), 0);
            addFile.setSelectedIndex(0);
            resizeAndSetImageIcon(selectedPicture);
        }
    }

    private void setOutputFolder()
    {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int response = folderChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION)
        {
            selectedOutputFolder = String.valueOf(folderChooser.getSelectedFile());
            addFolder.removeItemAt(0);
            addFolder.insertItemAt(selectedOutputFolder, 0);
            addFolder.setSelectedIndex(0);
        }
    }

    private void setStatusMessage(String message)
    {
        status.setText(message);
    }

}
