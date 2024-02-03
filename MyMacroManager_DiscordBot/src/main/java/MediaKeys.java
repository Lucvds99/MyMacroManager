import java.io.IOException;



public class MediaKeys {

	public static void volumeMute(Connection connection) {
		executeAutoHotkeyScript("AutoKeysCommands\\volumeMute.ahk", connection);
	}

	public static void volumeDown(Connection connection) {
		executeAutoHotkeyScript("AutoKeysCommands\\volumeDown.ahk", connection);
	}

	public static void volumeUp(Connection connection) {
		executeAutoHotkeyScript("AutoKeysCommands\\volumeUp.ahk", connection);
	}

	public static void songPrevious(Connection connection) {
		executeAutoHotkeyScript("AutoKeysCommands\\songPrevious.ahk", connection);
	}

	public static void songNext(Connection connection) {
		executeAutoHotkeyScript("AutoKeysCommands\\songNext.ahk", connection);
	}

	public static void songPlayPause(Connection connection) {
		executeAutoHotkeyScript("AutoKeysCommands\\songPlayPause.ahk", connection);
	}

	public static void mediaStop(Connection connection) {
		executeAutoHotkeyScript("AutoKeysCommands\\mediaStop.ahk", connection);
	}

	private static void executeAutoHotkeyScript(String scriptPath, Connection connection) {
		try{
			String autoHotkeyPath =  connection.AutoHotKey() + "\\v2\\AutoHotkey.exe";
			scriptPath = connection.AutoHotKey() + "\\" + scriptPath;
			String command = "\"" + autoHotkeyPath + "\" \"" + scriptPath + "\"";
			Process process = Runtime.getRuntime().exec(command);
			int exitCode = process.waitFor();
			System.out.println("Exit Code: " + exitCode);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
