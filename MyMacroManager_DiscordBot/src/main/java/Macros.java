import java.awt.*;
import java.io.IOException;

public class Macros {
    private static Robot robot;
    private static Connection connection;


    protected Macros(Connection connection) throws AWTException {
        Macros.connection = connection;
        robot = new Robot();
    }

    public static void cameraChange() {
        robot.keyPress(17); // Ctrl
        robot.keyPress(16); // Shift
        robot.keyPress(33); // Page Up
        robot.delay(500);
        robot.keyRelease(17); // Release Ctrl
        robot.keyRelease(16); // Release Shift
        robot.keyRelease(33); // Release Page Up
    }

    public static void cameraDelay(){
        robot.keyPress(17);   // Ctrl
        robot.keyPress(18);   // Alt
        robot.keyPress(91);   // Left Bracket
        robot.delay(500);
        robot.keyRelease(91); // Release Left Bracket
        robot.keyRelease(18); // Release Alt
        robot.keyRelease(17); // Release Ctrl
    }

    public static void switchDisplayRight() {
        robot.keyPress(17);    // Ctrl
        robot.keyPress(524);   // Windows
        robot.keyPress(39);    // Arrow Right
        robot.delay(500);
        robot.keyRelease(39);  // Release Arrow Right
        robot.keyRelease(524); // Release Windows
        robot.keyRelease(17);  // Release Ctrl
    }

    public static void switchDisplayLeft(){
        robot.keyPress(17);    // Ctrl
        robot.keyPress(524);   // Windows
        robot.keyPress(37);    // Arrow Left
        robot.delay(500);
        robot.keyRelease(37);  // Release Arrow Left
        robot.keyRelease(524); // Release Windows
        robot.keyRelease(17);  // Release Ctrl
    }

    public static void logOff() {
        String shutdownCmd = "rundll32.exe user32.dll,LockWorkStation";
        try {
            Process child = Runtime.getRuntime().exec(shutdownCmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shutDown(){
        String shutdownCmd = "shutdown /s -t 0";
        try {
            Process child = Runtime.getRuntime().exec(shutdownCmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void pausePlayMedia() {
        MediaKeys.songPlayPause(connection);
    }

    public static void volumeUp() {
        MediaKeys.volumeUp(connection);
        MediaKeys.volumeUp(connection);
        MediaKeys.volumeUp(connection);
        MediaKeys.volumeUp(connection);
        MediaKeys.volumeUp(connection);
        MediaKeys.volumeUp(connection);
    }
    public static void volumeDown() {
        MediaKeys.volumeDown(connection);
        MediaKeys.volumeDown(connection);
        MediaKeys.volumeDown(connection);
        MediaKeys.volumeDown(connection);
        MediaKeys.volumeDown(connection);
        MediaKeys.volumeDown(connection);
    }

    public static void back() {
        MediaKeys.songPrevious(connection);
    }

    public static void forward() {
        MediaKeys.songNext(connection);
    }
}
