package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class FallbackHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        EmbedBuilder fb = new EmbedBuilder();

        if (!event.isFromGuild() && event.getMessage().getContentDisplay().startsWith(".") && event.getMessage().getContentDisplay().length() >= 3) {
            PrivateChannel privateChannel = event.getAuthor().openPrivateChannel().complete();

            fb.setDescription("<@" + event.getMessage().getAuthor().getId() + ">" + ", использование команд доступно в канале <#941458443749978122>, на сервере **MiDeev's Brothers.**");

            fb.setColor(new Color(0xFF4646));
            privateChannel.sendMessageEmbeds(fb.build()).queue();
        }

        if (event.getMessage().getContentDisplay().startsWith(".") && event.getMessage().getContentDisplay().length() >= 3 && !event.getTextChannel().getId().equals("941458443749978122") && !event.getAuthor().getId().equals("421259943123877888")) {
            fb.setDescription("<@" + event.getMessage().getAuthor().getId() + ">" + " все команды доступны в <#941458443749978122>");
            fb.setColor(new Color(0xFF4646));
            event.getMessage().getTextChannel().sendMessageEmbeds(fb.build()).delay(30, TimeUnit.SECONDS).flatMap(Message::delete).queue();
            event.getMessage().delete().queueAfter(60, TimeUnit.SECONDS);
        }
    }
}
