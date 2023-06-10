import java.awt.*;

public class MacroTester {

    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        cameraChange(robot);

    }


    private static void cameraChange(Robot robot){
        robot.delay(1000);
        robot.keyPress(17);
        robot.keyPress(16);
        robot.keyPress(33);
        robot.delay(500);
        robot.keyRelease(17);
        robot.keyRelease(16);
        robot.keyRelease(33);
    }
}
