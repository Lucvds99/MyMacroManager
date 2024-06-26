import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

import java.time.Clock;
import java.time.Instant;

public class VersionControl {
    private EmbedCreateSpec embedCreateSpec;
    public VersionControl(String username) {
        embedCreateSpec =  EmbedCreateSpec.builder()
                .color(Color.DEEP_LILAC)
                .title("Macro Manager")
                .author("Created by: the_Double",null, null)
                .thumbnail("https://cdn.discordapp.com/avatars/564839474249334807/a_af81854e341dedff30bbe8ca87c54403.webp")
                .addField("User: ",  username, false)
                .description("Version 2.0.1")
                .addField("discord4j", "3.3.0-M2", true)
                .addField("Shadow-Run", "8.1.1", true)
                .addField("AutoHotKey", "V2", true)
                .addField("logback", "1.3.7", true)
                .image("https://images.squarespace-cdn.com/content/v1/5f3086ffb5573e1a11f7dc18/1611617447555-FVY71KV3JB0YX0VPXKQS/YDMK+Macro.jpg")
                .footer("Version Date: ", null)
                .timestamp(Instant.now(Clock.fixed(Instant.parse("2024-04-12T00:00:00Z"), Clock.systemUTC().getZone().normalized())))
                .build();
    }
    public EmbedCreateSpec embedCreateSpec(){
        return embedCreateSpec;
    }
}

