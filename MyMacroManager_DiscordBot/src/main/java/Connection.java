import discord4j.core.DiscordClient;

public class Connection {
    private DiscordClient client;
    public Connection() {
        client = DiscordClient.create("Server Token here");
    }

    public DiscordClient client(){
        return client;
    }
}
