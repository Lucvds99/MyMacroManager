import discord4j.core.DiscordClient;

public class Connection {
    private DiscordClient client;
    public Connection() {
        client = DiscordClient.create("Add bot Token");
    }

    public DiscordClient client(){
        return client;
    }
}
