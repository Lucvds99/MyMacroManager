import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Graphic {

    public static void takeScreenshot() throws AWTException, IOException {
        // Get the default screen size
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        // Capture screenshot of the main desktop
        BufferedImage image = new Robot().createScreenCapture(screenRect);

        // Save the screenshot to a file (you can modify this to send it to Discord)
        File screenshotFile = new File("screenshot.png");
        ImageIO.write(image, "png", screenshotFile);
    }
}
