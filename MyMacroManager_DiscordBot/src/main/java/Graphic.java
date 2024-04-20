import discord4j.core.object.entity.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

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

    public static void recordDesktop(User user, int durationInSeconds) {
        try {
            // Create a directory for videos if it doesn't exist
            String videosDirectory = System.getProperty("user.home") + "\\Videos";
            Path videosPath = Paths.get(videosDirectory);
            if (!Files.exists(videosPath)) {
                Files.createDirectories(videosPath);
            }

            // Create a timestamp for the video filename
            long timestamp = System.currentTimeMillis();
            String videoFilename = "desktop_video_" + timestamp + ".mp4";
            String videoFilePath = videosDirectory + "\\" + videoFilename;

            // Initialize JavaFX environment
            JFXPanel fxPanel = new JFXPanel();

            // Start JavaFX application on a separate thread
            javafx.application.Platform.runLater(() -> {
                // Create a new JavaFX stage
                Stage stage = new Stage();
                stage.setTitle("Desktop Recorder");

                // Create a JavaFX scene
                StackPane root = new StackPane();
                Scene scene = new Scene(root);

                // Capture the screen and create a Media object
                Rectangle screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                try {
                    Robot robot = new Robot();
                    BufferedImage screenCapture = robot.createScreenCapture(screenBounds);
                    File tempFile = File.createTempFile("temp", ".png");
                    ImageIO.write(screenCapture, "png", tempFile);
                    Media media = new Media(tempFile.toURI().toString());

                    // Play the captured screen as a video
                    MediaView mediaView = new MediaView();
                    mediaView.setMediaPlayer(new javafx.scene.media.MediaPlayer(media));
                    root.getChildren().add(mediaView);

                    // Set the scene to the stage
                    stage.setScene(scene);

                    // Show the stage
                    stage.show();

                    // Play the media
                    javafx.scene.media.MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                    mediaPlayer.setOnEndOfMedia(() -> {
                        mediaPlayer.stop();
                        stage.close();
                    });
                    mediaPlayer.play();

                    // Stop the recording after the specified duration
                    try {
                        Thread.sleep(durationInSeconds * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Save the recording as a video file
                    mediaPlayer.stop();
                    stage.close();
                    Files.move(tempFile.toPath(), Paths.get(videoFilePath));
                } catch (AWTException | IOException e) {
                    e.printStackTrace();
                }

                // Send the video file to the user
                try (InputStream inputStream = new FileInputStream(videoFilePath)) {
                    user.getPrivateChannel()
                            .flatMap(channel -> channel.createMessage(spec -> {
                                spec.addFile(videoFilename, inputStream);
                                spec.setContent("Here's the video recording you requested:");
                            }))
                            .block();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
