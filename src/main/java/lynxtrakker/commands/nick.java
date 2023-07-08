package lynxtrakker.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Objects;

public class nick extends ListenerAdapter {

    public static void nickProgram(SlashCommandInteractionEvent event) {
        String nick = event.getOption("nick", OptionMapping::getAsString);
        event.getMember().modifyNickname(nick).queue();
    }
}
