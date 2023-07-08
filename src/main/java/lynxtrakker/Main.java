package lynxtrakker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicReference;


public class Main {
    public static long start = System.currentTimeMillis(); // Creates the start time that shall be used to test bot start-up timing
    private final ShardManager shardManager;


    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    static String json = "secrets.json";

    public Main() throws FileNotFoundException {
        AtomicReference<Secrets> secrets = new AtomicReference<>();

        secrets.set(gson.fromJson(new FileReader(json), Secrets.class));
        //Secrets secret = gson.fromJson(new FileReader(json), secrets.getClass());

        // This builds the shardManager and gives the manager a token to link with Discord
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(secrets.get().getToken());
        //Sets options for the builder
        builder.enableIntents(
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_PRESENCES
                ).enableCache(CacheFlag.ONLINE_STATUS);
        // Creating a private variable called shardManager and also making it final, so it cannot be changed.
        shardManager = builder.build();
        shardManager.addEventListener(new eventListener());
        //getShardManager().addEventListener(new commManager());
    }
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        try {
            AtomicReference<Main> bot = new AtomicReference<>(new Main());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ShardManager getShardManager() {
        return shardManager;
    }


}
