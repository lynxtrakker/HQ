package lynxtrakker.events;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;

public class database extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        try {
            File db = new File("db.txt");
            if (db.createNewFile()){
                System.out.println("File created: " + db.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
