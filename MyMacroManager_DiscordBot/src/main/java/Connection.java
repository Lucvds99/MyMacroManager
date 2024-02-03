import discord4j.core.DiscordClient;
public class Connection {
    private final DiscordClient client;
    private final String UserId;
    private final String AutoHotKeyLocation;
    public Connection() {
        //this is ur bots Private key.
        client = DiscordClient.create("");

        //UserId can be found by turning on developer mode In discord, clicking ur own profile and copying ur user ID, there will be a button at the bottom.
        //UserId is not needed, If you don't want to use the function to show you which device is connected there is no need.
        //If you don't want to use this function leave empty.
        UserId = "";

        //This is the location of the AutoHotKey folder that you will find in the repository. it is automatically set to ProgramFiles.
        //I do recommend you put it there. if you decide to put the files elsewhere change string below.
        AutoHotKeyLocation = "C:\\Program Files\\AutoHotkey";
    }

    public DiscordClient client(){
        return client;
    }

    public String UserId(){
        return UserId;
    }

    public String AutoHotKey(){
        return AutoHotKeyLocation;
    }
}
