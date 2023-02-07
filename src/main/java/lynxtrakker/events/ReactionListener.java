package lynxtrakker.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event){

        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        GuildChannel channel = event.getGuildChannel();
        String link = event.getJumpUrl();
        assert user != null;
        TextChannel log = Objects.requireNonNull(event.getJDA().getGuildById(1068785921857691678L)).getTextChannelById(1069348533192372304L);
        assert log != null;
        log.sendMessage(String.format("%s reacted to a [message](%s) with %s in the %s channel!", user.getAsMention(), link, emoji, channel.getAsMention())).queue();

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.BLUE)
                .setTimestamp(new Date().toInstant());




    }
}
