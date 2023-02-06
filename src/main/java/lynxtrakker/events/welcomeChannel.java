package lynxtrakker.events;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class welcomeChannel extends ListenerAdapter {
    Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
    String webhookURL = dotenv.get("WEBHOOKURL");
    private final WebhookClient client;
    public welcomeChannel() {
        WebhookClientBuilder builder = new WebhookClientBuilder(webhookURL);
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("welcomeChannel");
            thread.setDaemon(true);
            return thread;
        });
        this.client = builder.build();

    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        Guild guild = event.getGuild();
        if (guild.getIdLong() == Long.parseLong(dotenv.get("LYNXMAIN"))) {
            WebhookEmbedBuilder builder = new WebhookEmbedBuilder()
                    .setColor(232323)
                    .setDescription("Welcome to the server" + event.getUser().getAsMention() + "!");
            WebhookMessageBuilder b1 = new WebhookMessageBuilder()
                    .setUsername("Welcome Bot")
                    .addEmbeds(builder.build());
            client.send(b1.build());
        }
    }
}
