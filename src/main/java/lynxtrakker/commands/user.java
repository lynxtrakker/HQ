package lynxtrakker.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.Objects;

public class user extends ListenerAdapter {
    public static void userProgram(SlashCommandInteractionEvent event) {
        Member mem = event.getOption("member", OptionMapping::getAsMember);
        Boolean bool = event.getOption("visible", OptionMapping::getAsBoolean);
        EmbedBuilder eb = new EmbedBuilder();
        if (mem != null){
            User user = mem.getUser();
            eb.setColor(Color.DARK_GRAY)
                    .setAuthor(user.getName(), user.getAvatarUrl())
                    .addField("Status: ", String.valueOf(mem.getOnlineStatus()), true)
                    .setFooter(join(mem))
                    .setThumbnail(user.getAvatarUrl());
            event.replyEmbeds(eb.build()).setEphemeral(Objects.requireNonNullElse(bool, false)).queue();
        } else {
            eb.setColor(Color.RED)
                    .setDescription("Please provide a user to get info for!");
            event.replyEmbeds(eb.build()).setEphemeral(true).queue();
        }
    }

    public static String join(Member member) {
        User user = member.getUser(); //Gets the user data from member
        String creationDate = String.valueOf(user.getTimeCreated()); //Gets the date that they created their Discord account
        String[] timeCreated = creationDate.split("T"); //Splits the date from the time as we do not need the time
        String[] date = timeCreated[0].split("-"); //Takes the original yyyy-mm-dd and turns it into ->
        return date[1] + "-" + date[2] + "-" + date[0]; //mm-dd-yyyy
    }


}
