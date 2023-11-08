import discord4j.core.DiscordClient;

public class Connection {
    private DiscordClient client;
    private String UserId;
    public Connection() {
        client = DiscordClient.create("add Discord Bot Token");
        //UserId can be found by turning on developer mode In discord, clicking ur own profile and copying ur user ID, there will be a button at the bottom.
        //UserId is not needed, If you don't want to use the function to show you which device is connected there is no need.
        //If you don't want to use this function leave empty.
        UserId = "";
    }

    public DiscordClient client(){
        return client;
    }

    public String UserId(){
        return UserId;
    }
}
