package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class VoiceChannelConnectionHandler extends ListenerAdapter {
    private final Map<String, Set<String>> channelMembers = new HashMap<>();

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        TextChannel textChannel = event.getGuild().getTextChannelById("975514909519540274");
        if (textChannel == null) return;

        if (event.getChannelJoined() != null) {
            handleVoiceUpdate(event.getChannelJoined().getId(), event.getChannelJoined().getMembers(), textChannel, true);
        }

        if (event.getChannelLeft() != null) {
            handleVoiceUpdate(event.getChannelLeft().getId(), event.getChannelLeft().getMembers(), textChannel, false);
        }
    }

    private void handleVoiceUpdate(String channelId, List<Member> members, TextChannel textChannel, boolean joined) {
        Set<String> currentMembers = members.stream()
                .map(member -> member.getUser().getId())
                .collect(Collectors.toSet());

        Set<String> previousMembers = channelMembers.getOrDefault(channelId, Collections.emptySet());

        for (String member : joined ? currentMembers : previousMembers) {
            if (joined && !previousMembers.contains(member) || !joined && !currentMembers.contains(member)) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setAuthor(joined ? "Участник зашёл в голосовой канал" : "Участник покинул голосовой канал", null, joined ? "https://raw.githubusercontent.com/Transiented/src/main/plus_icon.png" : "https://raw.githubusercontent.com/Transiented/src/main/minus_icon.png")
                        .setDescription("<@" + member + ">" + (joined ? " зашёл в " : " покинул ") + "голосовой канал <#" + channelId + ">")
                        .setColor(Color.decode("0xdcdddf"))
                        .setFooter(member)
                        .setTimestamp(Instant.now());
                textChannel.sendMessageEmbeds(embed.build()).queue();
            }
        }

        channelMembers.put(channelId, currentMembers);
    }
}
