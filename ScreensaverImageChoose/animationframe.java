
package animation2;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author jahid
 */
public class animationframe extends JFrame {
    paint2 p;
    animationframe() throws IOException{
        p = new paint2();
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(p);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    }
    
    
}
