package ru.mideev.midbot.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.DateUtil;

import java.awt.*;

import static ru.mideev.midbot.util.Constants.*;

public class JoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String name = event.getUser().getName();
        if (name.contains("!")) {
            event.getMember().modifyNickname(name.replace("!", "")).queue();
        }

        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(new Color(103, 236, 129));
        emb.setDescription("**" + event.getUser().getAsTag() + "** (<@" + event.getMember().getUser().getId() + ">)" + " присоединился к серверу.");

        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals(JOIN_LEFT_LOGS_CHANNEL_ID))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(emb.build()).queue());

        event.getMember().getGuild().addRoleToMember(event.getMember(), event.getMember().getGuild().getRolesByName("BRO", false).get(0)).queue();
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        EmbedBuilder emba = new EmbedBuilder();
        emba.setColor(new Color(255, 98, 98));

        String date = DateUtil.formatDate(event.getMember());
        emba.setDescription("**" + event.getUser().getAsTag() + "** (<@" + event.getUser().getId() + ">)" + " покинул сервер." +
                "\n\nПробыл на сервере: **" + date + "**");

        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals(JOIN_LEFT_LOGS_CHANNEL_ID))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(emba.build()).queue());
    }
}