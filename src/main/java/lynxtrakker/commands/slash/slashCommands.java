package lynxtrakker.commands.slash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class slashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        switch (event.getName()){
            case "info":
                EmbedBuilder eb = new EmbedBuilder();
                eb.setThumbnail(event.getInteraction().getUser().getEffectiveAvatarUrl());
                eb.setAuthor(event.getUser().getName());


        }
    }
}
