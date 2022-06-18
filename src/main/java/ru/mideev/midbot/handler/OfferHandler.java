package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;

import java.awt.*;

public class OfferHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMember() == null) return;

        if ((event.getMessage().getContentDisplay().startsWith(".true") || event.getMessage().getContentDisplay().startsWith(".false")) && event.getMember().getId().equals("421259943123877888")) return;

        String mid = null;

        if (event.getChannel().getId().equals("985623622293028935") && !event.getMember().getUser().isBot() && !event.getMember().getUser().isSystem()) {
            String content = event.getMessage().getContentRaw();

            Message message = event.getTextChannel().sendMessageEmbeds(new EmbedBuilder()
                    .setColor(Color.decode("0xffb135"))
                    .setAuthor("ОТКРЫТОЕ ПРЕДЛОЖЕНИЕ ",null , event.getMember().getEffectiveAvatarUrl())
                    .appendDescription("\n" + content)
                    .build()).complete();

            Main.DATABASE.insertIdea(event.getAuthor().getIdLong(), message.getIdLong());

            message.addReaction(":green_check_mark:983314695027064832").queue();
            message.addReaction("❌").queue();

            try {
                event.getMessage().delete().queue();
            } catch (Throwable ignored) {}
        }
    }
}

