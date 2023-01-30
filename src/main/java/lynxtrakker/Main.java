package lynxtrakker;

import io.github.cdimascio.dotenv.Dotenv;
import lynxtrakker.events.eventListener;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static long start = System.currentTimeMillis(); // Creates the start time that we are going to be used to test how long the bot takes to start up

    private final Dotenv config;
    private final ShardManager shardManager; // Creating a private variable called shardManager and also making it final so it cannot be changed
    public Main() throws LoginException {
        config = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
        String token = config.get("TOKEN");

        //This builds the shard manager and gives the manager the token to link with Discord
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        //Sets options for the builder
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.streaming("Moistcr1tikal", "https://www.twitch.tv/moistcr1tikal"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build(); // We are building the builder into a variable called shardManager

        // Register main listener file right here
        shardManager.addEventListener(new eventListener()); //Adds the main event listening file to the bot

    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            Main bot = new Main();




        } catch (Exception e){
            e.printStackTrace();
        }

    }
}