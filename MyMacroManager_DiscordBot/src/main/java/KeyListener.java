import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends JFrame { // A GUI Frame

    public KeyListener() {
        // Add the listener
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                // Print out the code
                System.out.println(e);
            }
        });

        // Show something
        add(new JLabel("Hello World!"));
        pack();
    }

    public static void main(String[] args) {
        // Run the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                KeyListener gui = new KeyListener();
                gui.setVisible(true);
            }
        });
    }
}
