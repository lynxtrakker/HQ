package lynxtrakker;

import lynxtrakker.commands.*;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
public class commManager extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        long start = System.currentTimeMillis();
        List<CommandData> commandData = new ArrayList<>();

        // Command: /help <page>
        // Permission: No Permission Needed
        // Purpose: Get a list of commands and other stuff
        commandData.add(Commands.slash("help", "Get a list of commands and other stuff")
                .addOptions(new OptionData(
                        OptionType.INTEGER,
                        "page",
                        "Which page number would you like to go to?",
                        false)
                ).setGuildOnly(false)
        );

        // This for loop allows you to incorporate aliases into slash commands
        //String[] help = {"help", "h"};
        //for (String s : help) {
        //    commandData.add(Commands.slash(s, "Get a list of commands and other stuff")
        //            .addOptions(new OptionData(OptionType.INTEGER, "page", "Which page number would you like to go
        //            to?", false))
        //    );
        //}

        // Command: /helpc <command>
        // Permission: No Permission Needed
        // Purpose: Get help for a certain command.
        commandData.add(Commands.slash("helpc", "Get help for a certain command")
                .addOptions(new OptionData(
                        OptionType.STRING,
                        "command",
                        "Which command are you needing help with?",
                        true)
                ).setGuildOnly(false)
        );


        // Command: /welcome <member>(Optional)
        // Permission: No Permission Needed
        // Purpose: Welcome a member to the server!!!
        commandData.add(Commands.slash("welcome", "Welcome someone to the server!")
                .addOptions(new OptionData(OptionType.MENTIONABLE,
                        "member",
                        "Mention the new member so they can feel welcome",
                        false)
                ).setGuildOnly(true)
        );

        // Command: /user <member> <visible>(Optional)
        // Permission: No Permission Needed
        // Purpose: Get the info of yourself or another member!
        commandData.add(Commands.slash("user", "Get the info of yourself or another member!")
                .addOptions(
                        new OptionData(
                                OptionType.MENTIONABLE,
                                "member",
                                "Which member would you like to get information about?",
                                true
                        ),
                        new OptionData(
                                OptionType.BOOLEAN,
                                "visible", "Choose whether or not you want this message to be visible" +
                                " to others.",
                                false
                        )
                ).setGuildOnly(true)
        );

        // Command: /ping
        // Permission: No Permission Needed
        // Purpose: Retrieve the ping of the bot
        commandData.add(Commands.slash("ping", "Retrieve the ping of the bot"));

        // Command: /nick <new> <member>
        // Permission: NICKNAME_CHANGE
        // Purpose: Used to change the nickname of members.
        commandData.add(Commands.slash("nick", "Change your name to whatever you want!")
                .addOptions(
                        new OptionData(
                                OptionType.STRING,
                                "nickname",
                                "Select the nickname you want!",
                                true
                        ),
                        new OptionData(
                                OptionType.MENTIONABLE,
                                "member",
                                "Who's nickname would you like to change?",
                                false
                        )
                ).setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.NICKNAME_CHANGE))
        );

        // Command: /ban <member> <days> <reason>
        // Permission: BAN_MEMBERS
        // Purpose: Used to ban members from the server
        commandData.add(Commands.slash("ban", "Ban a member from the server")
                .addOptions(
                        new OptionData(
                                OptionType.MENTIONABLE,
                                "member",
                                "Select the member you are needing to ban",
                                true
                        ),
                        new OptionData(
                                OptionType.INTEGER,
                                "days",
                                "Specify how many days of their chats you want to delete",
                                true
                        ),
                        new OptionData(
                                OptionType.STRING,
                                "reason",
                                "State the reason for the ban",
                                false
                        )
                ).setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS))

        );

        // Command: /kick <member> <reason>
        // Permission: KICK_MEMBERS
        // Purpose: Used to kick members from the server
        commandData.add(Commands.slash("kick", "Kick members from the server")
                .addOptions(
                        new OptionData(
                                OptionType.MENTIONABLE,
                                "member",
                                "Select the member you are needing to kick",
                                true
                        ),
                        new OptionData(
                                OptionType.STRING,
                                "reason",
                                "State the reason for the ban",
                                false
                        )
                ).setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS))
        );

        // Command: /mute <member> <duration>
        // Permission: MANAGE_MEMBERS
        //Purpose: Used to mute members for a temporary amount of time
        commandData.add(Commands.slash("mute", "Mute members for a temporary amount of time")
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MODERATE_MEMBERS))
                .addOptions(
                        new OptionData(
                                OptionType.MENTIONABLE,
                                "member",
                                "Who do you need to mute?",
                                true
                        ),
                        new OptionData(
                                OptionType.STRING,
                                "duration",
                                "Choose the duration of mute time",
                                true
                        )
                )
        );

        // Command: /unmute <member>
        // Permission: MANAGE_MEMBERS
        // Purpose: Unmute members that have been muted
        commandData.add(Commands.slash("unmute", "Unmute members that have been muted")
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MODERATE_MEMBERS))
                .addOptions(
                        new OptionData(
                                OptionType.MENTIONABLE,
                                "member",
                                "Who are you needing to unmute?",
                                true
                        )
                )
        );

        event.getGuild().updateCommands().addCommands(commandData).queue();
        System.out.printf("[%s] <%s> Commands loaded and running. Took %sms to complete.\n",
                event.getJDA().getSelfUser().getName(),
                event.getGuild().getName(),
                (System.currentTimeMillis() - start)
        );
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {


        String commandName = event.getName().toLowerCase();
        switch (commandName) {
            case "welcome" -> {
                welcome.welcomeProgram(event);
            }
            case "user" -> {
                user.userProgram(event);
            }
            case "ping" -> {
                ping.pingProgram(event);
            }
            case "shutdown" -> {
                shutdown.shutdownProgram(event);
            }
            //case "nick" -> {
            //    nick.nickProgram(event);
            //}
        }
    }

}
