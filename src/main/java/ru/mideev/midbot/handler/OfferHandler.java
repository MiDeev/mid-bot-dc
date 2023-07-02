package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;

import java.awt.*;
import java.util.Locale;

public class OfferHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMember() == null) return;

        if ((event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(".true") || event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(".false")) && event.getMember().getId().equals("421259943123877888")) return;

        if (event.getMember().getUser().isBot())
            return;

        if (event.getMember().getUser().isSystem())
            return;

        if (!event.getChannel().getId().equals("985623622293028935")) {
            return;
        }

        String content = event.getMessage().getContentRaw();

        Message message = event.getChannel().asTextChannel().sendMessage("<@&980016910227869746>").addEmbeds(new EmbedBuilder()
                .setColor(Color.decode("0xffb135"))
                .setAuthor("ОТКРЫТОЕ ПРЕДЛОЖЕНИЕ ",null , event.getMember().getEffectiveAvatarUrl())
                .appendDescription("\n" + content)
                .build()).complete();

        Main.DATABASE.insertIdea(event.getAuthor().getIdLong(), message.getIdLong());

        message.addReaction(Emoji.fromCustom("green_check_mark", 983314695027064832L, false)).queue();
        message.addReaction(Emoji.fromUnicode("❌")).queue();

        event.getMessage().delete().queue();
    }
}

