package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class FallbackHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.isFromGuild() && event.getMessage().getContentDisplay().startsWith(".")) {
            PrivateChannel privateChannel = event.getAuthor().openPrivateChannel().complete();

            privateChannel.sendMessage("пиздуй на сервер гондон").submit();
        }
    }
}
