package ru.mideev.midbot.command.filter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FilterChannelWithMessage extends FilterChannel {

    private final String errorMessage;

    public FilterChannelWithMessage(String channelId, String errorMessage) {
        super(channelId);

        this.errorMessage = errorMessage;
    }

    public FilterChannelWithMessage(String channelId, String errorMessage, List<String> ignoredUsers) {
        super(channelId, ignoredUsers);

        this.errorMessage = errorMessage;
    }

    @Override
    public void onError(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Objects.requireNonNull(event.getMember()).getColor());
        eb.addField("Ошибка!", errorMessage.replace("$AUTHOR_ID", event.getAuthor().getId()), true);
        event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();
        event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
    }
}
