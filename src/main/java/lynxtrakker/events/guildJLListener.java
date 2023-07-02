package lynxtrakker.events;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;

import static lynxtrakker.eventListener.servers;


public class guildJLListener extends ListenerAdapter {
    int guildAmount = servers.size();


    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        ShardManager manager = event.getJDA().getShardManager();
        Guild guild = event.getGuild();
        String name = guild.getName();
        Long id = guild.getIdLong();
        servers.put(name, id);
        assert manager != null;
        manager.setActivity(Activity.watching(String.format("%s servers", guildAmount)));
    }
    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        ShardManager manager = event.getJDA().getShardManager();
        Guild guild = event.getGuild();
        String name = guild.getName();
        servers.remove(name);
        assert manager != null;
        manager.setActivity(Activity.watching(String.format("%s servers", guildAmount)));

    }
}
