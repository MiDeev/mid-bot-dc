package ru.mideev.midbot.command.admin.other;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.UtilLang;

import java.util.Locale;

public class News extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getChannel().getId().equals("941364846899900496") && event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).equals(UtilLang.PREFIX + "news")) {
            event.getChannel().sendMessage("Доброго времени суток, уважаемые @everyone! \n" +
                    "Как Вы помните, ранее я снимал видео на тему получения значков на GitHub'e...\n" +
                    "Так вот, **ВЫШЛА ВТОРАЯ ЧАСТЬ ПО ПОЛУЧЕНИЮ ЗНАЧКОВ**!\n" +
                    "Заходите на канал **MiDeev'a** или смотрите сразу по ссылке: https://www.youtube.com/watch?v=m31bPjI7zEM\n" +
                    "\n" +
                    "*Не забудьте подписаться и лайкосики ставить, надеюсь Вы справитесь!*").submit();
        }
    }
}