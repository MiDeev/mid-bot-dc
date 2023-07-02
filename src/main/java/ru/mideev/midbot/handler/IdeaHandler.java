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

public class IdeaHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMember() == null) return;

        if (event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(".an")
                && event.getMember().getId().equals("421259943123877888"))
            return;

        String mid = event.getMember().getId().equals("421259943123877888") ? "0xff4444" : "0xff6331";

        if (event.getMember().getUser().isBot())
            return;

        if (event.getMember().getUser().isSystem())
            return;

        if (! (event.getChannel().getId().equals("1109502097914863667") || event.getChannel().getId().equals("979498476452859994")))
            return;

        String content = event.getMessage().getContentRaw();

        event.getMessage().delete().queue();

        Message message = event.getChannel().asTextChannel().sendMessageEmbeds(new EmbedBuilder()
                .setColor(Color.decode(mid))
                .setAuthor("ОТКРЫТАЯ ИДЕЯ ", null, event.getMember().getEffectiveAvatarUrl())
                .setDescription("**Предложение от: <@" + event.getMessage().getAuthor().getId() + "> ** \n")
                .appendDescription("\n" + content)
                .build()).complete();

        Main.DATABASE.insertIdea(event.getAuthor().getIdLong(), message.getIdLong());

        message.addReaction(Emoji.fromCustom("green_check_mark", 983314695027064832L, false)).queue();
        message.addReaction(Emoji.fromUnicode("❌")).queue();

    }
}

