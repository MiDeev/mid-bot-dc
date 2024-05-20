package ru.mideev.midbot.command.admin.other;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReactionManager extends ListenerAdapter {
    private static final List<String> CHANNEL_IDS = Arrays.asList(
            "1071497117530587326",
            "1096456959663812618",
            "1096456932182736977",
            "1120388399963045918",
            "989613511124590662"
    );

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (CHANNEL_IDS.contains(event.getChannel().getId())) {
            event.getMessage().addReaction(Emoji.fromUnicode("\uD83D\uDC4D")).queueAfter(1, TimeUnit.SECONDS);
            event.getMessage().addReaction(Emoji.fromUnicode("\uD83D\uDC4E")).queueAfter(2, TimeUnit.SECONDS);
        }
    }
}
