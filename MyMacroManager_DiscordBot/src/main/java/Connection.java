import discord4j.core.DiscordClient;

public class Connection {
    private DiscordClient client;
    public Connection() {
        client = DiscordClient.create("MTExNzAzMTMxMzExMTc4MTUwNg.GW-sou.D5kOyO_kETRjo0ERaifqwAVe-FDGC4ecuPfWjQ");
    }

    public DiscordClient client(){
        return client;
    }
}
