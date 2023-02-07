package lynxtrakker.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;

public class nick extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        ShardManager manager = event.getJDA().getShardManager();
        String[] bad = {"pizza", "help", "what"};


    }
}
