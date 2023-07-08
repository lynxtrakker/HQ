package lynxtrakker;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class statusUpdater extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        JDA jda = event.getJDA();
        int guilds = event.getJDA().getGuilds().size();
        Objects.requireNonNull(jda.getShardManager()).setActivity(Activity.watching(guilds + " servers").asRichPresence());
    }
}
