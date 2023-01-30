package lynxtrakker.events;

import lynxtrakker.Main;
import lynxtrakker.commands.commandManager;
import lynxtrakker.commands.slash.slashCommands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class eventListener implements EventListener {
    @Override
    public void onEvent(GenericEvent event) {
        //
        JDA jda = event.getJDA();

        if (event instanceof ReadyEvent){
            //Register listeners here
            jda.addEventListener(new slashCommands());
            jda.addEventListener(new ReactionListener());
            jda.addEventListener(new commandManager());
            long end = System.currentTimeMillis() - Main.start;
            System.out.printf("[%s] Now up and running. Took %s ms to load and ping is %s ms\n%s\n", event.getJDA().getSelfUser().getName(), end, event.getJDA().getGatewayPing(), event.getJDA().getGuilds());
            TextChannel textChannel = jda.getTextChannelById(753248587420401757L);

        }
        if (event instanceof MessageReceivedEvent){
            if (!((MessageReceivedEvent) event).getAuthor().isBot()) {
                long start = System.currentTimeMillis();
                Guild guild = ((MessageReceivedEvent) event).getGuild();
                Guild lynxmain = event.getJDA().getGuildById(1068785921857691678L);
                assert lynxmain != null;
                TextChannel log = lynxmain.getTextChannelById(1069348533192372304L);
                String guildName = guild.getName();


                String member = Objects.requireNonNull(((MessageReceivedEvent) event).getMember()).getEffectiveName();
                Message message = ((MessageReceivedEvent) event).getMessage();
                String messageContent = ((MessageReceivedEvent) event).getMessage().getContentDisplay().toLowerCase();
                String channel = message.getChannel().getName();
                String footer = String.format("[%s] #%s", guild.getName(), channel);
                String avatar = message.getMember().getUser().getAvatarUrl();
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(Color.DARK_GRAY)
                        .setAuthor(member)
                        .setFooter(footer)
                        .setThumbnail(avatar)
                        .setTimestamp(new Date().toInstant())
                        .setFooter(messageContent, message.getJumpUrl());
                assert log != null;
                log.sendMessageEmbeds(eb.build()).queue();
                System.out.println("Time taken:" + (System.currentTimeMillis() - start) + "ms");


            }
        }
        //if (event instanceof UserUpdateOnlineStatusEvent) {
        //    User user = ((UserUpdateOnlineStatusEvent) event).getUser();
        //    TextChannel botChannel = Objects.requireNonNull(jda.getGuildById(600013597858594856L)).getTextChannelById(600013599418744874L);
        //    String message = String.format("[%s] %s updated their online status from %s to %s", event.getJDA().getSelfUser().getName(), ((UserUpdateOnlineStatusEvent) event).getUser().getName(), ((UserUpdateOnlineStatusEvent) event).getOldOnlineStatus(), ((UserUpdateOnlineStatusEvent) event).getNewOnlineStatus());
        //    assert botChannel != null;
        //    botChannel.sendMessage(message).queue(msg -> {
        //        msg.addReaction(Emoji.fromUnicode("\uD83D\uDC4D")).queue();
//
        //    });

        //}
        if (event instanceof GuildReadyEvent){
            List<CommandData> commandData = new ArrayList<>();
            // Command: /welcome <member>
            OptionData welcome1 = new OptionData(OptionType.MENTIONABLE, "member", "Mention the new member so they know you said welcome!", false);
            commandData.add(Commands.slash("welcome", "Welcome someone to the server").addOptions(welcome1));
            commandData.add(Commands.slash("ping", "Get the ping of the bot"));
            OptionData user = new OptionData(OptionType.MENTIONABLE, "member", "Select a member", true);
            OptionData invis = new OptionData(OptionType.BOOLEAN, "seen", "Choose if you want this message to be seen by others", false);
            commandData.add(Commands.slash("userinfo", "Get the info of a member").addOptions(user, invis));
            ((GuildReadyEvent) event).getGuild().updateCommands().addCommands(commandData).queue();
        }

    }
}
