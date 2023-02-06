package lynxtrakker.events;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ClientType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;

public class userStatusListener extends ListenerAdapter {

    Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
    Long ownerID = Long.valueOf(dotenv.get("OWNERID"));

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        User user = event.getUser();
        Member member = event.getMember();
        ShardManager manager = event.getJDA().getShardManager();
        if (event.getUser().getIdLong() == ownerID){
            OnlineStatus status = event.getNewOnlineStatus();
            assert manager != null;
            manager.setStatus(status);


        }

    }
}
