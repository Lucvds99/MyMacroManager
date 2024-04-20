import discord4j.common.util.Snowflake;
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
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.json.Heartbeat;
import discord4j.voice.json.HeartbeatAck;
import reactor.core.publisher.Mono;


import javax.xml.bind.Marshaller;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeoutException;


//TODO: add reconnect Button that basically restarts the bot;



public class Main {


    private static DiscordClient client;


    public static void main(String[] args) {

        while (true) {
            try {
                String UserId;
                Connection connection = new Connection();
                UserId = connection.UserId();
                client = connection.client();
                client.login();
                client.gateway().login();



                //TODO turn into class
                final SelectMenu select = SelectMenu.of("menu",
                        SelectMenu.Option.of("Pc Related", "Pc"),
                        SelectMenu.Option.of("Media Control", "Media"),
                        SelectMenu.Option.of("OBS", "Obs"),
                        SelectMenu.Option.of("Bot Related", "Bot"),
                        SelectMenu.Option.of("Graphic", "Graphic"),
                        SelectMenu.Option.of("Secured Selection", "securedSelection")
                ).withPlaceholder("Select Category").withMinValues(1).withMaxValues(1);


                new Macros(connection);

                ResetText resetText = new ResetText();

                //function buttons
                Button versionControlButton = Button.success("versionControlButton", "Version Control");

                //camera buttons
                Button cameraFade = Button.primary("cameraFade", "Camera Fade");
                Button cameraDelay = Button.secondary("cameraDelay", "camera delay");

                //control buttons
                Button switchDisplayRight = Button.primary("switchDisplayRight", "switch display >");
                Button switchDisplayLeft = Button.primary("switchDisplayLeft", "< switch display");

                //media buttons
                ReactionEmoji skipEmoji = ReactionEmoji.unicode("\u23ED");
                ReactionEmoji playPauseEmoji = ReactionEmoji.unicode("\u23EF");
                ReactionEmoji backEmoji = ReactionEmoji.unicode("\u23EE");
                ReactionEmoji volumeUpEmoji = ReactionEmoji.of(null, "\uD83D\uDD0A", false);
                ReactionEmoji volumeDownEmoji = ReactionEmoji.of(null, "\uD83D\uDD08", false);

                Button forward = Button.secondary("forward", skipEmoji);
                Button playPause = Button.secondary("playPause", playPauseEmoji);
                Button back = Button.secondary("back", backEmoji);
                Button volumeUp = Button.secondary("volumeUp", volumeUpEmoji);
                Button volumeDown = Button.secondary("volumeDown", volumeDownEmoji);

                //Graphical buttons
                Button screenshot = Button.primary("screenshot", "Screen shot");

                //Secure Buttons
                Button logOff = Button.danger("logOff", "Lock");
                Button ShutDown = Button.danger("ShutDown", "Shut Down");






                Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
                    Mono<Void> sendStartupMessage = gateway.on(ReadyEvent.class, event -> {
                        final User self = event.getSelf();
                        String messageContent = getComputerName()+ ": Connected";
                        System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                        // Send a direct message to your user
                        if (!UserId.equals("")){
                            return gateway
                                    .getUserById(Snowflake.of(UserId))
                                    .flatMap(User::getPrivateChannel)
                                    .flatMap(privateChannel -> privateChannel.createMessage(messageContent).withComponents(ActionRow.of(select)))
                                    .then();
                        }
                        return Mono.empty();
                    }).then();

                    Mono<Void> handlePingCommand = gateway.on(MessageCreateEvent.class, event -> {
                        Message message = event.getMessage();
                        if (message.getContent().equalsIgnoreCase("r")) {
                            return message.getChannel().flatMap(messageChannel -> messageChannel.createMessage(resetText.resetText).withComponents(ActionRow.of(select)));
                        }
                        return Mono.empty();
                    }).then();



                    Mono<Void> MenuListener = gateway.on(SelectMenuInteractionEvent.class, event -> {
                        if (event.getCustomId().equals("menu")) {
                            switch (event.getValues().get(0)) {
                                case "Pc":
                                    return event.reply(resetText.resetText).withComponents(ActionRow.of(switchDisplayLeft, switchDisplayRight), ActionRow.of(select)).then();
                                case "Obs":
                                    return event.reply(resetText.resetText).withComponents(ActionRow.of(cameraFade, cameraDelay), ActionRow.of(select)).then();
                                case "Media":
                                    return event.reply(resetText.resetText).withComponents(ActionRow.of(back, playPause, forward), ActionRow.of(volumeUp, volumeDown), ActionRow.of(select)).then();
                                case "Bot":
                                    return event.reply(resetText.resetText).withComponents(ActionRow.of(versionControlButton), ActionRow.of(select)).then();
                                case "Graphic":
                                    return event.reply(resetText.resetText).withComponents(ActionRow.of(screenshot), ActionRow.of(select)).then();
                                case "securedSelection":
                                    return event.reply(resetText.resetText).withComponents(ActionRow.of(logOff, ShutDown), ActionRow.of(select)).then();
                            }
                        }
                        return Mono.empty();
                    }).then();

                    Mono<Void> tempListener = gateway.on(ButtonInteractionEvent.class, event -> {
                        User user = event.getInteraction().getUser();
                        System.out.println(event.getCustomId());

                        switch (event.getCustomId()) {
                            case "screenshot":
                                // Take a screenshot
                                try {
                                    Graphic.recordDesktop(user, 20);
                                    // Respond to the interaction indicating success
                                    return event.reply("Screenshot captured!").then();
                                } catch (Exception e) {
                                    // Respond to the interaction indicating failure
                                    return event.reply("Failed to capture screenshot.").then();
                                }
                            case "cameraFade":
                                Macros.cameraChange();
                                return event.deferEdit();
                            case "switchDisplayRight":
                                Macros.switchDisplayRight();
                                return event.deferEdit();
                            case "switchDisplayLeft":
                                Macros.switchDisplayLeft();
                                return event.deferEdit();
                            case "cameraDelay":
                                Macros.cameraDelay();
                                return event.deferEdit();
                            case "logOff":
                                Macros.logOff();
                                return event.deferEdit();
                            case "playPause":
                                Macros.pausePlayMedia();
                                return event.deferEdit();
                            case "ShutDown":
                                Macros.shutDown();
                                return event.deferEdit();
                            case "back":
                                Macros.back();
                                return event.deferEdit();
                            case "forward":
                                Macros.forward();
                                return event.deferEdit();
                            case "volumeUp":
                                Macros.volumeUp();
                                return event.deferEdit();
                            case "volumeDown":
                                Macros.volumeDown();
                                return event.deferEdit();
                            case "versionControlButton":
                                VersionControl versionControl = new VersionControl(event.getInteraction().getUser().getUsername());
                                return event.reply().withEmbeds(versionControl.embedCreateSpec()).withEphemeral(true);
                            default:
                                return Mono.empty();
                        }
                    }).onErrorResume(TimeoutException.class, ignore -> Mono.empty()).then();
                    return sendStartupMessage.and(handlePingCommand).and(tempListener).and(MenuListener);
                });
                login.block();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }
    private static String getComputerName()
    {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else return env.getOrDefault("HOSTNAME", "Unknown Computer");
    }
}


