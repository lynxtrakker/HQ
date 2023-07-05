package lynxtrakker;

import io.github.cdimascio.dotenv.Dotenv;
import lynxtrakker.Main;

import lynxtrakker.commands.commManager;
import lynxtrakker.events.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class eventListener implements EventListener {
    //TODO:
    // * Add ban command
    // * Add kick command
    // * Add mute command
    // * Add help command
    // * Make all commands work with slash and message
    // * Add vcmove command
    // * Add functional database
    // * Clean up code
    // * Optimize to the max


    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();

    public static HashMap<String, Long> servers = new HashMap<String, Long>();

    @Override
    public void onEvent(GenericEvent event) {
        ShardManager manager = event.getJDA().getShardManager();
        JDA jda = event.getJDA();
        assert manager != null;
        if (event instanceof ReadyEvent) {

            List<Guild> guilds = event.getJDA().getGuilds();
            //List<Guild> guilds = .getJDA().getGuilds();
            for (Guild guild : guilds) {
                servers.put(guild.getName(), guild.getIdLong());
            }
            System.out.println(servers);

            int guildAmount = servers.size();
            //Register listeners here
            jda.addEventListener(new ReactionListener());
            jda.addEventListener(new commManager());
            jda.addEventListener(new messageReciever());
            jda.addEventListener(new guildJLListener());
            jda.addEventListener(new userStatusListener());
            jda.addEventListener(new welcomeChannel());
            jda.addEventListener(new database());
            jda.addEventListener(new buttonListener());
            long end = System.currentTimeMillis() - Main.start;
            System.out.printf("[%s] Now up and running. Took %s ms to load and ping is %s ms\n", event.getJDA().getSelfUser().getName(), end, event.getJDA().getGatewayPing());
            TextChannel textChannel = jda.getTextChannelById(753248587420401757L);
            manager.setActivity(Activity.watching(String.format("%s servers", guildAmount)));

        }

        if (event instanceof GuildReadyEvent) {
            long guildStart = System.currentTimeMillis();
            List<CommandData> commandData = new ArrayList<>();
            // Command: /help <page>
            // Permission: No Permission Needed
            // Purpose: Get a list of commands and other stuff.
            commandData.add(Commands.slash("help", "Get a list of commands and other stuff")
                    .addOptions(new OptionData(OptionType.INTEGER, "page", "Which page number would you like to go to?", false))
                    .setGuildOnly(false)
            );

            // Command: /helpc <command>
            // Permission: No Permission Needed
            // Purpose: Get help for a certain command.


            // Command: /welcome <member>(optional)
            // Permission: No permission needed
            // Purpose: Welcome a member to the server!!!
            commandData.add(Commands.slash("welcome", "Welcome someone to the server")
                    .addOptions(new OptionData(OptionType.MENTIONABLE, "member", "Mention the new member so they can feel welcome!", false))
                    .setGuildOnly(true)
            );

            // Command: /ping
            // Permission: No permission needed
            // Purpose: Get the ping of the server and the bot.
            commandData.add(Commands.slash("ping", "Get the ping of the server"));

            // Command: /user <member> <visible>(optional)
            OptionData user = new OptionData(OptionType.MENTIONABLE, "member", "Select a member", true);
            OptionData visible = new OptionData(OptionType.BOOLEAN, "visible", "Choose if you want this message to be seen by others", false);
            commandData.add(Commands.slash("user", "Get the info of a member")
                    .addOptions(user, visible)
            );

            // Command: /shutdown
            commandData.add(Commands.slash("shutdown", "Shut down the bot")
                    .setDefaultPermissions(DefaultMemberPermissions.ENABLED)
            );

            // Command: /nick <new> <member>
            // Permission: NICKNAME_CHANGE
            // Purpose: Used to change the nickname of members.
            OptionData nick = new OptionData(OptionType.STRING, "nickname", "Select the nickname that you want", true);
            OptionData memNick = new OptionData(OptionType.MENTIONABLE, "member", "Who's nickname would you like to change?");
            commandData.add(Commands.slash("nick", "Change your name to whatever you want!")
                    .addOptions(nick)
                    .setDefaultPermissions(DefaultMemberPermissions
                            .enabledFor(Permission.NICKNAME_CHANGE))
                    .setGuildOnly(true)
            );
            commandData.add(Commands.message("$nick")
                    .setGuildOnly(true)
                    .setDefaultPermissions(DefaultMemberPermissions
                            .enabledFor(Permission.NICKNAME_CHANGE)
                    )
            );

            // Command: /ban <member> <reason>(optional)
            // Permission: BAN_MEMBERS
            // Purpose: Used to ban members from the server
            OptionData banMem = new OptionData(OptionType.MENTIONABLE, "member", "Choose who you would like to ban", true);
            OptionData reason = new OptionData(OptionType.STRING, "reason", "Why do you want to ban this member?", false);
            commandData.add(Commands.slash("ban", "Ban a member from the server")
                    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS))
                    .setGuildOnly(true)
            );

            // Command: /kick <member> <reason>(optional)
            // Required Permission: KICK_MEMBERS
            // Purpose: Used to kick members from the server
            OptionData kickMem = new OptionData(OptionType.MENTIONABLE, "member", "Choose who you would like to kick", true);
            OptionData kickReason = new OptionData(OptionType.STRING, "reason", "Why do you want to kick this member?");
            commandData.add(Commands.slash("kick", "Kick a member from the server")
                    .setGuildOnly(true)
                    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS))
            );

            // Command: /mute <member> <duration>(optional)
            // Required Permission: MANAGE_MEMBERS
            // Purpose: Will be used to mute members for a temporary amount of time or permanently. Applies to both chat and voice chat
            OptionData muteMem = new OptionData(OptionType.MENTIONABLE, "member", "Who do you want to mute?", true);
            OptionData muteDuration = new OptionData(OptionType.STRING, "duration", "How long do you want to mute the member for?", false);
            commandData.add(Commands.slash("mute", "Mute a member for misbehaviour")
                    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MODERATE_MEMBERS))
                    .setGuildOnly(true)
            );

            // Command: /vcmove <member> <channel>
            // Reqired Permission: Manage Members
            // Purpose: Will be used to move members to different voice chats


            ((GuildReadyEvent) event).getGuild().updateCommands().addCommands(commandData).queue();

            long guildEnd = System.currentTimeMillis() - guildStart;
            String botName = event.getJDA().getSelfUser().getName();
            String guildName = ((GuildReadyEvent) event).getGuild().getName();
            System.out.printf("[%s] %s is now up and running! Commands and listeners are available and working! Took %sms to complete!%n", botName, guildName, guildEnd);

        }
    }
}
