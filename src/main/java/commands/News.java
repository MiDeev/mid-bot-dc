package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class News extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        EmbedBuilder eb = new EmbedBuilder();

        if (event.getChannel().getId().equals("941364846899900496") && event.getMessage().getContentDisplay().contains(".news")) {
            event.getChannel().sendMessage("**Привет, @everyone!** На связи **MiDeev**! Не поверите, но на канале вышло **НЕВЕРОЯТНОГО** уровня **видео**, туда было вложено не мало **сил** и **времени**! \n**СКОРЕЕ СМОТРИТЕ**: https://www.youtube.com/watch?v=868RONy_CGw").submit();

        }

    }
}
