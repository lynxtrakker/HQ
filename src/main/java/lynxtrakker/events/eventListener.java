package lynxtrakker.events;

import io.github.cdimascio.dotenv.Dotenv;
import lynxtrakker.Main;
import lynxtrakker.commands.commandManager;
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

import java.security.Permissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class eventListener implements EventListener {

    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
    //Long ownerid = Long.valueOf(dotenv.get("OWNERID"));
    //Long logID = Long.valueOf(dotenv.get("LOGCHANNEL"));
    //Long logServerID = Long.valueOf(dotenv.get("LYNXMAIN"));

    public static HashMap<String, Long> servers = new HashMap<String, Long>();

    @Override
    public void onEvent(GenericEvent event) {
        ShardManager manager = event.getJDA().getShardManager();
        JDA jda = event.getJDA();
        assert manager != null;
        if (event instanceof ReadyEvent){

            List<Guild> guilds = event.getJDA().getGuilds();

            //List<Guild> guilds = .getJDA().getGuilds();
            for (Guild guild : guilds) {
                servers.put(guild.getName(), guild.getIdLong());
            }
            System.out.println(servers);

            int guildAmount = servers.size();
            //Register listeners here
            jda.addEventListener(new ReactionListener());
            jda.addEventListener(new commandManager());
            jda.addEventListener(new messageReciever());
            jda.addEventListener(new guildJLListener());
            jda.addEventListener(new userStatusListener());
            jda.addEventListener(new welcomeChannel());
            jda.addEventListener(new database());
            long end = System.currentTimeMillis() - Main.start;
            System.out.printf("[%s] Now up and running. Took %s ms to load and ping is %s ms\n%s\n", event.getJDA().getSelfUser().getName(), end, event.getJDA().getGatewayPing(), event.getJDA().getGuilds());
            TextChannel textChannel = jda.getTextChannelById(753248587420401757L);
            manager.setActivity(Activity.watching(String.format("%s servers", guildAmount)));
        }

        if (event instanceof GuildReadyEvent){
            List<CommandData> commandData = new ArrayList<>();

            // Command: /welcome <member>
            OptionData welcome1 = new OptionData(OptionType.MENTIONABLE, "member", "Mention the new member so they know you said welcome!", false);
            commandData.add(Commands.slash("welcome", "Welcome someone to the server").addOptions(welcome1));
            commandData.add(Commands.slash("ping", "Get the ping of the bot"));
            OptionData user = new OptionData(OptionType.MENTIONABLE, "member", "Select a member", true);
            OptionData  invis = new OptionData(OptionType.BOOLEAN, "seen", "Choose if you want this message to be seen by others", false);
            commandData.add(Commands.slash("userinfo", "Get the info of a member").addOptions(user, invis));
            commandData.add(Commands.slash("shutdown", "Shut down the bot").setDefaultPermissions(DefaultMemberPermissions.ENABLED));
            OptionData nick = new OptionData(OptionType.STRING, "nickname", "Select the nickname that you want");
            commandData.add(Commands.message("$nick").setGuildOnly(true));
            ((GuildReadyEvent) event).getGuild().updateCommands().addCommands(commandData).queue();


        }
    }
}
