import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

import java.time.Instant;

public class VersionControl {
    private EmbedCreateSpec embedCreateSpec;

    public VersionControl() {
        embedCreateSpec = EmbedCreateSpec.builder()
                .color(Color.DEEP_LILAC)
                .title("Version Control")
                .description("Version 1.3.2")
                .author("Created by: Double",null, null)
                .description("Version control panel")
                .thumbnail("https://cdn.discordapp.com/avatars/564839474249334807/a_05f1000abae0486262310f60441d09eb.gif")
                .image("https://images.squarespace-cdn.com/content/v1/5f3086ffb5573e1a11f7dc18/1611617447555-FVY71KV3JB0YX0VPXKQS/YDMK+Macro.jpg")
                .timestamp(Instant.now())
                .build();
    }

    public EmbedCreateSpec embedCreateSpec(){
        return embedCreateSpec;
    }
}

