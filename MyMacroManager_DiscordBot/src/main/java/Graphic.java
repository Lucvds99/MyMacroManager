import discord4j.core.object.entity.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Graphic {

    public static void takeScreenshot(User user) throws AWTException, IOException {
        // Get the default screen size
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        // Capture screenshot of the main desktop
        BufferedImage image = new Robot().createScreenCapture(screenRect);

        // Get the pictures directory in Windows
        String picturesDirectory = System.getProperty("user.home") + "\\Pictures";

        // Create a directory for screenshots if it doesn't exist
        String screenshotsDirectory = picturesDirectory + "\\MacroManagerScreenshots";
        Path screenshotsPath = Paths.get(screenshotsDirectory);
        if (!Files.exists(screenshotsPath)) {
            Files.createDirectories(screenshotsPath);
        }

        // Save the screenshot to the screenshots directory
        File screenshotFile = new File(screenshotsDirectory, "screenshot.png");
        ImageIO.write(image, "png", screenshotFile);

        try (InputStream inputStream = new FileInputStream(screenshotFile)) {
            user.getPrivateChannel()
                    .flatMap(channel -> channel.createMessage(spec -> {
                        spec.addFile("screenshot.png", inputStream);
                        spec.setContent("Here's the screenshot you requested:");
                    }))
                    .block();
        }
    }

}
