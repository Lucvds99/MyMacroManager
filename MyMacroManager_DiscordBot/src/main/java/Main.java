import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.time.Duration;
import java.util.concurrent.TimeoutException;


public class Main {
    private static DiscordClient client;
    private static VersionControl versionControl = new VersionControl();

    public static void main(String[] args) throws AWTException {
        while (true) {
            Connection connection = new Connection();
            client = connection.client();

            versionControl = new VersionControl();
            new Macros();

            ResetText resetText = new ResetText();

            //function buttons
            Button reset = Button.primary("reset", "reset");
            Button versionControlButton = Button.success("versionControlButton", "Version Control");

            //camera buttons
            Button cameraFade = Button.secondary("cameraFade", "Camera Fade");
            Button cameraDelay = Button.secondary("cameraDelay", "camera delay");

            //control buttons
            Button switchDisplayRight = Button.secondary("switchDisplayRight", "switch display >");
            Button switchDisplayLeft = Button.secondary("switchDisplayLeft", "< switch display");





            Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
                // ReadyEvent example
                Mono<Void> printOnLogin = gateway.on(ReadyEvent.class, event ->
                                Mono.fromRunnable(() -> {
                                    final User self = event.getSelf();
                                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                                }))
                        .then();

                // MessageCreateEvent example
                Mono<Void> handlePingCommand = gateway.on(MessageCreateEvent.class, event -> {
                    Message message = event.getMessage();

                    if (message.getContent().equalsIgnoreCase("menu")) {
                        return message.getChannel().flatMap(messageChannel -> messageChannel.createMessage("")
                                .withComponents(ActionRow.of(cameraFade, cameraDelay, switchDisplayLeft, switchDisplayRight ,versionControlButton), ActionRow.of(reset)));
                    } else if (message.getContent().equalsIgnoreCase("reset")) {
                        return message.getChannel().flatMap(messageChannel -> messageChannel.createMessage(resetText.toString()));
                    }
                    return Mono.empty();
                }).then();

                Mono<Void> tempListener = gateway.on(ButtonInteractionEvent.class, event -> {
                    System.out.println(event.getCustomId());

                    if (event.getCustomId().equals("cameraFade")) {
                        Macros.cameraChange();
                        return event.deferEdit();
                    } else if (event.getCustomId().equals("switchDisplayRight")) {
                        Macros.switchDisplayRight();
                        return event.deferEdit();
                    } else if (event.getCustomId().equals("switchDisplayLeft")) {
                        Macros.switchDisplayLeft();
                        return event.deferEdit();
                    } else if (event.getCustomId().equals("cameraDelay")) {
                        Macros.cameraDelay();
                        return event.deferEdit();
                    } else if (event.getCustomId().equals("reset")) {
                        return event.reply(resetText.resetText).withComponents(ActionRow.of(cameraFade, cameraDelay, switchDisplayLeft, switchDisplayRight ,versionControlButton), ActionRow.of(reset));
                    } else if (event.getCustomId().equals("versionControlButton")) {
                        return event.reply().withEmbeds(versionControl.embedCreateSpec()).withEphemeral(true);
                    } else {
                        return Mono.empty();
                    }
                }).timeout(Duration.ofMinutes(30)).onErrorResume(TimeoutException.class, ignore -> Mono.empty()).then();
                return printOnLogin.and(handlePingCommand).and(tempListener);
            });
            login.block();
        }
    }
}
