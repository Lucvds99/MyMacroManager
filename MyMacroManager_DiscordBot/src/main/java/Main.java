import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.event.domain.interaction.SelectMenuInteractionEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.component.SelectMenu;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import javax.xml.bind.Marshaller;
import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

//TODO fix Button not responding after a long time being active problem. (maybe a timeout?)

public class Main {
    private static DiscordClient client;
    public static void main(String[] args) throws AWTException {
        while (true) {
            Connection connection = new Connection();
            client = connection.client();


            //TODO turn into class
            final SelectMenu select = SelectMenu.of("menu",
                    SelectMenu.Option.of("Pc Related", "Pc"),
                    SelectMenu.Option.of("OBS", "Obs"),
                    SelectMenu.Option.of("Bot Related", "Bot"),
                    SelectMenu.Option.of("Secured Selection", "securedSelection")
            ).withPlaceholder("Select Category").withMinValues(1).withMaxValues(1);


            new Macros();

            ResetText resetText = new ResetText();
            
            //function buttons
            Button versionControlButton = Button.success("versionControlButton", "Version Control");

            //camera buttons
            Button cameraFade = Button.primary("cameraFade", "Camera Fade");
            Button cameraDelay = Button.secondary("cameraDelay", "camera delay");

            //control buttons
            Button switchDisplayRight = Button.primary("switchDisplayRight", "switch display >");
            Button switchDisplayLeft = Button.primary("switchDisplayLeft", "< switch display");
            Button mediaControl = Button.primary("mediaControl", "Media Control");


            //Secure Buttons
            Button logOff = Button.danger("logOff", "Lock");
            Button ShutDown = Button.danger("ShutDown", "Shut Down");

            Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
                // ReadyEvent
                Mono<Void> printOnLogin = gateway.on(ReadyEvent.class, event ->
                                Mono.fromRunnable(() -> {
                                    final User self = event.getSelf();
                                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                                }))
                        .then();
                // MessageCreateEvent
                Mono<Void> handlePingCommand = gateway.on(MessageCreateEvent.class, event -> {
                    Message message = event.getMessage();
                    if (message.getContent().equalsIgnoreCase("refresh")) {
                        return message.getChannel().flatMap(messageChannel -> messageChannel.createMessage(resetText.resetText).withComponents(ActionRow.of(select)));
                    }
                    return Mono.empty();
                }).then();

                Mono<Void> MenuListener = gateway.on(SelectMenuInteractionEvent.class, event -> {
                    if (event.getCustomId().equals("menu")) {
                        if (event.getValues().get(0).equals("Pc")) {
                            return event.reply(resetText.resetText).withComponents(ActionRow.of(switchDisplayLeft, switchDisplayRight, mediaControl), ActionRow.of(select)).then();
                        } else if (event.getValues().get(0).equals("Obs")) {
                            return event.reply(resetText.resetText).withComponents(ActionRow.of(cameraFade, cameraDelay), ActionRow.of(select)).then();
                        } else if ( event.getValues().get(0).equals("Bot")) {
                            return event.reply(resetText.resetText).withComponents(ActionRow.of(versionControlButton), ActionRow.of(select)).then();
                        } else if (event.getValues().get(0).equals("securedSelection")) {
                            return event.reply(resetText.resetText).withComponents(ActionRow.of(logOff, ShutDown), ActionRow.of(select)).then();
                        }
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
                    } else if (event.getCustomId().equals("logOff")) {
                        Macros.logOff();
                        return event.deferEdit();
                    } else if (event.getCustomId().equals("mediaControl")) {
                        Macros.pausePlayMedia();
                        return event.deferEdit();
                    } else if (event.getCustomId().equals("ShutDown")) {
                        Macros.shutDown();
                        return event.deferEdit();
                    } else if (event.getCustomId().equals("mediaControl")) {
                        Macros.pausePlayMedia();
                        return event.deferEdit();
                    } else if (event.getCustomId().equals("versionControlButton")) {
                        VersionControl versionControl = new VersionControl(event.getInteraction().getUser().getUsername());
                        return event.reply().withEmbeds(versionControl.embedCreateSpec()).withEphemeral(true);
                    }
                    else {
                        return Mono.empty();
                    }
                }).timeout(Duration.ofMinutes(30)).onErrorResume(TimeoutException.class, ignore -> Mono.empty()).then();
                return printOnLogin.and(handlePingCommand).and(tempListener).and(MenuListener);
            });
            login.block();
        }
    }
}
