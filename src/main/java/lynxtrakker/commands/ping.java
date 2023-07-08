package lynxtrakker.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;

public class ping extends ListenerAdapter {
    public static void pingProgram(SlashCommandInteractionEvent event) {
        double gatewayPing = Objects.requireNonNull(event.getJDA().getShardManager()).getAverageGatewayPing();
        double restPing = event.getJDA().getRestPing().complete();
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription("Pong!");

        event.deferReply(false).addEmbeds(eb.build())
                .flatMap(v -> {
                    if (gatewayPing <= 75) {
                        eb.setColor(Color.GREEN);
                    } else if (gatewayPing > 75 && gatewayPing <= 150) {
                        eb.setColor(Color.ORANGE);
                    } else if (gatewayPing > 150) {
                        eb.setColor(Color.RED);
                    }
                    eb.setDescription(String.format("Ping: %sms", gatewayPing));
                    return v.editOriginalEmbeds(eb.build());
                }).queue();
    }
}
