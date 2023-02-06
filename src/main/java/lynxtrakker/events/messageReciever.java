package lynxtrakker.events;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class messageReciever extends ListenerAdapter {

    Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
    Long ownerID = Long.valueOf(dotenv.get("OWNERID"));
    Long mainID = Long.valueOf(dotenv.get("LYNXMAIN"));

    static Map<String, String> words = new HashMap<>();


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        words.put("pizza", "\uD83C\uDF55");
        words.put("welcome", "\uD83C\uDF55");
        ChannelType channelType = event.getChannelType();
        if (!((MessageReceivedEvent) event).getAuthor().isBot()){
            long start = System.currentTimeMillis();
            Guild main = event.getJDA().getGuildById(mainID);


            Message message = event.getMessage();
            String messageContent = message.getContentDisplay();
            User user = event.getAuthor();
            if (channelType != ChannelType.PRIVATE){
                Member member = message.getMember();
            }

            switch (channelType){
                case PRIVATE -> {




                    System.out.println("private");
                }
                case TEXT -> {
                    String memName = Objects.requireNonNull(event.getMember()).getEffectiveName();
                    Guild guild = event.getGuild();
                    String guildName = guild.getName();
                    TextChannel channel = event.getMessage().getChannel().asTextChannel();
                    System.out.println("test");
                }

            }


        }



    }
}
