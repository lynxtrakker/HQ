package lynxtrakker.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class ban extends ListenerAdapter {

    public static void banProgram(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();
    }
}
