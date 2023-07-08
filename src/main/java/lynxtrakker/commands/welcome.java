package lynxtrakker.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class welcome extends ListenerAdapter {
    public static void welcomeProgram(SlashCommandInteractionEvent event) {

        EmbedBuilder eb = new EmbedBuilder();

        Member mem = event.getOption("member", OptionMapping::getAsMember);
        if (mem != null) { // If the member's name is not null:
            eb.setTitle(String.format("Welcome to %s, %s!",
                            mem.getGuild().getName(),
                            mem.getEffectiveName()))
                    .setColor(Color.GREEN);
            event.replyEmbeds(eb.build()).queue();
        } else {
            eb.setTitle("Welcome to the server!")
                    .setColor(Color.BLUE);
            event.replyEmbeds(eb.build()).queue();
        }
    }

}
