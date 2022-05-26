package ru.mideev.midbot.command.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
public class FilterChannel implements IFilter {

    private final String channelId;

    @Getter
    @Accessors(fluent = true)
    private List<String> ignoredUsers = new ArrayList<>();

    @Override
    public boolean test(MessageReceivedEvent event) {
        return event.getChannel().getId().equals(channelId);
    }
}
