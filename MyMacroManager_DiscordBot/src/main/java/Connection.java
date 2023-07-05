import discord4j.core.DiscordClient;

public class Connection {
    private DiscordClient client;
    public Connection() {
        client = DiscordClient.create("enter token here");
    }

    public DiscordClient client(){
        return client;
    }
}
