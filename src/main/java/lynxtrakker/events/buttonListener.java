package lynxtrakker.events;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class buttonListener extends ListenerAdapter {

    Dotenv env = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
    Long owner = Long.valueOf(env.get("OWNERID"));
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        switch (event.getComponentId()) {
            case "shutdownYes" -> {
                Button yes = event.getButton();
                if (event.getInteraction().getUser().getIdLong() == owner) {
                    System.exit(131);
                } else {
                    Button b = yes.asDisabled();
                    event.editButton(b).queue();
                }
            }
            case "shutdownNo" -> {
                Button no = event.getButton();
                event.getInteraction().editMessage("Word!").queue();
            }
        }
    }
}
