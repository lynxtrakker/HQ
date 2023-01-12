package lynxtrakker.events;

import lynxtrakker.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class eventListener implements EventListener {
    @Override
    public void onEvent(GenericEvent event) {
        //
        JDA jda = event.getJDA();
        if (event instanceof ReadyEvent){
            long end = System.currentTimeMillis() - Main.start;
            System.out.printf("[%s] Now up and running. Took %s ms to load and ping is %s ms\n%s\n", event.getJDA().getSelfUser().getName(), end, event.getJDA().getGatewayPing(), event.getJDA().getGuilds());
            TextChannel textChannel = jda.getTextChannelById(753248587420401757L);

        }
        if (event instanceof MessageReceivedEvent){
            if (!((MessageReceivedEvent) event).getAuthor().isBot()) {
                Message message = ((MessageReceivedEvent) event).getMessage();
                String messageContent = ((MessageReceivedEvent) event).getMessage().getContentDisplay().toLowerCase();
            }
        }
    }
}
