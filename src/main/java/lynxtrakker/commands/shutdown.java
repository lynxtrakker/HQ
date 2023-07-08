package lynxtrakker.commands;

import lynxtrakker.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Objects;

public class shutdown extends ListenerAdapter {

    private static final Secrets secrets = new Secrets();

    public static void shutdownProgram(SlashCommandInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        if (Objects.requireNonNull(event.getMember()).getIdLong() == secrets.getOwnerID()) {
            String name = event.getJDA().getSelfUser().getEffectiveName();
            String avatar = event.getJDA().getSelfUser().getAvatarUrl();
            eb.setAuthor(name, null, avatar).setDescription("Are you sure you want to shut down this bot?");

            event.replyEmbeds(eb.build())
                    .addActionRow(
                            Button.success("shutdownYes", "Yes"),
                            Button.danger("shutdownNo", "No")
                    ).flatMap((reply -> {
                        event.getJDA().openPrivateChannelById(secrets.getOwnerID()).flatMap(privateChannel -> {
                            privateChannel.sendMessage("Test").queue();
                            return null;
                        }).queue();
                        return null;
                    })).queue();



        } else {
            eb.setDescription("You do not have permission to use this command.");

            event.replyEmbeds(eb.build()).flatMap((reply -> {
                event.getJDA().openPrivateChannelById(secrets.getOwnerID()).flatMap((privateChannel -> {
                    privateChannel.sendMessage("Someone just tried to use /shutdown").queue();
                    return null;
                })
                ).queue();
                return null;
            })).queue();
        }
    }
}
