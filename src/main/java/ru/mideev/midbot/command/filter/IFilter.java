package ru.mideev.midbot.command.filter;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface IFilter {

    boolean test(MessageReceivedEvent event);

    default void onError(MessageReceivedEvent event) {

    }

    default List<String> ignoredUsers() {
        return Collections.emptyList();
    }
}
