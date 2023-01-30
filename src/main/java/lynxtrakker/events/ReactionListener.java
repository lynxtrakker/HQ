package lynxtrakker.events;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event){
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        String channelMention = event.getChannel().getAsMention();
        String jumpLink = event.getJumpUrl();

        String message = String.format("%s reacted to a [message](%s) with %s in the %s channel!", user.getAsTag(), jumpLink, emoji, channelMention);

        TextChannel log = event.getJDA().getGuildById(600013597858594856L).getTextChannelById(600013599418744874L);
        assert log != null;
        log.sendMessage(MessageCreateData.fromContent(message)).queue();

    }
}
