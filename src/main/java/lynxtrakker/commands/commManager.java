package lynxtrakker.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class commManager extends ListenerAdapter {
    Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load(); //Gets all the secret stuff from the .env file
    Long ownerid = Long.valueOf(dotenv.get("OWNERID")); //Gets the Owner ID from the .env
    String prefix = String.valueOf(dotenv.get("PREFIX")); //Gets the prefix that is set in the env filez
    static Map<String, String> string = new HashMap<>();
    //static Map<String, String> commands = new HashMap<>();



    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        JDA jda = event.getJDA();
        Member member = event.getMember(); //Gets the member info from the event
        String commandName = event.getName().toLowerCase(); //Gets the name of the command
        switch (commandName) {
            case "welcome" -> { //Welcome Command
                Member mem = event.getOption("member", OptionMapping::getAsMember); //Gets the member name from the command
                if (mem != null) { //If the member's name is null:
                    event.replyFormat("Welcome to %s, %s!", mem.getGuild().getName(), mem.getAsMention()).setEphemeral(true).queue(); //Reply

                } else {
                    event.reply("Welcome to the server!").queue();
                }
            } case "ping" -> {
                //long start = System.currentTimeMillis();
                double ping = Objects.requireNonNull(event.getJDA().getShardManager()).getAverageGatewayPing();
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setDescription("Pong!");

                event.deferReply().addEmbeds(eb.build()).setEphemeral(false)
                        .flatMap(v -> {
                            eb.setDescription(String.format("Ping: %s ms", ping));
                            if (ping <= 50){
                                eb.setColor(Color.GREEN);
                            } else if (ping > 50 && ping < 151) {
                                eb.setColor(Color.YELLOW);
                            } else if (ping > 150) {
                                eb.setColor(Color.RED);
                            }
                            return v.editOriginalEmbeds(eb.build());
                        }).queue();
            }
            case "user" -> {
                Member mem = event.getOption("member", OptionMapping::getAsMember); //Gets the member from the first option in the command
                Boolean bool = event.getOption("visible", OptionMapping::getAsBoolean); //Gets the boolean from the second option in the command
                EmbedBuilder eb = new EmbedBuilder(); //We are going to set the base of the embed up here
                if (mem != null) { //If mem is not null:
                    User user = mem.getUser(); //Gets the user info of the member
                    string.put("avatar", user.getAvatarUrl()); //Puts the avatar url into a hashmap
                    OnlineStatus status = mem.getOnlineStatus(); //Gets the online status of the member
                    eb.setColor(Color.DARK_GRAY) //Sets the color to dark gray
                            .setAuthor(user.getName(), string.get("avatar")) //Sets the author to the name of the member and the url to the member's avatar
                            .setFooter(joinManip(mem)) //Sets the footer to the join date of the member
                            .addField("Status: ", String.valueOf(mem.getOnlineStatus()), true) //Adds a field with the status of the user
                            .setThumbnail(string.get("avatar")); //Sets the thumbnail to the member's avatar
                    event.replyEmbeds(eb.build()).setEphemeral(Objects.requireNonNullElse(bool, false)).queue(); //Now, we reply to the command with the complete embed
                } else {
                    eb.setColor(Color.RED)
                            .setDescription("Please provide a user to get info for!");
                    event.replyEmbeds(eb.build()).setEphemeral(true).queue();

                }
            }
            case "shutdown" -> {
                if (Objects.requireNonNull(event.getMember()).getIdLong() == ownerid) {
                    String name = event.getJDA().getSelfUser().getName();
                    String avatar = event.getJDA().getSelfUser().getAvatarUrl();
                    EmbedBuilder eb = new EmbedBuilder()
                            .setAuthor(name, null, avatar);

                    event.reply("Are you sure you want ot shut down this bot?")
                            .addActionRow(
                                    Button.success("shutdownYes", "Yes"),
                                    Button.danger("shutdownNo", "No")
                            ).queue();

                }
            }
            case "nick" -> {
                if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.NICKNAME_CHANGE)) {
                    String n = event.getOption("nick", OptionMapping::getAsString);
                    Member memNick = event.getOption("member", OptionMapping::getAsMember);
                    event.getMember().modifyNickname(n).queue();
                }
            }
            
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        switch (event.getComponentId()){
            case "shutdownYes" -> {
                Button yes = event.getButton();
                if (event.getInteraction().getUser().getIdLong() == ownerid){
                    //System.exit(131);
                } else {
                    Button b = yes.asDisabled();
                    event.editButton(b).queue();
                }

                System.out.println("test");
                Button b = yes.asDisabled();
                event.editButton(b).queue();
            }
            case "shutdownNo" -> {
                Button no = event.getButton();
                event.getInteraction().editMessage("Word!").queue();
            }
        }
    }


    public static String joinManip(Member member){
        User user = member.getUser(); //Gets the user data from member
        String creationDate = String.valueOf(user.getTimeCreated()); //Gets the date that they created their discord account
        String[] timeCreated = creationDate.split("T"); //Splits the date from the time as we do not need the time
        String[] date = timeCreated[0].split("-"); //Takes the original yyyy-mm-dd and turns it into ->
        return date[1] + "-" + date[2] + "-" + date[0]; //mm-dd-yyyy
    }
}
