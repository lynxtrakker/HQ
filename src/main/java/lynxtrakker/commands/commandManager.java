package lynxtrakker.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class commandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        switch (event.getName().toLowerCase()) {
            case "welcome" -> {
                Member member = event.getOption("member", OptionMapping::getAsMember);
                if (member != null) {
                    event.replyFormat("Welcome to the server %s!", member.getAsMention()).setEphemeral(true).queue();
                } else {
                    event.reply("Welcome to the server!!").queue();
                }
            }
            case "ping" -> {
                //long time = System.currentTimeMillis();
                //double ping = Objects.requireNonNull(event.getJDA().getShardManager()).getAverageGatewayPing();
                //EmbedBuilder embedBuilder = new EmbedBuilder();
                //embedBuilder.setDescription("Pong!")
                //        .setColor(Color.RED);
                //event.replyEmbeds(embedBuilder.build())
                //        .setEphemeral(false)
                //        .flatMap(v -> {
                //            EmbedBuilder eb2 = new EmbedBuilder()
                //                    .setColor(Color.GREEN);
                //            eb2.setDescription(String.format("Ping: %s ms", System.currentTimeMillis() - time));
                //            event.getHook().editOriginalEmbeds(eb2.build()).queue();
                //            return null;
                //        }).queue();
                long start = System.currentTimeMillis();
                double ping = Objects.requireNonNull(event.getJDA().getShardManager().getAverageGatewayPing());
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setDescription("Pong!");
                event.replyEmbeds(eb.build()).setEphemeral(false)
                        .flatMap(v -> {
                            String description = String.format("Ping: %s ms", ping);
                            eb.setDescription(description)
                                    .setColor(Color.GREEN);
                            return v.editOriginalEmbeds(eb.build());
                        }).queue();

            }
            case "userinfo" -> {
                Member member = event.getOption("member", OptionMapping::getAsMember); //Gets the member from the first option in the command
                Boolean bool = event.getOption("seen", OptionMapping::getAsBoolean); //Gets the boolean from the second option in the command
                if (member != null) { //If member is not null, continue with the message
                    User user = member.getUser(); //Gets user from the member info
                    String discordJoin = String.valueOf(user.getTimeCreated()); //Gets the time of which they created their discord account
                    String avatar = user.getAvatarUrl(); //Gets the avatar url for the thumbnail
                    OnlineStatus status = member.getOnlineStatus(); //Gets the status for the member
                    EmbedBuilder eb = new EmbedBuilder()
                            .setColor(Color.DARK_GRAY)
                            .setAuthor(user.getName(), avatar)
                            .setDescription("in")
                            .setFooter(discordJoin)
                            .addField("Status: ", String.valueOf(member.getOnlineStatus()), true)
                            .setThumbnail(avatar);
                    event.replyEmbeds(eb.build()).setEphemeral(Objects.requireNonNullElse(bool, false)).queue();

                } else {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setDescription("Please provide a user to get info for!");
                    event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                    System.out.println("fail");
                }
            }
        }
    }

}
