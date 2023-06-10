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
import reactor.core.publisher.Mono;

import java.awt.*;
import java.time.Duration;
import java.util.concurrent.TimeoutException;



public class Main {
    private static DiscordClient client;
    public static void main(String[] args) throws AWTException {
        Connection connection = new Connection();
        client = connection.client();

        ResetText resetText = new ResetText();

        Button cameraFade = Button.primary("cameraFade", "Camera Fade");
        Button switchDisplayRight = Button.primary("switchDisplayRight", "switch display >");
        Button switchDisplayLeft = Button.primary("switchDisplayLeft", "< switch display");
        Button cameraDelay = Button.primary("cameraDelay", "camera delay");
        Button reset = Button.primary("reset", "reset");

        Robot robot = new Robot();

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
                            .withComponents(ActionRow.of(cameraFade, cameraDelay, switchDisplayRight, switchDisplayLeft, reset)));
                }
                else if (message.getContent().equalsIgnoreCase("reset")) {
                    return message.getChannel().flatMap(messageChannel -> messageChannel.createMessage(resetText.toString()));
                }

                return Mono.empty();
            }).then();

            Mono<Void> tempListener = gateway.on(ButtonInteractionEvent.class, event -> {
                System.out.println(event.getCustomId());

                if (event.getCustomId().equals("cameraFade")) {
                    robot.keyPress(17);
                    robot.keyPress(16);
                    robot.keyPress(33);
                    robot.delay(500);
                    robot.keyRelease(17);
                    robot.keyRelease(16);
                    robot.keyRelease(33);


                    return event.reply("camera has Faded");
                } else if (event.getCustomId().equals("switchDisplayRight")){
                    robot.keyPress(17);    // Ctrl
                    robot.keyPress(524);   // Windows
                    robot.keyPress(39);    // Arrow Right
                    robot.delay(500);
                    robot.keyRelease(39);  // Release Arrow Right
                    robot.keyRelease(524); // Release Windows
                    robot.keyRelease(17);  // Release Ctrl

                    return event.reply("pc has gone to a display on the right");
                }else if (event.getCustomId().equals("switchDisplayLeft")){
                    robot.keyPress(17);    // Ctrl
                    robot.keyPress(524);   // Windows
                    robot.keyPress(37);    // Arrow Left
                    robot.delay(500);
                    robot.keyRelease(37);  // Release Arrow Left
                    robot.keyRelease(524); // Release Windows
                    robot.keyRelease(17);  // Release Ctrl

                    return event.reply("pc has gone to a display on the left");
                }else if (event.getCustomId().equals("cameraDelay")) {

                    robot.keyPress(17);   // Ctrl
                    robot.keyPress(18);   // Alt
                    robot.keyPress(91);   // Left Bracket
                    robot.delay(500);
                    robot.keyRelease(91); // Release Left Bracket
                    robot.keyRelease(18); // Release Alt
                    robot.keyRelease(17); // Release Ctrl
                    return event.reply("Camera has slown down / sped up");
                }else if (event.getCustomId().equals("reset")) {
                    return event.reply(resetText.resetText).withComponents(ActionRow.of(cameraFade, cameraDelay, switchDisplayRight, switchDisplayLeft, reset));
                } else{
                    return Mono.empty();
                }
            }).timeout(Duration.ofMinutes(30)).onErrorResume(TimeoutException.class, ignore -> Mono.empty()).then();
            return printOnLogin.and(handlePingCommand).and(tempListener);
        });

        login.block();
    }
}
