package lynxtrakker;

import lynxtrakker.events.eventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main extends ListenerAdapter {
    public static long start = System.currentTimeMillis();
    public static void main(String[] args) {
        try {
            JDABuilder builder = JDABuilder.createDefault("ODMxNTM5NjQzNDc1Njg5NTAy.GBekrF.Z5rlioyubSwEQWFu8lqugQEdrTWM8VYiR_wMk4")
                    .setAutoReconnect(true)
                    .setActivity(Activity.playing("FORTNITE BATTLE ROYALE ON MY SAMSUNG SMART FRIDGE"))
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .disableCache(CacheFlag.ACTIVITY)
                    .setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
                    .setChunkingFilter(ChunkingFilter.NONE)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS);
            JDA jda = builder.build();
            jda.addEventListener(new eventListener());
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}