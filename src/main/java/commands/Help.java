package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Help extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();

        if (event.getChannel().getId().equals("942520425936719952") && event.getMessage().getContentDisplay().startsWith("/help")) {
            eb.setDescription("<@" + event.getMessage().getAuthor().getId() + ">" + " команда в разработке, пожалуйста ожидайте.");
            eb.setColor(event.getMember().getColor());
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();

        } else if (event.getMessage().getContentDisplay().startsWith("/help")) {
            eb.setDescription("<@" + event.getMessage().getAuthor().getId() + ">" + " все команды доступны в <#941458443749978122>");
            eb.setColor(event.getMember().getColor());
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).delay(7, TimeUnit.SECONDS).flatMap(Message::delete).queue();
            event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
        }
    }
}
