import discord4j.core.DiscordClient;

public class Connection {
    private DiscordClient client;
    public Connection() {
        client = DiscordClient.create("MTExNzAzMTMxMzExMTc4MTUwNg.GApi9T.1jLQiA_SajMpFaW_jKwR7S2zYntnWAkP2bL5D0");
    }

    public DiscordClient client(){
        return client;
    }
}
