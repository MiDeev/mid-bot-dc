package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FallbackHandler extends ListenerAdapter {

    private static final List<String> COMMANDS = Arrays.asList(".help", ".si", ".ui", ".ava", ".banner", ".server", ".user", ".info");

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        EmbedBuilder fh = new EmbedBuilder();

        if (!event.isFromGuild() && event.getMessage().getContentDisplay().startsWith(".")) {
            PrivateChannel privateChannel = event.getAuthor().openPrivateChannel().complete();

            fh.setDescription("<@" + event.getMessage().getAuthor().getId() + ">" + ", использование команд доступно в канале <#941458443749978122>, на сервере **MiDeev's Brothers.**");
            fh.setColor(new Color(0xFF4646));
            privateChannel.sendMessageEmbeds(fh.build()).queue();
        }

        if (COMMANDS.stream().anyMatch(x -> event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(x)) && event.getMessage().getContentDisplay().length() >= 3 && !event.getChannel().asTextChannel().getId().equals("941458443749978122") && !event.getChannel().asTextChannel().getId().equals("979498476452859994") && !event.getAuthor().getId().equals("421259943123877888")) {
            fh.setDescription("<@" + event.getMessage().getAuthor().getId() + ">" + " все команды доступны в <#941458443749978122>");
            fh.setColor(new Color(0xFF4646));
            event.getMessage().getChannel().asTextChannel().sendMessageEmbeds(fh.build()).delay(30, TimeUnit.SECONDS).flatMap(Message::delete).queue();
            event.getMessage().delete().queueAfter(60, TimeUnit.SECONDS);
        }
    }
}
