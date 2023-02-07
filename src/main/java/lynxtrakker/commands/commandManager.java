package lynxtrakker.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class commandManager extends ListenerAdapter {
    Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
    Long ownerid = Long.valueOf(dotenv.get("OWNERID"));


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
                //long start = System.currentTimeMillis();
                double ping = Objects.requireNonNull(event.getJDA().getShardManager()).getAverageGatewayPing();
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setDescription("Pong!");

                event.deferReply().addEmbeds(eb.build()).setEphemeral(false)
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
                    String avatar = user.getAvatarUrl(); //Gets the avatar url for the thumbnail
                    OnlineStatus status = member.getOnlineStatus(); //Gets the status for the member
                    EmbedBuilder eb = new EmbedBuilder()
                            .setColor(Color.DARK_GRAY)
                            .setAuthor(user.getName(), avatar)
                            .setDescription("in")
                            .setFooter(joinManip(member))
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
            case "shutdown" -> {
                if (Objects.requireNonNull(event.getMember()).getIdLong() == ownerid) {
                    String avatar = event.getJDA().getSelfUser().getAvatarUrl();
                    EmbedBuilder eb = new EmbedBuilder()
                            .setAuthor("HQ", null, avatar);

                    event.reply("Are you sure you want to shut down this bot?")
                            .addActionRow(
                                    Button.success("Yes", "Yes"),
                                    Button.danger("No", "No")

                            ).queue();

                    //System.exit(130);

                }

            }
            case "nick" -> {

            }
        }

    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        if (event.getComponentId().equals("Yes")){
            Button yes = event.getButton();

            if (event.getInteraction().getUser().getIdLong() == ownerid){

            } else {
                Button bu1 = yes.asDisabled();
                event.editButton(bu1).queue();
            }

            System.out.println("test");
            Button bu1 = yes.asDisabled();
            event.editButton(bu1).queue();

        }
    }

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        ShardManager manager = event.getJDA().getShardManager();
        String msg = event.getName();

        switch (msg.toLowerCase()){
            case "$nick" -> {

            }
        }


    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        ShardManager manager = event.getJDA().getShardManager();
        Message message = event.getMessage();
        String msgContent = message.getContentDisplay();
        String[] args = msgContent.split(" ");
        String cmd = args[0].toLowerCase();
        ChannelType cType = message.getChannelType();
        //System.out.println("te");

        if (cType == ChannelType.TEXT){

        } else if (cType == ChannelType.PRIVATE) {

        }

        switch (cmd){
            case "$nick" -> {
                message.reply("nickname").queue();
            }
            case "$say" -> {

            }
            case "$help" -> {
                User user = message.getAuthor();

            }
        }
    }

    public static String joinManip(Member member){
        User user = member.getUser(); //Gets the user data from member
        String creationDate = String.valueOf(user.getTimeCreated()); //Gets the date that they created their discord account
        String[] timeCreated = creationDate.split("T"); //Splits the date from the time as we do not need the time
        String[] date = timeCreated[0].split("-"); //Takes the original yyyy-mm-dd and turns it into ->
        return date[1] + "-" + date[2] + "-" + date[0]; // mm-dd-yyyy

    }
}
