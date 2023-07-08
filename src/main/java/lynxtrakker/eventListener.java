package lynxtrakker;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.util.HashMap;
import java.util.List;

public class eventListener implements EventListener {

    public static HashMap<String, Long> servers = new HashMap<>();

    @Override
    public void onEvent(GenericEvent event) {
        ShardManager manager = event.getJDA().getShardManager();
        JDA jda = event.getJDA();
        assert manager != null;
        if (event instanceof ReadyEvent){
            manager.addEventListener(new commManager());
            List<Guild> guilds = event.getJDA().getGuilds();
            for (Guild guild : guilds) {
                servers.put(guild.getName(), guild.getIdLong());
            }
            System.out.println(servers);

            int guildAmount = servers.size();
            //Register listeners here


            // End of listeners
            System.out.println(jda.getEventManager().getRegisteredListeners());

            long end = System.currentTimeMillis() - Main.start;
            System.out.printf("[%s] Now up and operating. Start-up took %sms to load and the ping is %sms\n",
                    jda.getSelfUser().getName(),
                    end,
                    jda.getGatewayPing()
            );
            manager.setActivity(Activity.watching(String.format("%s servers", guildAmount)));



        }

    }

}

