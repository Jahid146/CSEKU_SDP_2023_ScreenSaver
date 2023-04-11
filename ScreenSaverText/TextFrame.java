package animation3;
import java.io.IOException;
import javax.swing.*;
/**
 *
 * @author jahid
 */
public class TextFrame extends JFrame{
    TextPaint P;
    TextFrame() throws IOException{
        P = new TextPaint();
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(P);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    }

}
