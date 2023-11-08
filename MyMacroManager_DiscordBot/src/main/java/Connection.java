import discord4j.core.DiscordClient;

public class Connection {
    private DiscordClient client;
    public Connection() {
        client = DiscordClient.create("MTExNzAzMTMxMzExMTc4MTUwNg.G7EC1t.8FnMPYYQiM9UZrzNKA6qUTqAHT2wNqg_h4QlOs");
    }

    public DiscordClient client(){
        return client;
    }
}
