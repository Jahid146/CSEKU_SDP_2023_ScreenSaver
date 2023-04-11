package animation2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class paint2 extends JPanel implements ActionListener, KeyListener, MouseListener{

    final int panel_w;
    final int panel_h;
    Image image2;
    Timer timer;
    int xV = 2;
    int yV = 2;
    int x = 0;
    int y = 0;
    String imagePath;

    // Constructor
    paint2() {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        panel_w = (int) screensize.getWidth();
        panel_h = (int) screensize.getHeight();
        this.setPreferredSize(new Dimension(panel_w, panel_h));
        this.setBackground(Color.decode("#000000"));
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocus();
        init();
    }

    // Initializes the image and starts the timer
    private void init() {
        // Load image from saved path or ask user to choose an image file
        File file = new File("image_path.txt");
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                imagePath = br.readLine();
                br.close();
                image2 = ImageIO.read(new File(imagePath));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            chooseImageFile();
        }

        // Start the timer
        timer = new Timer(10, this);
        timer.start();
    }

    // Paints the image on the panel
    public void paint(Graphics g) {
        super.paint(g); // for paint background
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image2, x, y, this);
    }

    // Handles the action performed event of the timer
    @Override
    public void actionPerformed(ActionEvent e) {
        if (x >= panel_w - image2.getWidth(null) || x < 0) {
            xV = xV * -1;
        }
        x = x + xV;
        if (y >= panel_h - image2.getHeight(null) || y < 0) {
            yV = yV * -1;
        }
        y = y + yV;
        repaint();
    }

    // Handles the key pressed event
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                chooseImageFile();
                break;
            case KeyEvent.VK_DOWN:
                y += 10;
                break;
            case KeyEvent.VK_LEFT:
                x -= 10;
                break;
            case KeyEvent.VK_RIGHT:
                x += 10;
                break;
            default:
                break;
        }
        exitFrameIfMouseClicked();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        exitFrameIfMouseClicked();
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    // Asks the user to choose an image file
   private void chooseImageFile() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        imagePath = selectedFile.getAbsolutePath();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("image_path.txt"));
            bw.write(imagePath);
            bw.close();
            image2 = ImageIO.read(selectedFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    exitFrameIfMouseClicked();
    repaint();
}

// Exits the frame if the mouse is clicked
private void exitFrameIfMouseClicked() {
    if ( MouseInfo.getPointerInfo().getLocation().x >= this.getLocationOnScreen().x &&
             MouseInfo.getPointerInfo().getLocation().x <= this.getLocationOnScreen().x + this.getWidth() &&
             MouseInfo.getPointerInfo().getLocation().y >= this.getLocationOnScreen().y &&
             MouseInfo.getPointerInfo().getLocation().y <= this.getLocationOnScreen().y + this.getHeight()) {
             System.exit(0);
        }
  }


}
