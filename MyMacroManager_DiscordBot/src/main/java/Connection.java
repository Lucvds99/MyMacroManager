import discord4j.core.DiscordClient;

public class Connection {
    private DiscordClient client;
    public Connection() {
        client = DiscordClient.create("MTExNzAzMTMxMzExMTc4MTUwNg.GLUC9e.cZIFPu5bgVyELB-DbuwwtTmBegFvfiX3iKh0gc");
    }

    public DiscordClient client(){
        return client;
    }
}
