import discord4j.core.DiscordClient;

public class Connection {
    private DiscordClient client;
    public Connection() {
        client = DiscordClient.create("MTExNzAzMTMxMzExMTc4MTUwNg.G3QeHy.si8bWBoB2kX5IBGJQNZCC84myOSnVJScSudbOg");
    }

    public DiscordClient client(){
        return client;
    }
}
