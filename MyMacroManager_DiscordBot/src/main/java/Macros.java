import java.awt.*;

public class Macros {
    private static Robot robot;


    protected Macros() throws AWTException {

        robot = new Robot();
    }

    public static void cameraChange() {
        robot.delay(1000);
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
        robot.keyPress(524);   // Windows
        robot.keyPress(76);    // L
        robot.delay(500);
        robot.keyRelease(76);  // Release L
        robot.keyRelease(524); // Release Windows
    }

}
