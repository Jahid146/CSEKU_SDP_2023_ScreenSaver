package animation3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class TextPaint extends JPanel implements ActionListener, KeyListener, MouseListener {
    final int panel_w;
    final int panel_h;
    Timer timer;
    int xV = 2;
    int yV = 2;
    int x = 0;
    int y = 0;
    private int textWidth;
    private int textHeight;
    String userText = "";

    public TextPaint() {
        super();
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

    private void init() {
        this.setPreferredSize(new Dimension(panel_w, panel_h));
        this.setBackground(Color.decode("#000000"));

        // read user text from file
        File file = new File("user_text.txt");
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                userText = br.readLine();
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            // ask user for input
            userText = JOptionPane.showInputDialog("Enter text to show on panel");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(userText);
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        timer = new Timer(10, this);
        timer.start();
        
    }

    //Paints the image on the panel.
    public void paint(Graphics g) {
        super.paint(g); // for paint background
        Graphics2D g2 = (Graphics2D) g;
        int FontType = Font.BOLD;
        int FontSize = 50;
        Font font = new Font("Rockwell Extra Bold", FontType, FontSize);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString(userText, x, y);
        Rectangle bounds = g.getFontMetrics().getStringBounds(userText, g).getBounds();
        textWidth = bounds.width;
        textHeight = bounds.height;
    }

    //Handles the action performed event of the timer.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (x >= panel_w - textWidth || x < 0) {
            xV = xV * -1;
        }
        x = x + xV;
        if (y >= panel_h - textHeight || y < 0) {
            yV = yV * -1;
        }
        y = y + yV;
        repaint();
    }

    //Handles the key pressed event.
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                // ask user for new input
                userText = JOptionPane.showInputDialog("Enter text to show on panel");
                // write new input to file
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(new File("user_text.txt")));
                    bw.write(userText);
                    bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case KeyEvent.VK_DOWN:
                y += 10;
                break;
            case KeyEvent.VK_LEFT:
                x -= 10;
                break;
            case KeyEvent.VK_RIGHT:
                x += 10;
    case KeyEvent.VK_SPACE:
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                Image image = ImageIO.read(selectedFile);
                Image scaledImage = image.getScaledInstance(panel_w, panel_h, Image.SCALE_SMOOTH);
                JLabel label = new JLabel(new ImageIcon(scaledImage));
                JOptionPane.showMessageDialog(null, label);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        break;
}
    }

// Other unused key event methods.
public void keyTyped(KeyEvent e) {}
public void keyReleased(KeyEvent e) {}

// Other unused mouse event methods.
public void mouseClicked(MouseEvent e) {}
public void mousePressed(MouseEvent e) {}
public void mouseReleased(MouseEvent e) {}
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {}


}