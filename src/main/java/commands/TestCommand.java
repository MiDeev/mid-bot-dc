package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class TestCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getChannel().getId().equals("942520425936719952") && event.getMessage().getContentDisplay().contains("/test")) {


            EmbedBuilder emba = new EmbedBuilder();
            emba.setColor(new Color(255, 98, 98));

            event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942520425936719952"))
                    .forEach(textChannel -> textChannel.sendMessageEmbeds(emba.build()).queue());

        }
    }
}
