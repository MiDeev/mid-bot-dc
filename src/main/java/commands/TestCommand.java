package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class TestCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getChannel().getId().equals("942520425936719952") && event.getMessage().getContentDisplay().contains("/test")) {


            EmbedBuilder emba = new EmbedBuilder();
            emba.setColor(new Color(255, 98, 98));


            OffsetDateTime offset = event.getMember().getTimeJoined();
            long time = offset.atZoneSameInstant(ZoneId.of("Europe/Moscow")).toEpochSecond() * 1000;
            SimpleDateFormat format = new SimpleDateFormat("**dd дней, HH часов, mm минут, ss секунд**");
            emba.addField("Пробыл на сервере:", format.format(new Date((ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toEpochSecond() * 1000) - time)), true);

            event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942520425936719952"))
                    .forEach(textChannel -> textChannel.sendMessageEmbeds(emba.build()).queue());


        }
    }
}
