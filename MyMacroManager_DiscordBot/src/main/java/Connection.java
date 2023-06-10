import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

public class Connection {
    private DiscordClient client;
    public Connection() {
        client = DiscordClient.create("MTExNzAzMTMxMzExMTc4MTUwNg.GApi9T.1jLQiA_SajMpFaW_jKwR7S2zYntnWAkP2bL5D0");
    }

    public DiscordClient client(){
        return client;
    }
}
