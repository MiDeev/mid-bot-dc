package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class UpdatesHandler extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (event.getChannel().getId().equals("989613511124590662") && !event.getMember().getUser().isBot()) {

            embedBuilder.setColor(new Color(0xdcddde));
            embedBuilder.setDescription(event.getMessage().getContentRaw());
            event.getMessage().getChannel().asTextChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            event.getMessage().delete().submit();
        }
    }
}
