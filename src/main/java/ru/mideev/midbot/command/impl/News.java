package ru.mideev.midbot.command.impl;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.mideev.midbot.command.AbstractCommand;
import ru.mideev.midbot.command.filter.FilterChannel;

import static ru.mideev.midbot.util.Constants.VIDEO_NEWS_CHANNEL_ID;

public class News extends AbstractCommand {

    public News() {
        super("news", "Новости", true);
        filters.add(new FilterChannel(VIDEO_NEWS_CHANNEL_ID));
    }

    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("**Привет, @everyone!** На связи **MiDeev**! Не поверите, но на канале вышло **НЕВЕРОЯТНОГО** уровня **видео**, туда было вложено не мало **сил** и **времени**! \n**СКОРЕЕ СМОТРИТЕ**: https://www.youtube.com/watch?v=868RONy_CGw").submit();
        return true;
    }
}
